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
import RacialPieChart from "./RacialPieChart";
import LabelAndInfoIcon from "./LabelAndInfoIcon";
import ReactMapGL, { Layer, Source } from "react-map-gl";
import BoxPlot from "./BoxPlot";
import ObjectiveFunctionTable from "./ObjectiveFunctionTable";
import RacialDemographicsTable from "./RacialDemographicsTable";
import NormalizedTable from "./NormalizedTable";

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
      case ViewportUtilities.STATE_OPTIONS.ALABAMA:
        newViewport = this.props.MinimizedMap
          ? ViewportUtilities.ALABAMA.Minimized
          : ViewportUtilities.ALABAMA.Maximized;
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
        {this.props.DistrictingToDisplay.enacted ? (
          <div />
        ) :
        <div className="centerWithinMe">
          <BoxPlot districtingId = {this.props.DistrictingToDisplay.id}/>
          <h5>Minority Deviation from Average</h5>
          <div className="padBelowMe">{StatUtilities.formatAsPercentage(this.props.DistrictingToDisplay.measures.minorityDeviationFromAvg, 2)}</div>
        </div>
        }

        {this.props.DistrictingToDisplay.enacted ? (
          <div />
        ) : (
          <CollapsibleItem
            expanded={false}
            key={-1}
            header={"Objective Function Details"}
            onSelect={() => {}}
          >
            <h5 className="title-text">Normalized Values</h5>
            <p>These values are used to create the objective function score.</p>
            <p>Values were normalized by the min and max of the weighted set.</p>
            <NormalizedTable
              DistrictingToDisplay={this.props.DistrictingToDisplay}
            />
            <h5 className="title-text">Raw Values</h5>
            <p>These values represent the calculated measures.</p>
            <ObjectiveFunctionTable
              DistrictingToDisplay={this.props.DistrictingToDisplay}
            />
          </CollapsibleItem>
        )}
        {this.props.DistrictingToDisplay.districtSummaries.map(
          (district, key) => {
            return (
              <CollapsibleItem
                /* The key tells the highlighting engine how to identify the feature 
                         This will work so long as the key matches the feature's ID in the visual object
                         that the map renders, which I think it always will since it's in order. */
                onMouseEnter={(e) => {
                  if (!this.props.InSelectionMenu) {
                    this.props.addFeatureToHighlight(district);
                  }
                }}
                onMouseLeave={(e) => {
                  if (!this.props.InSelectionMenu) {
                    this.props.removeFeatureHighlighting(district);
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
                    : this.props.StatShowcasedDistrictID == district.districtNumber-1
                }
                key={key}
                header={"District " + district.districtNumber}
              >
                <h5>Racial Demographics</h5>

                <RacialDemographicsTable DistrictToDisplay={district} />

                <div className="demographicsContainer">
                  <RacialPieChart district={district} />
                </div>

                {this.props.DistrictingToDisplay.enacted ? (
                  <div></div>
                ) : (
                  <div>
                    <h5>Objective Function Details</h5>
                    <ObjectiveFunctionTable DistrictToDisplay={district} />
                  </div>
                )}
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
