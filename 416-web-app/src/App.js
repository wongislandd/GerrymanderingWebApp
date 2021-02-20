import './css/App.css';
import React, {useState} from "react"
import Toolbar from './components/Toolbar/MapToolbar';
import MapBoxComponent from './components/Map/MapBoxComponent';


function App() {  
  return (
    <div className = "full-screen-flex-container">
      <MapBoxComponent/>
      <Toolbar/>
    </div>
  );
}

export default App;
