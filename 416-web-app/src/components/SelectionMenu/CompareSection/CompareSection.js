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
    "Deviation from Ideal": [["normalizedMeasures"],["populationEquality"]],
    "Deviation from Average": [["normalizedMeasures"],["deviationFromAverageAvg"]],
    "Deviation from Average": [
      ["normalizedMeasures"],
      ["deviationFromAverage"]
    ],
    "Deviation from Enacted": [
      ["normalizedMeasures"],
      ["deviationFromEnacted"]
    ],
    "Compactness": [["normalizedMeasures"],["compactness"]],
    "Split County Score": [["normalizedMeasures"],["splitCountyScore"]],
    "Majority Minority Districts" : [["measures"],["majorityMinorityDistricts"]]
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
              : this.props.ComparisonDistrictingA.id}{" "}
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
              : this.props.ComparisonDistrictingB.id}{" "}
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
                  let thisDistrictingVal = this.props.ComparisonDistrictingA
                    [this.statsToCompare[key][0]];
                  let otherDistrictingVal = this.props.ComparisonDistrictingB
                    [this.statsToCompare[key][0]];
                  if (this.statsToCompare[key].length == 2) {
                    thisDistrictingVal =
                      thisDistrictingVal[this.statsToCompare[key][1]];
                    otherDistrictingVal =
                      otherDistrictingVal[this.statsToCompare[key][1]];
                  }
                  let difference = StatUtilities.getPercentageChange(
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
                      value={
                        thisDistrictingVal.toFixed(2)
                      }
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
                  let thisDistrictingVal = this.props.ComparisonDistrictingB
                    [this.statsToCompare[key][0]];
                  let otherDistrictingVal = this.props.ComparisonDistrictingA
                    [this.statsToCompare[key][0]];
                  if (this.statsToCompare[key].length == 2) {
                    thisDistrictingVal =
                      thisDistrictingVal[this.statsToCompare[key][1]];
                    otherDistrictingVal =
                      otherDistrictingVal[this.statsToCompare[key][1]];
                  }
                  let difference = StatUtilities.getPercentageChange(
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
                      value={
                        thisDistrictingVal.toFixed(2)
                      }
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
