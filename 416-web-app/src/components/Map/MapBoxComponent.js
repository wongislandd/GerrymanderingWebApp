import React, {useState} from 'react'
import ReactMapGL, { Layer, Source, MapContext } from "react-map-gl"
import PrecinctGeoData from '../../data/NC/PrecinctGeoData.json'
import DistrictGeoData from '../../data/NC/EnactedDistrictingPlan.json'

function MapBoxComponent(props) {
  const [viewport, setViewport] = useState({
    latitude : 35.490477690914446, 
    longitude: -79.41601173255576,
    width: "75vw",
    height: "100vh",
    zoom: 6.75
  })
  
  /* Default state for display mode, "P" || "D" || "PD" || ""*/
  const [displaymode, setDisplayMode] = useState("PD")

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
          id = {"precinct-layer"}
          type="fill"
          source="PrecinctGeoData"
          paint={{
            /* Access the RGB properties of each individual feature */
            "fill-color" : ["rgb",["get","rgb-R"], ["get","rgb-G"], ["get","rgb-B"]],
            "fill-opacity": displaymode.includes("P") ? .35 : 0
          }}/>
      <Source
        id = "DistrictGeoData"
        type = "geojson"
        data = {DistrictGeoData}/>
      <Layer
          id = {"district-layer"}
          type = "line"
          source="DistrictGeoData"
          paint={{
            /* Access the RGB properties of each individual feature */
            "line-color" : "#000000",
            /* Check for the current display mode to see whether or not to display the districtings*/
            "line-opacity": displaymode.includes("D") ? 1 : 0
          }}/>
      </ReactMapGL>
      </div>
  )
}

export default MapBoxComponent;