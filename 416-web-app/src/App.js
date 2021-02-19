import './css/App.css';
import ReactMapGL from "react-map-gl"
import React, {useState} from "react"
import Toolbar from './components/Toolbar/MapToolbar';


function App() {
  const [viewport, setViewport] = useState({
    longitude: -73.11563514995987,
    latitude : 40.914992863470744, 
    width: "75vw",
    height: "100vh",
    zoom: 10
  })

  
  return (
    <div className = "full-screen-flex-container">
      <ReactMapGL 
      className = "map-display"
      {...viewport} 
      mapboxApiAccessToken = {process.env.REACT_APP_MAPBOX_TOKEN}
      onViewportChange={viewport=> {
        setViewport(viewport)
      }}
      >
      </ReactMapGL>
      <Toolbar/>
    </div>
  );
}

export default App;
