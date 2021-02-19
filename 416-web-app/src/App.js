import './css/App.css';
import ReactMapGL from "react-map-gl"
import React, {useState} from "react"
import MapSidebar from './components/MapToolbar';


function App() {
  const [viewport, setViewport] = useState({
    longitude: -73.11563514995987,
    latitude : 40.914992863470744, 
    width: "80vw",
    height: "100vh",
    zoom: 10
  })

  
  return (
    <div className = "primary-flex-container">
      <ReactMapGL 
      className = "map-display"
      {...viewport} 
      mapboxApiAccessToken = {process.env.REACT_APP_MAPBOX_TOKEN}
      onViewportChange={viewport=> {
        setViewport(viewport)
      }}
      >
      </ReactMapGL>
      <MapSidebar/>
    </div>
  );
}

export default App;
