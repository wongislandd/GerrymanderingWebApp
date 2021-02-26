import React, {Component, useState} from 'react'
import ReactMapGL, { Layer, Source } from "react-map-gl"
//import PrecinctGeoData from '../../data/NC/ReducedPrecinctGeoData.json'
import PrecinctGeoData from '../../data/NC/PrecinctGeoData.json'
import * as MapUtilities from '../../utilities/MapUtilities'
import { connect } from 'react-redux'
import { moveMouse, setFeaturedDistrict, setMouseEntered, setFeaturedPrecinct, setMapReference} from '../../redux/actions/settingActions'
import TooltipComponent from './TooltipComponent'

class MapBoxComponent extends Component{
  constructor(props) {
    super(props)
    this.state = {
      viewport : {
        latitude : MapUtilities.NC.LATTITUDE, 
        longitude: MapUtilities.NC.LONGITUDE,
        width: "75vw",
        height: window.innerHeight,
        zoom: 6.75
      }
    }
  }

  setViewport(viewport) {
    this.setState({
      viewport : viewport
    })
  }

  

  //Hover
  _onHover = event => {
    const {
      features,
    } = event;
    /* This finds what feature is being hovered over*/
    if (this.props.DisplayDistricts){
      const hoveredFeature = features && features.find(f => f.layer.id === 'district-fill-layer')
      this.props.setFeaturedDistrict(hoveredFeature)
    } else if(this.props.DisplayPrecincts) {
      const hoveredFeature = features && features.find(f => f.layer.id === 'precinct-fill-layer')
      this.props.setFeaturedPrecinct(hoveredFeature)
    }
    this._renderTooltip();
  };

  _renderTooltip() {
    return <TooltipComponent/>
  }

  /* Can't use Map reference until AFTER it's mounted, otherwise no guarentee it's set yet. */
  componentDidMount(){
    console.log(this.props.MapRef.current.getMap())
  }

  render() {
    return (
        <div 
          onMouseMove={(e) => this.props.moveMouse(e)}
          onMouseEnter={(e) => this.props.setMouseEntered(true)}
          onMouseLeave={(e) => this.props.setMouseEntered(false)}
        >
          <ReactMapGL 
            className = "map-display"
            {...this.state.viewport} 
            mapboxApiAccessToken = {process.env.REACT_APP_MAPBOX_TOKEN}
            onViewportChange={viewport=> {
              this.setViewport(viewport)
            }}
            onHover={this._onHover.bind(this)}
            // Tie this reference to the one in the state
            ref = {this.props.MapRef}
          >
            {this._renderTooltip()}
          <Source
            id = "PrecinctGeoData"
            type="geojson"
            data = {PrecinctGeoData} 
            generateId = {true}/>,
          <Layer
              id = {"precinct-fill-layer"}
              type="fill"
              source="PrecinctGeoData"
              layout={{
                "visibility": this.props.DisplayPrecincts && !this.props.DisplayDistricts ? "visible" : "none"
              }}
              paint={{
                "fill-color" : ["rgb",["get","rgb-R"], ["get","rgb-G"], ["get","rgb-B"]],
                "fill-opacity": .35
              }}/>
          <Layer
              id = {"precinct-outline-layer"}
              type="line"
              source="PrecinctGeoData"
              layout={{
                "visibility": this.props.DisplayPrecincts ? "visible" : "none"
              }}
              paint={{
                "line-opacity": 1
              }}/>
          <Source
            id = "DistrictGeoData"
            type = "geojson"
            data = {this.props.CurrentDistricting.geoJson}
            generateId = {true}/>
          <Layer
            id = {"district-fill-layer"}
            type="fill"
            source="DistrictGeoData"
            layout={{
              "visibility": this.props.DisplayDistricts ? "visible" : "none"
            }}
            paint={{
              "fill-color" : ["rgb",["get","rgb-R"], ["get","rgb-G"], ["get","rgb-B"]],
              "fill-opacity": [
                'case',
                ['boolean', ['feature-state', 'hover'], false],
                1.0,
                .5,
              ]
            }}/>
          <Layer
              id = {"district-outline-layer"}
              type = "line"
              source="DistrictGeoData"
              layout={{
                "visibility": this.props.DisplayDistricts ? "visible" : "none"
              }}
              paint={{
                "line-opacity": 1
              }}/>
          </ReactMapGL>
          </div>
      )
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
      moveMouse : (event) => {dispatch(moveMouse(event))},
      setMouseEntered : (bool) => {dispatch(setMouseEntered(bool))},
      setFeaturedDistrict : (district) => {dispatch(setFeaturedDistrict(district))},
      setFeaturedPrecinct : (precinct) => {dispatch(setFeaturedPrecinct(precinct))},
  }
}

const mapStateToProps = (state, ownProps) => {
  return {
      DisplayPrecincts : state.DisplayPrecincts,
      DisplayDistricts : state.DisplayDistricts,
      CurrentDistricting : state.CurrentDistricting,
      MouseX : state.MouseX,
      MouseY : state.MouseY,
      MapRef : state.MapRef
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(MapBoxComponent);