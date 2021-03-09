import React, { Component } from 'react'
import { connect } from 'react-redux'
import ReactMapGL, { Layer, Source } from "react-map-gl"
import { setCurrentState, setTentativeState, setViewport } from '../../redux/actions/settingActions'
import * as ViewportUtilities from '../../utilities/ViewportUtilities'
import * as MapUtilities from '../../utilities/MapUtilities'
import NCCountyGeoData from '../../data/NC/CountiesGeoData.json'
import CACountyGeoData from '../../data/CA/CountiesGeoData.json'
import TXCountyGeoData from '../../data/TX/CountiesGeoData.json'

class StateSelectionMap extends Component {
    
    componentDidMount() {
        this.props.setCurrentState(ViewportUtilities.STATE_OPTIONS.UNSELECTED)
    }

    _onClick = event => {
        const {
          features,
        } = event;
        const hoveredFeature = features && features.find(f => f.layer.id === MapUtilities.IDs.COUNTY_FILL_LAYER_ID + "NC" || f.layer.id === MapUtilities.IDs.COUNTY_FILL_LAYER_ID + "CA" || f.layer.id === MapUtilities.IDs.COUNTY_FILL_LAYER_ID + "TX")
        if (hoveredFeature != undefined) {
          switch(hoveredFeature.layer.id) {
            case MapUtilities.IDs.COUNTY_FILL_LAYER_ID + "NC":
              this.props.setViewport(ViewportUtilities.NORTH_CAROLINA.Maximized)
              this.props.setTentativeState(ViewportUtilities.STATE_OPTIONS.NORTH_CAROLINA)
              break;
            case MapUtilities.IDs.COUNTY_FILL_LAYER_ID + "CA":
              this.props.setViewport(ViewportUtilities.CALIFORNIA.Maximized)
              this.props.setTentativeState(ViewportUtilities.STATE_OPTIONS.CALIFORNIA)
              break;
            case MapUtilities.IDs.COUNTY_FILL_LAYER_ID + "TX":
              this.props.setViewport(ViewportUtilities.TEXAS.Maximized)
              this.props.setTentativeState(ViewportUtilities.STATE_OPTIONS.TEXAS)
              break;
            default:
              this.props.setViewport(ViewportUtilities.UNSELECTED.Maximized)
              this.props.setTentativeState(ViewportUtilities.STATE_OPTIONS.UNSELECTED)
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
                        onClick={this._onClick.bind(this)}
                    >
                         <Source
                            id = {MapUtilities.IDs.COUNTY_SOURCE_ID + "NC"}
                            type="geojson"
                            data = {NCCountyGeoData}
                            generateId = {true}/>
                        <Layer
                            id = {MapUtilities.IDs.COUNTY_FILL_LAYER_ID + "NC"}
                            type="fill"
                            source={MapUtilities.IDs.COUNTY_SOURCE_ID + "NC"}
                            paint={{
                            "fill-color" : "#abcdef",
                            "fill-opacity": [
                                'case',
                                ['boolean', ['feature-state', 'hover'], false],
                                .1,
                                .6,
                            ]
                            }}/>
                        <Source
                            id = {MapUtilities.IDs.COUNTY_SOURCE_ID + "CA"}
                            type="geojson"
                            data = {CACountyGeoData}
                            generateId = {true}/>
                        <Layer
                            id = {MapUtilities.IDs.COUNTY_FILL_LAYER_ID + "CA"}
                            type="fill"
                            source={MapUtilities.IDs.COUNTY_SOURCE_ID + "CA"}
                            paint={{
                            "fill-color" : "#abcdef",
                            "fill-opacity": [
                                'case',
                                ['boolean', ['feature-state', 'hover'], false],
                                .1,
                                .6,
                            ]
                            }}/>
                        <Source
                            id = {MapUtilities.IDs.COUNTY_SOURCE_ID + "TX"}
                            type="geojson"
                            data = {TXCountyGeoData}
                            generateId = {true}/>
                        <Layer
                            id = {MapUtilities.IDs.COUNTY_FILL_LAYER_ID + "TX"}
                            type="fill"
                            source={MapUtilities.IDs.COUNTY_SOURCE_ID + "TX"}
                            paint={{
                            "fill-color" : "#abcdef",
                            "fill-opacity": [
                                'case',
                                ['boolean', ['feature-state', 'hover'], false],
                                .1,
                                .6,
                            ]
                            }}
                        />
                 </ReactMapGL>
            </div>
        )
    }
}


const mapDispatchToProps = (dispatch) => {
    return {
        setCurrentState : (state) => {dispatch(setCurrentState(state))},
        setViewport : (viewport) => {dispatch(setViewport(viewport))},
        setTentativeState : (state) => {dispatch(setTentativeState(state))},
    }
  }
  
  const mapStateToProps = (state, ownProps) => {
    return {
        MapViewport : state.MapViewport
    }
  }
  
  export default connect(mapStateToProps, mapDispatchToProps)(StateSelectionMap);