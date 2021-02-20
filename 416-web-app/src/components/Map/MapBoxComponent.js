import React, {useState} from 'react'
import ReactMapGL, { Layer, Source } from "react-map-gl"
import geoData from '../../data/NorthCarolinaVotingPrecincts.json'

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
          id="north-carolina-geodata"
          type="geojson"
          data = {geoData} />
        <Layer
          id="anything"
          type="fill"
          source="north-carolina-geodata"
          paint={{
            "fill-color" : "#228b22",
            "fill-opacity": 0.35
          }}/>
      </ReactMapGL>
  )
}

export default MapBoxComponent;