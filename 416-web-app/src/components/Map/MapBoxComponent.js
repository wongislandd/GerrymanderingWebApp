import React, {useState} from 'react'
import ReactMapGL, { Layer, Source } from "react-map-gl"
//import geoData from '../../data/TestGeoJson.json'
import geoData from '../../data/NorthCarolinaVotingPrecincts.json'

console.log(geoData)

function MapBoxComponent() {
  const [viewport, setViewport] = useState({
    latitude : 35.490477690914446, 
    longitude: -79.41601173255576,
    width: "75vw",
    height: "100vh",
    zoom: 6.75
  })


  return (
      <ReactMapGL 
      className = "map-display"
      {...viewport} 
      mapboxApiAccessToken = {process.env.REACT_APP_MAPBOX_TOKEN}
      onViewportChange={viewport=> {
        setViewport(viewport)
      }}
      >
      <Source
        id = "ncgeodata"
        type="geojson"
        data = {geoData} />,
      <Layer
          id = {"main-layer"}
          type="fill"
          source="ncgeodata"
          paint={{
            "fill-color" : ["rgb",["get","rgb-R"], ["get","rgb-G"], ["get","rgb-B"]],
            "fill-opacity": 0.35
          }}/>
      </ReactMapGL>
  )
}

export default MapBoxComponent;