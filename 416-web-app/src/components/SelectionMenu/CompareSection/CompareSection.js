import React, { Component } from "react";
import { Button, Row, Col, Icon } from "react-materialize";
import { connect } from "react-redux";
import {
  setComparisonDistrictingA,
  setComparisonDistrictingB,
} from "../../../redux/actions/settingActions";
import * as MapUtilities from "../../../utilities/MapUtilities";
import * as StatUtilities from "../../../utilities/StatUtilities";
import ComparisonItem from "./ComparisonItem";

class CompareSection extends Component {
  constructor(props) {
    super(props);
  }

  /* Dictionary where key is the label to display and value is the value to look up */
  statsToCompare = {
    "Population Equality": MapUtilities.PROPERTY_LABELS.AVG_POPULATION_EQUALITY,
    "Split Counties": MapUtilities.PROPERTY_LABELS.SPLIT_COUNTIES_SCORE,
    "Deviation from Average":
      MapUtilities.PROPERTY_LABELS.AVG_DEVIATION_FROM_AVG_DISTRICTING,
    "Deviation from Enacted":
      MapUtilities.PROPERTY_LABELS.AVG_DEVIATION_FROM_ENACTED_DISTRICTING,
    Compactness: MapUtilities.PROPERTY_LABELS.AVG_COMPACTNESS,
  };

  readyToCompare() {
    return (
      this.props.ComparisonDistrictingA != null &&
      this.props.ComparisonDistrictingB != null
    );
  }

  render() {
    return (
      <div className="SelectionMenuSection CompareSection centerWithinMe">
        <h5>Compare Districtings</h5>
        <div className="DistrictingHolderBox labelAndIcon">
          <span>
            Districting A:{" "}
            {this.props.ComparisonDistrictingA == null
              ? ""
              : this.props.ComparisonDistrictingA.name}{" "}
          </span>
          <Icon
            className={
              this.props.ComparisonDistrictingA == null ? "hideMe" : "cancelBtn"
            }
            onClick={(e) => this.props.setComparisonDistrictingA(null)}
          >
            cancel
          </Icon>
        </div>
        <div className="DistrictingHolderBox labelAndIcon">
          <span>
            Districting B:{" "}
            {this.props.ComparisonDistrictingB == null
              ? ""
              : this.props.ComparisonDistrictingB.name}{" "}
          </span>
          <Icon
            className={
              this.props.ComparisonDistrictingB == null ? "hideMe" : "cancelBtn"
            }
            onClick={(e) => this.props.setComparisonDistrictingB(null)}
          >
            cancel
          </Icon>
        </div>
        <div className="comparedStatsDiv centerWithinMe">
          <div className="DistrictingAStats">
            <h5>Districting A</h5>
            {!this.readyToCompare()
              ? ""
              : Object.keys(this.statsToCompare).map((key) => {
                  var thisDistrictingVal = this.props.ComparisonDistrictingA
                    .geoJson.objectivefunc[this.statsToCompare[key]];
                  var otherDistrictingVal = this.props.ComparisonDistrictingB
                    .geoJson.objectivefunc[this.statsToCompare[key]];
                  var difference = StatUtilities.getPercentageChange(
                    thisDistrictingVal,
                    otherDistrictingVal
                  );
                  return (
                    <ComparisonItem
                      key={key}
                      label={key}
                      direction={
                        difference == 0
                          ? StatUtilities.COMPARISON_DIRECTIONS.NONE
                          : difference > 0
                          ? StatUtilities.COMPARISON_DIRECTIONS.UP
                          : StatUtilities.COMPARISON_DIRECTIONS.DOWN
                      }
                      value={thisDistrictingVal}
                      pct={difference + "%"}
                    />
                  );
                })}
          </div>
          <div className="DistrictingBStats">
            <h5>Districting B</h5>
            {!this.readyToCompare()
              ? ""
              : Object.keys(this.statsToCompare).map((key) => {
                  var thisDistrictingVal = this.props.ComparisonDistrictingB
                    .geoJson.objectivefunc[this.statsToCompare[key]];
                  var otherDistrictingVal = this.props.ComparisonDistrictingA
                    .geoJson.objectivefunc[this.statsToCompare[key]];
                  var difference = StatUtilities.getPercentageChange(
                    thisDistrictingVal,
                    otherDistrictingVal
                  );
                  return (
                    <ComparisonItem
                      key={key}
                      label={key}
                      direction={
                        difference == 0
                          ? StatUtilities.COMPARISON_DIRECTIONS.NONE
                          : difference > 0
                          ? StatUtilities.COMPARISON_DIRECTIONS.UP
                          : StatUtilities.COMPARISON_DIRECTIONS.DOWN
                      }
                      value={thisDistrictingVal}
                      pct={difference + "%"}
                    />
                  );
                })}
          </div>
        </div>
      </div>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    setComparisonDistrictingA: (districting) => {
      dispatch(setComparisonDistrictingA(districting));
    },
    setComparisonDistrictingB: (districting) => {
      dispatch(setComparisonDistrictingB(districting));
    },
  };
};

const mapStateToProps = (state, ownProps) => {
  return {
    ComparisonDistrictingA: state.ComparisonDistrictingA,
    ComparisonDistrictingB: state.ComparisonDistrictingB,
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(CompareSection);
