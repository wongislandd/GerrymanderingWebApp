import React, { Component } from 'react'
import { connect } from 'react-redux'
import ReactMapGL, { Layer, Source } from "react-map-gl"
import { setCurrentState, setViewport } from '../../redux/actions/settingActions'
import * as ViewportUtilities from '../../utilities/ViewportUtilities'
import * as MapUtilities from '../../utilities/MapUtilities'
import CountyGeoData from '../../data/NC/CountiesGeoData.json'

class StateSelectionMap extends Component {
    
    componentDidMount() {
        this.props.setCurrentState(ViewportUtilities.STATE_OPTIONS.UNSELECTED)
    }

    _onHover = event => {
        const {
          features,
        } = event;
        if (this.props.DisplayDistricts){
          this.props.resetAllHighlighting()
          const hoveredFeature = features && features.find(f => f.layer.id === MapUtilities.IDs.DISTRICT_FILL_LAYER_ID)
          this.props.setFeaturedDistrict(hoveredFeature)
          if (hoveredFeature != undefined) {
            this.props.addFeatureToHighlight(hoveredFeature)
          }
        } else if(this.props.DisplayPrecincts) {
          this.props.resetAllHighlighting()
          const hoveredFeature = features && features.find(f => f.layer.id === MapUtilities.IDs.PRECINCT_FILL_LAYER_ID)
          this.props.setFeaturedPrecinct(hoveredFeature)
          if (hoveredFeature != undefined) {
            this.props.addFeatureToHighlight(hoveredFeature)
          }
        }
      };

    render() {
        return (
            <div>
                 <ReactMapGL 
                        className = "map-display"
                        {...this.props.MapViewport} 
                        mapboxApiAccessToken = {process.env.REACT_APP_MAPBOX_TOKEN}
                        onViewportChange={viewport=> {
                            this.props.setViewport(viewport)
                        }}
                        onHover={this._onHover.bind(this)}
                    >
                         <Source
                            id = {MapUtilities.IDs.COUNTY_SOURCE_ID}
                            type="geojson"
                            data = {CountyGeoData}
                            generateId = {true}/>
                        <Layer
                            id = {MapUtilities.IDs.COUNTY_FILL_LAYER_ID}
                            type="fill"
                            source={MapUtilities.IDs.COUNTY_SOURCE_ID}
                            paint={{
                            "fill-color" : "#abcdef",
                            "fill-opacity": [
                                'case',
                                ['boolean', ['feature-state', 'hover'], false],
                                .1,
                                .6,
                            ]
                            }}/>
                 </ReactMapGL>
            </div>
        )
    }
}


const mapDispatchToProps = (dispatch) => {
    return {
        setCurrentState : (state) => {dispatch(setCurrentState(state))},
        setViewport : (viewport) => {dispatch(setViewport(viewport))},
    }
  }
  
  const mapStateToProps = (state, ownProps) => {
    return {
        MapViewport : state.MapViewport
    }
  }
  
  export default connect(mapStateToProps, mapDispatchToProps)(StateSelectionMap);