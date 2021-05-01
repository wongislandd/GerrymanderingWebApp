import React, { Component } from "react";
import { connect } from "react-redux";
import {
  setCurrentDistricting,
  setNewDistrictingSelected,
  setComparisonDistrictingA,
  setComparisonDistrictingB,
  populateCurrentDistricting,
} from "../../../redux/actions/settingActions";
import { Button } from "react-materialize";
import * as SelectionMenuUtilities from "../../../utilities/SelectionMenuUtilities";
import * as NetworkingUtilities from '../../../network/NetworkingUtilities';
import DistrictingSummary from "../../StatisticComponents/DistrictingSummary";

/* Properties: 
    this.props.districting => the associated districting to display info for
*/

class DistrictingInfoSection extends Component {

  async loadNewDistricting(id) {
    NetworkingUtilities.loadDistricting(id).then(results => {
      this.props.populateCurrentDistricting(results);
    })
  }
  
  render() {
    return (
      <div className="districtingInfoSection">
        <DistrictingSummary DistrictingToDisplay={this.props.districting} />

        <div className="compareOptionsDiv centerWithinMe">
          <Button
            className="compareOptionsButton"
            onClick={(e) =>
              this.props.setComparisonDistrictingA(this.props.districting)
            }
            disabled={
              this.props.ComparisonDistrictingA == this.props.districting ||
              this.props.ComparisonDistrictingB == this.props.districting
            }
          >
            {this.props.ComparisonDistrictingA == this.props.districting
              ? "Chosen as Districting A"
              : "Set as Districting A for Compare"}
          </Button>
          <Button
            className="compareOptionsButton"
            onClick={(e) =>
              this.props.setComparisonDistrictingB(this.props.districting)
            }
            disabled={
              this.props.ComparisonDistrictingA == this.props.districting ||
              this.props.ComparisonDistrictingB == this.props.districting
            }
          >
            {this.props.ComparisonDistrictingB == this.props.districting
              ? "Chosen as Districting B"
              : "Set as Districting B for Compare"}
          </Button>
        </div>
        <div className="centerWithinMe">
          <Button
            className="redBrownBtn"
            onClick={(e) => {
              this.loadNewDistricting(this.props.districting.id);
              this.props.setNewDistrictingSelected(true);
            }}
          >
            {SelectionMenuUtilities.LABELS.LOAD_THIS_DISTRICTING}
          </Button>
        </div>
      </div>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    populateCurrentDistricting: (districting) => {
      dispatch(populateCurrentDistricting(districting));
    },
    setNewDistrictingSelected: (bool) => {
      dispatch(setNewDistrictingSelected(bool));
    },
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

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DistrictingInfoSection);
