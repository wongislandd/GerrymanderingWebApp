import React, {useState} from 'react'
import ReactMapGL, { Layer, Source } from "react-map-gl"
//import PrecinctGeoData from '../../data/NC/ReducedPrecinctGeoData.json'
import PrecinctGeoData from '../../data/NC/PrecinctGeoData.json'
import * as MapUtilities from '../../utilities/MapUtilities'
import { connect } from 'react-redux'


function MapBoxComponent(props) {
  const [viewport, setViewport] = useState({
    latitude : MapUtilities.NC.LATTITUDE, 
    longitude: MapUtilities.NC.LONGITUDE,
    width: "75vw",
    height: "100vh",
    zoom: 6.75
  })

  return (
      <div>
      <ReactMapGL 
        className = "map-display"
        {...viewport} 
        mapboxApiAccessToken = {process.env.REACT_APP_MAPBOX_TOKEN}
        onViewportChange={viewport=> {
          setViewport(viewport)
        }}
      >
      {/* <Source
        id = "PrecinctGeoData"
        type="geojson"
        data = {PrecinctGeoData} />,
      <Layer
          id = {"precinct-fill-layer"}
          type="fill"
          source="PrecinctGeoData"
          layout={{
            "visibility": props.DisplayPrecincts && !props.DisplayDistricts ? "visible" : "none"
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
            "visibility": props.DisplayPrecincts ? "visible" : "none"
          }}
          paint={{
            "line-opacity": 1
          }}/> */}
      <Source
        id = "DistrictGeoData"
        type = "geojson"
        data = {props.CurrentDistricting.geoJson}/>
      <Layer
        id = {"district-fill-layer"}
        type="fill"
        source="DistrictGeoData"
        layout={{
          "visibility": props.DisplayDistricts ? "visible" : "none"
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
            "visibility": props.DisplayDistricts ? "visible" : "none"
          }}
          paint={{
            "line-opacity": 1
          }}/>
      </ReactMapGL>
      </div>
  )
}

const mapStateToProps = (state, ownProps) => {
  return {
      DisplayPrecincts : state.DisplayPrecincts,
      DisplayDistricts : state.DisplayDistricts,
      CurrentDistricting : state.CurrentDistricting
  }
}

export default connect(mapStateToProps)(MapBoxComponent);