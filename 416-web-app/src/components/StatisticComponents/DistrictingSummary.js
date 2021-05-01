import React, { Component } from "react";
import { Collapsible, CollapsibleItem } from "react-materialize";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
} from "@material-ui/core";
import { connect } from "react-redux";
import {
  addFeatureToHighlight,
  removeFeatureHighlighting,
  setCurrentState,
  setFeaturedDistrict,
  setFeaturedPrecinct,
  setStatShowcasedDistrictID,
  setViewport,
} from "../../redux/actions/settingActions";
import * as MapUtilities from "../../utilities/MapUtilities";
import * as StatUtilities from "../../utilities/StatUtilities";
import * as ViewportUtilities from "../../utilities/ViewportUtilities";
import PartyPieChart from "./PartyPieChart";
import RacialPieChart from "./RacialPieChart";
import LabelAndInfoIcon from "./LabelAndInfoIcon";
import ReactMapGL, { Layer, Source } from "react-map-gl";
import BoxPlot from "./BoxPlot";
import ObjectiveFunctionTable from "./ObjectiveFunctionTable";
import VoterDemographicsTable from "./VoterDemographicsTable";
import RacialDemographicsTable from "./RacialDemographicsTable";

class DistrictingSummary extends Component {
  constructor(props) {
    super(props);
  }

  /* This guarentees that only the StatShowcasedDistrictID is open. This is a fix to some weird issues
    where a collapsible item would be technically closed (not active) but still had styling as if it
    were open */
  closeAllCollapsibles() {
    let collapsible = document.getElementById("stat-collapsible");
    if (collapsible != null) {
      let items = collapsible.getElementsByTagName("li");
      for (let i = 0; i < items.length; ++i) {
        let collapsiblebodies = items[i].getElementsByClassName(
          "collapsible-body"
        );
        for (let j = 0; j < collapsiblebodies.length; ++j) {
          collapsiblebodies[j].removeAttribute("style");
        }
      }
    }
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

  componentDidMount() {
    this.recenterMap();
  }

  render() {
    /* Don't want this behavior for the Selection Listing usage, since it's mostly meant for clicking on the map.*/
    if (!this.props.InSelectionMenu) {
      this.closeAllCollapsibles();
    }
    return (
      <Collapsible
        className="stat-window"
        id="stat-collapsible"
        accordion={false}
      >
        {this.props.InSelectionMenu ? (
          <div>
            <h6 className="title-text">Preview</h6>
            <ReactMapGL
              className="map-display"
              longitude={this.props.MapViewport.longitude}
              latitude={this.props.MapViewport.latitude}
              zoom={4.8}
              width="100%"
              height="300px"
              mapboxApiAccessToken={process.env.REACT_APP_MAPBOX_TOKEN}
            >
              <Source
                id={MapUtilities.IDs.DISTRICT_SOURCE_ID}
                type="geojson"
                data={this.props.DistrictingToDisplay.geoJson}
              />
              <Layer
                id={MapUtilities.IDs.DISTRICT_FILL_LAYER_ID}
                type="fill"
                source={MapUtilities.IDs.DISTRICT_SOURCE_ID}
                paint={{
                  "fill-color": [
                    "rgb",
                    ["get", "rgb-R"],
                    ["get", "rgb-G"],
                    ["get", "rgb-B"],
                  ],
                  "fill-opacity": 0.5,
                }}
              />
              <Layer
                id={MapUtilities.IDs.DISTRICT_LINE_LAYER_ID}
                type="line"
                source={MapUtilities.IDs.DISTRICT_SOURCE_ID}
                paint={{
                  "line-opacity": 1,
                }}
              />
            </ReactMapGL>
          </div>
        ) : (
          <div />
        )}
        <div>
          <h6 className="title-text centerWithinMe">Box and Whisker</h6>
          <BoxPlot DistrictingToDisplay={this.props.DistrictingToDisplay} />
        </div>

        <CollapsibleItem
          expanded={false}
          key={-1}
          header={"Objective Function Details"}
          onSelect={() => {}}
        >
          <ObjectiveFunctionTable
            DistrictingToDisplay={this.props.DistrictingToDisplay}
          />
        </CollapsibleItem>

        {this.props.DistrictingToDisplay.geoJson.features.map(
          (feature, key) => {
            return (
              <CollapsibleItem
                /* The key tells the highlighting engine how to identify the feature 
                         This will work so long as the key matches the feature's ID in the visual object
                         that the map renders, which I think it always will since it's in order. */
                onMouseEnter={(e) => {
                  if (!this.props.InSelectionMenu) {
                    feature.id = key;
                    this.props.addFeatureToHighlight(feature);
                  }
                }}
                onMouseLeave={(e) => {
                  if (!this.props.InSelectionMenu) {
                    feature.id = key;
                    this.props.removeFeatureHighlighting(feature);
                  }
                }}
                onClick={(e) => {
                  /* The state refreshing along with the expanded attribute cause some buggy behavior
                            where the collapsible will be open but seen as closed, this is a price we can pay to
                            trade off for clicking on a district to display stats, but on anywhere else this is being
                            used we don't need that behavior. */
                  if (!this.props.InSelectionMenu) {
                    /* If they close the already open one. */
                    if (key == this.props.StatShowcasedDistrictID) {
                      this.props.setStatShowcasedDistrictID(null);
                    } else {
                      this.props.setStatShowcasedDistrictID(key);
                    }
                  }
                }}
                expanded={
                  this.props.InSelectionMenu
                    ? false
                    : this.props.StatShowcasedDistrictID == null
                    ? false
                    : this.props.StatShowcasedDistrictID == key
                }
                key={key}
                header={"District " + feature.properties["District"]}
                style={{
                  backgroundColor:
                    "rgba(" +
                    feature.properties["rgb-R"] +
                    "," +
                    feature.properties["rgb-G"] +
                    "," +
                    feature.properties["rgb-B"] +
                    "," +
                    MapUtilities.VALUES.UNHIGHLIGHTED_DISTRICT_OPACITY +
                    ")",
                }}
              >
                <h5>Voter Demographics</h5>

                <VoterDemographicsTable DistrictToDisplay={feature} />

                <h5>Racial Demographics</h5>

                <RacialDemographicsTable DistrictToDisplay={feature} />

                <div className="demographicsContainer">
                  <PartyPieChart feature={feature} />
                  <RacialPieChart feature={feature} />
                </div>

                <h5>Objective Function Details</h5>
                <ObjectiveFunctionTable DistrictToDisplay={feature} />
              </CollapsibleItem>
            );
          }
        )}
      </Collapsible>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    setFeaturedDistrict: (district) => {
      dispatch(setFeaturedDistrict(district));
    },
    setFeaturedPrecinct: (precinct) => {
      dispatch(setFeaturedPrecinct(precinct));
    },
    addFeatureToHighlight: (feature) => {
      dispatch(addFeatureToHighlight(feature));
    },
    removeFeatureHighlighting: (feature) => {
      dispatch(removeFeatureHighlighting(feature));
    },
    setStatShowcasedDistrictID: (districtID) => {
      dispatch(setStatShowcasedDistrictID(districtID));
    },
    setCurrentState: (state) => {
      dispatch(setCurrentState(state));
    },
    setViewport: (viewport) => {
      dispatch(setViewport(viewport));
    },
  };
};

const mapStateToProps = (state, ownProps) => {
  return {
    DisplayPrecincts: state.DisplayPrecincts,
    DisplayDistricts: state.DisplayDistricts,
    StatShowcasedDistrictID: state.StatShowcasedDistrictID,
    InSelectionMenu: state.InSelectionMenu,
    MapViewport: state.MapViewport,
    CurrentState: state.CurrentState,
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(DistrictingSummary);
