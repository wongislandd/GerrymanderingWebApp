import React, { Component, useState } from "react";
import ReactMapGL, { Layer, Source } from "react-map-gl";
import * as MapUtilities from "../../utilities/MapUtilities";
import { connect } from "react-redux";
import {
  moveMouse,
  setFeaturedDistrict,
  setMouseEntered,
  setFeaturedPrecinct,
  setMinimizedMap,
  setLoadedStatus,
  setInSelectionMenu,
  addFeatureToHighlight,
  resetAllHighlighting,
  setViewport,
  setCurrentState,
  setStatShowcasedDistrictID,
} from "../../redux/actions/settingActions";
import DistrictTooltip from "./DistrictTooltip";
import PrecinctTooltip from "./PrecinctTooltip";
import MapIcon from "@material-ui/icons/Map";
import { Icon } from "react-materialize";
import * as ViewportUtilities from "../../utilities/ViewportUtilities";

class MapBoxComponent extends Component {
  constructor(props) {
    super(props);
  }

  /* Recenter the map to the default state's location (minimized or maximized)*/
  recenterMap() {
    let newViewport = null;
    switch (this.props.CurrentState) {
      case ViewportUtilities.STATE_OPTIONS.NORTH_CAROLINA:
        newViewport = this.props.MinimizedMap
          ? ViewportUtilities.NORTH_CAROLINA.Minimized
          : ViewportUtilities.NORTH_CAROLINA.Maximized;
        break;
      case ViewportUtilities.STATE_OPTIONS.TEXAS:
        newViewport = this.props.MinimizedMap
          ? ViewportUtilities.TEXAS.Minimized
          : ViewportUtilities.TEXAS.Maximized;
        break;
      case ViewportUtilities.STATE_OPTIONS.LOUISIANA:
        newViewport = this.props.MinimizedMap
          ? ViewportUtilities.LOUISIANA.Minimized
          : ViewportUtilities.LOUISIANA.Maximized;
        break;
      default:
        newViewport = this.props.MinimizedMap
          ? ViewportUtilities.UNSELECTED.Minimized
          : ViewportUtilities.UNSELECTED.Maximized;
    }
    this.props.setViewport(newViewport);
  }

  /* If there are features to unhighlight, unhighlight them (kind of like a queue)*/
  unhighlightFeatures = () => {
    const map = this.props.MapRef.current.getMap();
    this.props.FeaturesToUnhighlight.forEach((feature) => {
      let source = this.props.DisplayDistricts
        ? MapUtilities.IDs.DISTRICT_SOURCE_ID
        : MapUtilities.IDs.PRECINCT_SOURCE_ID;
      map.setFeatureState(
        {
          source: source,
          id: feature.id,
        },
        {
          hover: false,
        }
      );
    });
  };

  /* If there are features to highlight, highlight them*/
  highlightFeatures = () => {
    const map = this.props.MapRef.current.getMap();
    this.props.FeaturesToHighlight.forEach((feature) => {
      let source = this.props.DisplayDistricts
        ? MapUtilities.IDs.DISTRICT_SOURCE_ID
        : MapUtilities.IDs.PRECINCT_SOURCE_ID;
      map.setFeatureState(
        {
          source: source,
          id: feature.id,
        },
        {
          hover: true,
        }
      );
    });
  };

  /* OnHover behavior responsible for tooltip and setting the currently featured district or feature */
  _onHover = (event) => {
    const { features } = event;
    if (this.props.DisplayDistricts) {
      this.props.resetAllHighlighting();
      // Identify the newly featured district
      const hoveredFeature =
        features &&
        features.find(
          (f) => f.layer.id === MapUtilities.IDs.DISTRICT_FILL_LAYER_ID
        );
      this.props.setFeaturedDistrict(hoveredFeature);
      if (hoveredFeature != undefined) {
        this.props.addFeatureToHighlight(hoveredFeature);
      }
    } else if (this.props.DisplayPrecincts) {
      this.props.resetAllHighlighting();
      // Identify the newly featured precinct
      const hoveredFeature =
        features &&
        features.find(
          (f) => f.layer.id === MapUtilities.IDs.PRECINCT_FILL_LAYER_ID
        );
      this.props.setFeaturedPrecinct(hoveredFeature);
      if (hoveredFeature != undefined) {
        this.props.addFeatureToHighlight(hoveredFeature);
      }
    }
    this._renderTooltip();
  };

  /* Open the corresponding stat window on click*/
  _onClick = (event) => {
    const { features } = event;
    if (this.props.DisplayDistricts) {
      this.props.resetAllHighlighting();
      // Identify the newly featured district
      const hoveredFeature =
        features &&
        features.find(
          (f) => f.layer.id === MapUtilities.IDs.DISTRICT_FILL_LAYER_ID
        );
      if (hoveredFeature != undefined) {
        this.props.setStatShowcasedDistrictID(hoveredFeature.id);
      }
    }
  };

  _renderTooltip() {
    return this.props.DisplayDistricts ? (
      <DistrictTooltip />
    ) : this.props.DisplayPrecincts ? (
      <PrecinctTooltip />
    ) : (
      <div />
    );
  }

  /* Can't use Map reference until AFTER it's mounted, otherwise no guarentee it's set yet.
  Abstracted to Loaded property of the state for checking 
  Access map through this.state.MapRef.current.getMap()*/
  componentDidMount() {
    console.log("Map Reference has been set.");
    this.props.setLoadedStatus(true);
  }

  /* Set loaded to false when unmounting */
  componentWillUnmount() {
    this.props.setLoadedStatus(false);
  }

  render() {
    /* If the map reference is loaded, keep track of what to highlight and unhighlight */
    if (this.props.Loaded && this.props.MapRef.current.getMap().style._loaded) {
      this.unhighlightFeatures();
      this.highlightFeatures();
    }
    return (
      <div
        onMouseMove={(e) => this.props.moveMouse(e)}
        onMouseEnter={(e) => this.props.setMouseEntered(true)}
        onMouseLeave={(e) => this.props.setMouseEntered(false)}
      >
        {/* Tooltip on the top left to show what's currently being viewed. */}
        <div
          className="currentDistrictingNameSidebar"
          onClick={(e) => this.recenterMap()}
        >
          <div className="iconAndLabel">
            <MapIcon />
            <span onClick={(e) => (e) => this.props.setInSelectionMenu(true)}>
              {this.props.CurrentDistrictingGeoJson != null ? this.props.CurrentDistrictingGeoJson.name : "Loading districting."}
            </span>
          </div>
        </div>
        {/* Option to enter the selection menu */}
        <div
          className="viewAndFilterDistrictingsOption"
          onClick={(e) => {
            this.props.setInSelectionMenu(true);
          }}
        >
          <div className="iconAndLabel">
            <Icon>code</Icon>
            <span>{MapUtilities.MESSAGES.ViewAndFilterDistrictingsMsg}</span>
          </div>
        </div>
        {/* Option to return to state selection */}
        <div
          className="returnToStateSelectionOption"
          onClick={(e) => {
            this.props.setCurrentState(
              ViewportUtilities.STATE_OPTIONS.UNSELECTED
            );
          }}
        >
          <div className="iconAndLabel">
            <Icon>arrow_back</Icon>
            <span>{MapUtilities.MESSAGES.ReturnToStateSelectionMsg}</span>
          </div>
        </div>
        <ReactMapGL
          className="map-display"
          {...this.props.MapViewport}
          mapboxApiAccessToken={process.env.REACT_APP_MAPBOX_TOKEN}
          onViewportChange={(viewport) => {
            this.props.setViewport(viewport);
          }}
          onHover={this._onHover.bind(this)}
          onClick={(e) => {
            this._onClick(e);
          }}
          // Tie this reference to the one in the state
          ref={this.props.MapRef}
        >
          {this._renderTooltip()}
          {this.props.PrecinctsGeoJson != null ? 
          <div>
          <Source
            id={MapUtilities.IDs.PRECINCT_SOURCE_ID}
            type="geojson"
            data={this.props.PrecinctsGeoJson}
            generateId={true}
          />

          <Layer
            id={MapUtilities.IDs.PRECINCT_FILL_LAYER_ID}
            type="fill"
            source={MapUtilities.IDs.PRECINCT_SOURCE_ID}
            layout={{
              visibility:
                this.props.DisplayPrecincts && !this.props.DisplayDistricts
                  ? "visible"
                  : "none",
            }}
            paint={{
              "fill-color": [
                "rgb",
                ["get", "rgb-R"],
                ["get", "rgb-G"],
                ["get", "rgb-B"],
              ],
              "fill-opacity": [
                "case",
                ["boolean", ["feature-state", "hover"], false],
                1,
                MapUtilities.VALUES.UNHIGHLIGHTED_DISTRICT_OPACITY,
              ],
            }}
          />
          <Layer
            id={MapUtilities.IDs.PRECINCT_LINE_LAYER_ID}
            type="line"
            source={MapUtilities.IDs.PRECINCT_SOURCE_ID}
            layout={{
              visibility: this.props.DisplayPrecincts ? "visible" : "none",
            }}
            paint={{
              "line-opacity": 1,
            }}
          /></div>
            :
            <div>

            </div>
            }
          {this.props.CountiesGeoJson != null ? <div>
            <Source
              id={MapUtilities.IDs.COUNTY_SOURCE_ID}
              type="geojson"
              data={this.props.CountiesGeoJson}
              generateId={true}
            />
            <Layer
              id={MapUtilities.IDs.COUNTY_FILL_LAYER_ID}
              type="fill"
              source={MapUtilities.IDs.COUNTY_SOURCE_ID}
              layout={{
                visibility: this.props.DisplayCounties ? "visible" : "none",
              }}
              paint={{
                "fill-color": "lightblue",
                "fill-opacity": [
                  "case",
                  ["boolean", ["feature-state", "hover"], false],
                  0.6,
                  0.3,
                ],
              }}
            />
            <Layer
              id={MapUtilities.IDs.COUNTY_LINE_LAYER_ID}
              type="line"
              source={MapUtilities.IDs.COUNTY_SOURCE_ID}
              layout={{
                visibility: this.props.DisplayCounties ? "visible" : "none",
              }}
              paint={{
                "line-opacity": 1,
              }}
            /></div> : <div></div>}
          {this.props.CurrentDistrictingGeoJson != null ? <div>
            <Source
              id={MapUtilities.IDs.DISTRICT_SOURCE_ID}
              type="geojson"
              data={this.props.CurrentDistrictingGeoJson}
              generateId={true}
            />
            <Layer
              id={MapUtilities.IDs.DISTRICT_FILL_LAYER_ID}
              type="fill"
              source={MapUtilities.IDs.DISTRICT_SOURCE_ID}
              layout={{
                visibility: this.props.DisplayDistricts ? "visible" : "none",
              }}
              paint={{
                "fill-color": [
                  "rgb",
                  ["get", "rgb-R"],
                  ["get", "rgb-G"],
                  ["get", "rgb-B"],
                ],
                "fill-opacity": [
                  "case",
                  ["boolean", ["feature-state", "hover"], false],
                  1.0,
                  0.5,
                ],
              }}
            />
            <Layer
              id={MapUtilities.IDs.DISTRICT_LINE_LAYER_ID}
              type="line"
              source={MapUtilities.IDs.DISTRICT_SOURCE_ID}
              layout={{
                visibility: this.props.DisplayDistricts ? "visible" : "none",
              }}
              paint={{
                "line-opacity": 1,
              }}
            /> </div>: <div/>}
        </ReactMapGL>
      </div>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    moveMouse: (event) => {
      dispatch(moveMouse(event));
    },
    setMouseEntered: (bool) => {
      dispatch(setMouseEntered(bool));
    },
    setFeaturedDistrict: (district) => {
      dispatch(setFeaturedDistrict(district));
    },
    setFeaturedPrecinct: (precinct) => {
      dispatch(setFeaturedPrecinct(precinct));
    },
    addFeatureToHighlight: (feature) => {
      dispatch(addFeatureToHighlight(feature));
    },
    resetAllHighlighting: () => {
      dispatch(resetAllHighlighting());
    },
    setLoadedStatus: (bool) => {
      dispatch(setLoadedStatus(bool));
    },
    setInSelectionMenu: (bool) => {
      dispatch(setInSelectionMenu(bool));
    },
    setMinimizedMap: (bool) => {
      dispatch(setMinimizedMap(bool));
    },
    setViewport: (viewport) => {
      dispatch(setViewport(viewport));
    },
    setCurrentState: (state) => {
      dispatch(setCurrentState(state));
    },
    setStatShowcasedDistrictID: (districtID) => {
      dispatch(setStatShowcasedDistrictID(districtID));
    },
  };
};

const mapStateToProps = (state, ownProps) => {
  return {
    DisplayPrecincts: state.DisplayPrecincts,
    DisplayDistricts: state.DisplayDistricts,
    DisplayCounties: state.DisplayCounties,
    ViewingDistrictDetails: state.ViewingDistrictDetails,
    CurrentDistrictingGeoJson: state.CurrentDistrictingGeoJson,
    CurrentState: state.CurrentState,
    FeaturedDistrict: state.FeaturedDistrict,
    FeaturedPrecinct: state.FeaturedPrecinct,
    FeaturesToHighlight: state.FeaturesToHighlight,
    FeaturesToUnhighlight: state.FeaturesToUnhighlight,
    MinimizedMap: state.MinimizedMap,
    MouseX: state.MouseX,
    MouseY: state.MouseY,
    MapRef: state.MapRef,
    MapViewport: state.MapViewport,
    Loaded: state.Loaded,
    PrecinctsGeoJson : state.PrecinctsGeoJson,
    CountiesGeoJson : state.CountiesGeoJson,
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(MapBoxComponent);
