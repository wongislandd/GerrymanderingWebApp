import React, {Component, useState} from 'react'
import ReactMapGL, { Layer, Source } from "react-map-gl"
//import PrecinctGeoData from '../../data/NC/ReducedPrecinctGeoData.json'
import PrecinctGeoData from '../../data/NC/PrecinctGeoData.json'
import * as MapUtilities from '../../utilities/MapUtilities'
import { connect } from 'react-redux'
import { moveMouse, setFeaturedDistrict, setMouseEntered, setFeaturedPrecinct} from '../../redux/actions/settingActions'
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
      srcEvent: {offsetX, offsetY}
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
    return (<TooltipComponent/>)
  }


  render() {
    //console.log(this.props)
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
          >
            {this._renderTooltip()}
          <Source
            id = "PrecinctGeoData"
            type="geojson"
            data = {PrecinctGeoData} />,
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
            data = {this.props.CurrentDistricting.geoJson}/>
          <Layer
            id = {"district-fill-layer"}
            type="fill"
            source="DistrictGeoData"
            layout={{
              "visibility": this.props.DisplayDistricts ? "visible" : "none"
            }}
            paint={{
              "fill-color" : ["rgb",["get","rgb-R"], ["get","rgb-G"], ["get","rgb-B"]],
              "fill-opacity": .5
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
      setFeaturedPrecinct : (precinct) => {dispatch(setFeaturedPrecinct(precinct))}
  }
}

const mapStateToProps = (state, ownProps) => {
  return {
      DisplayPrecincts : state.DisplayPrecincts,
      DisplayDistricts : state.DisplayDistricts,
      CurrentDistricting : state.CurrentDistricting,
      MouseX : state.MouseX,
      MouseY : state.MouseY
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(MapBoxComponent);