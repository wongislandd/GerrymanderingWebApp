import React, {useState} from 'react'
import ReactMapGL, { Layer, Source, MapContext } from "react-map-gl"
import PrecinctGeoData from '../../data/NC/PrecinctGeoData.json'
import DistrictGeoData from '../../data/NC/EnactedDistrictingPlanColored.json'
import { connect } from 'react-redux'


function MapBoxComponent(props) {
  const [viewport, setViewport] = useState({
    latitude : 35.490477690914446, 
    longitude: -79.41601173255576,
    width: "75vw",
    height: "100vh",
    zoom: 6.75
  })

  console.log(props)
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
      <Source
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
            /* Access the RGB properties of each individual feature */
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
            "line-color" : "#000000",
            /* Check for the current display mode to see whether or not to display the districtings*/
            "line-opacity": 1
          }}/>
      <Source
        id = "DistrictGeoData"
        type = "geojson"
        data = {DistrictGeoData}/>
      <Layer
        id = {"district-fill-layer"}
        type="fill"
        source="DistrictGeoData"
        layout={{
          "visibility": props.DisplayDistricts ? "visible" : "none"
        }}
        paint={{
          /* 0 for now until we have more district layer functionality */
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
            "line-color" : "#000000",
            /* Check for the current display mode to see whether or not to display the districtings*/
            "line-opacity": 1
          }}/>
      </ReactMapGL>
      </div>
  )
}

const mapStateToProps = (state, ownProps) => {
  return {
      DisplayPrecincts : state.DisplayPrecincts,
      DisplayDistricts : state.DisplayDistricts
  }
}
export default connect(mapStateToProps)(MapBoxComponent);