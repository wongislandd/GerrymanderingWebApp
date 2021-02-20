import React, {useState} from 'react'
import ReactMapGL, { Layer, Source } from "react-map-gl"
//import geoData from '../../data/TestGeoJson.json'
import geoData from '../../data/NorthCarolinaVotingPrecincts.json'


function getRandomColor() {
  const randomBetween = (min, max) => min + Math.floor(Math.random() * (max - min + 1));
  const r = randomBetween(0, 255);
  const g = randomBetween(0, 255);
  const b = randomBetween(0, 255);
  const rgb = `rgb(${r},${g},${b})`;
  return rgb
}

function getRandomScalar() {
  return 1+Math.random()
}


const precincts = []

function populatePrecincts(){
  /** We will use the enr_desc, which I think is the precinct description, as a unique key to access
   *  the precincts.
   */

  for (var index in geoData.features) {
      var currentFeature = geoData.features[index];
      precincts.push(
      <Source
        id = {currentFeature.properties.enr_desc}
        type="geojson"
        data = {geoData.features[index]} />,
      <Layer
          id = {currentFeature.properties.enr_desc + "-layer"}
          type="fill"
          source={currentFeature.properties.enr_desc}
          paint={{
            "fill-color" : ["rgb",["get","fillcolor"], ["get","OBJECTID"], ["get","county_id"]],
            "fill-opacity": 0.35
          }}/>)
  }
}


//populatePrecincts()

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
            "fill-color" : ["rgb",["get","fillcolor"], ["get","OBJECTID"], ["get","county_id"]],
            "fill-opacity": 0.35
          }}/>
      </ReactMapGL>
  )
}

export default MapBoxComponent;