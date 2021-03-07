import React, {Component, useState} from 'react'
import ReactMapGL, { Layer, Source } from "react-map-gl"
//import PrecinctGeoData from '../../data/NC/ReducedPrecinctGeoData.json'
import PrecinctGeoData from '../../data/NC/PrecinctGeoDataOutput.json'
import CountyGeoData from '../../data/NC/CountiesGeoData.json'
import * as MapUtilities from '../../utilities/MapUtilities'
import { connect } from 'react-redux'
import { moveMouse, setFeaturedDistrict, setMouseEntered, setFeaturedPrecinct, setMapReference, setLoadedStatus, setInSelectionMenu, addFeatureToHighlight, resetAllHighlighting} from '../../redux/actions/settingActions'
import TooltipComponent from './TooltipComponent'
import MapIcon from '@material-ui/icons/Map';

class MapBoxComponent extends Component{
  constructor(props) {
    super(props)
    this.state = {
      viewport : {
        latitude : MapUtilities.NC.LATTITUDE, 
        longitude: MapUtilities.NC.LONGITUDE,
        width: "75vw",
        height: window.innerHeight,
        zoom: 6.5 
      }
    }
  }

  /* Update viewport on change */
  setViewport(viewport) {
    this.setState({
      viewport : viewport
    })
  }

  recenterMap() {
    this.setState({
      viewport : {
        latitude : MapUtilities.NC.LATTITUDE, 
        longitude: MapUtilities.NC.LONGITUDE,
        width: "75vw",
        height: window.innerHeight,
        zoom: 6.5
      }
    })
  }

  unhighlightFeatures = () => {
    const map = this.props.MapRef.current.getMap()
    this.props.FeaturesToUnhighlight.forEach(feature => {
      let source = this.props.DisplayDistricts ? MapUtilities.IDs.DISTRICT_SOURCE_ID : MapUtilities.IDs.PRECINCT_SOURCE_ID
      map.setFeatureState({
        source : source,
        id : feature.id
      }, {
          hover : false
      })
    })
  }

  highlightFeatures = () => {
    const map = this.props.MapRef.current.getMap()
    this.props.FeaturesToHighlight.forEach(feature => {
      console.log(feature)
      let source = this.props.DisplayDistricts ? MapUtilities.IDs.DISTRICT_SOURCE_ID : MapUtilities.IDs.PRECINCT_SOURCE_ID
        map.setFeatureState({
            source : source,
            id : feature.id
        }, {
            hover : true
        })
    });
  }


  _onHover = event => {
    const {
      features,
    } = event;
    if (this.props.DisplayDistricts){
      this.props.resetAllHighlighting()
      // Identify the newly featured district
      const hoveredFeature = features && features.find(f => f.layer.id === MapUtilities.IDs.DISTRICT_FILL_LAYER_ID)
      this.props.setFeaturedDistrict(hoveredFeature)
      if (hoveredFeature != undefined) {
        this.props.addFeatureToHighlight(hoveredFeature)
      }
    } else if(this.props.DisplayPrecincts) {
      this.props.resetAllHighlighting()
      // Identify the newly featured precinct
      const hoveredFeature = features && features.find(f => f.layer.id === MapUtilities.IDs.PRECINCT_FILL_LAYER_ID)
      this.props.setFeaturedPrecinct(hoveredFeature)
      if (hoveredFeature != undefined) {
        this.props.addFeatureToHighlight(hoveredFeature)
      }
    }
    this._renderTooltip();
  };

  _renderTooltip() {
    return <TooltipComponent/>
  }

  /* Can't use Map reference until AFTER it's mounted, otherwise no guarentee it's set yet.
  Abstracted to Loaded property of the state for checking 
  Access map through this.state.MapRef.current.getMap()*/
  componentDidMount(){
    console.log("Map Reference has been set.")
    this.props.setLoadedStatus(true)
  }

  render() {
    /* If the map reference is loaded, keep track of what to highlight and unhighlight */
    if (this.props.Loaded) {
      this.unhighlightFeatures()
      this.highlightFeatures()
    }
    return (
        <div 
          onMouseMove={(e) => this.props.moveMouse(e)}
          onMouseEnter={(e) => this.props.setMouseEntered(true)}
          onMouseLeave={(e) => this.props.setMouseEntered(false)}
        >
          {/* Tooltip on the top left to show what's currently being viewed. */}
          <div className="currentDistrictingNameSidebar" onClick={(e)=>this.recenterMap()}>
            <div className="iconAndLabel">
            <MapIcon/>
            <span onClick={(e) => (e)=>this.props.setInSelectionMenu(true)}>
            {this.props.CurrentDistricting.name}
            </span>
              </div>
          </div>
          {/* Option to enter the selection menu */}
          <div className="viewAndFilterDistrictingsOption" onClick={(e)=>{this.props.setInSelectionMenu(true)}}>
            {MapUtilities.MESSAGES.ViewAndFilterDistrictingsMsg}
          </div>
          <ReactMapGL 
            className = "map-display"
            {...this.state.viewport} 
            mapboxApiAccessToken = {process.env.REACT_APP_MAPBOX_TOKEN}
            onViewportChange={viewport=> {
              this.setViewport(viewport)
            }}
            onHover={this._onHover.bind(this)}
            // Tie this reference to the one in the state
            ref = {this.props.MapRef}
          >
            {this._renderTooltip()}
          <Source
            id = {MapUtilities.IDs.PRECINCT_SOURCE_ID}
            type="geojson"
            data = {PrecinctGeoData} 
            generateId = {true}/>,
          <Layer
              id = {MapUtilities.IDs.PRECINCT_FILL_LAYER_ID}
              type="fill"
              source={MapUtilities.IDs.PRECINCT_SOURCE_ID}
              layout={{
                "visibility": this.props.DisplayPrecincts && !this.props.DisplayDistricts ? "visible" : "none"
              }}
              paint={{
                "fill-color" : ["rgb",["get","rgb-R"], ["get","rgb-G"], ["get","rgb-B"]],
                "fill-opacity": [
                  'case',
                  ['boolean', ['feature-state', 'hover'], false],
                  1,
                  MapUtilities.VALUES.UNHIGHLIGHTED_DISTRICT_OPACITY,
                ]
              }}/>
          <Layer
              id = {MapUtilities.IDs.PRECINCT_LINE_LAYER_ID}
              type="line"
              source={MapUtilities.IDs.PRECINCT_SOURCE_ID}
              layout={{
                "visibility": this.props.DisplayPrecincts ? "visible" : "none"
              }}
              paint={{
                "line-opacity": 1
              }}/>
          <Source
            id = {MapUtilities.IDs.COUNTY_SOURCE_ID}
            type="geojson"
            data = {CountyGeoData}
            generateId = {true}/>
          <Layer
            id = {MapUtilities.IDs.COUNTY_FILL_LAYER_ID}
            type="fill"
            source={MapUtilities.IDs.COUNTY_SOURCE_ID}
            layout={{
              "visibility": this.props.DisplayCounties ? "visible" : "none"
            }}
            paint={{
              "fill-color" : ["rgb",["get","rgb-R"], ["get","rgb-G"], ["get","rgb-B"]],
              "fill-opacity": [
                'case',
                ['boolean', ['feature-state', 'hover'], false],
                .6,
                .3,
              ]
            }}/>
          <Layer
              id = {MapUtilities.IDs.COUNTY_LINE_LAYER_ID}
              type = "line"
              source={MapUtilities.IDs.COUNTY_SOURCE_ID}
              layout={{
                "visibility": this.props.DisplayCounties ? "visible" : "none"
              }}
              paint={{
                "line-opacity": 1
              }}/>
          <Source
            id = {MapUtilities.IDs.DISTRICT_SOURCE_ID}
            type = "geojson"
            data = {this.props.CurrentDistricting.geoJson}
            generateId = {true}/>
          <Layer
            id = {MapUtilities.IDs.DISTRICT_FILL_LAYER_ID}
            type="fill"
            source={MapUtilities.IDs.DISTRICT_SOURCE_ID}
            layout={{
              "visibility": this.props.DisplayDistricts ? "visible" : "none"
            }}
            paint={{
              "fill-color" : ["rgb",["get","rgb-R"], ["get","rgb-G"], ["get","rgb-B"]],
              "fill-opacity": [
                'case',
                ['boolean', ['feature-state', 'hover'], false],
                1.0,
                .5,
              ]
            }}/>
          <Layer
              id = {MapUtilities.IDs.DISTRICT_LINE_LAYER_ID}
              type = "line"
              source={MapUtilities.IDs.DISTRICT_SOURCE_ID}
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
      setFeaturedPrecinct : (precinct) => {dispatch(setFeaturedPrecinct(precinct))},
      addFeatureToHighlight : (feature) => {dispatch(addFeatureToHighlight(feature))},
      resetAllHighlighting : () => {dispatch(resetAllHighlighting())},
      setLoadedStatus : (bool) => {dispatch(setLoadedStatus(bool))},
      setInSelectionMenu : (bool) => {dispatch(setInSelectionMenu(bool))}
  }
}

const mapStateToProps = (state, ownProps) => {
  return {
      DisplayPrecincts : state.DisplayPrecincts,
      DisplayDistricts : state.DisplayDistricts,
      DisplayCounties : state.DisplayCounties,
      CurrentDistricting : state.CurrentDistricting,
      FeaturedDistrict : state.FeaturedDistrict,
      FeaturedPrecinct : state.FeaturedPrecinct,
      FeaturesToHighlight : state.FeaturesToHighlight,
      FeaturesToUnhighlight : state.FeaturesToUnhighlight,
      MouseX : state.MouseX,
      MouseY : state.MouseY,
      MapRef : state.MapRef,
      Loaded : state.Loaded,
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(MapBoxComponent);