import React, { Component } from "react";
import { connect } from "react-redux";
import {
  setNewDistrictingSelected,
  setComparisonDistrictingA,
  setComparisonDistrictingB,
  populateCurrentDistrictingGeoJson,
  populateCurrentDistrictingSummary,
} from "../../../redux/actions/settingActions";
import { Button } from "react-materialize";
import * as SelectionMenuUtilities from "../../../utilities/SelectionMenuUtilities";
import * as NetworkingUtilities from "../../../network/NetworkingUtilities";
import DistrictingSummary from "../../StatisticComponents/DistrictingSummary";

/* Properties: 
    this.props.districting => the associated districting to display info for
*/

class DistrictingInfoSection extends Component {
  constructor(props) {
    super(props);
    this.state = { loading: false };
  }

  async loadNewDistricting(id) {
    this.setState({ loading: true });
    NetworkingUtilities.loadDistricting(id)
      .then((results) => {
        this.props.populateCurrentDistrictingGeoJson(results);
      }).then((results) => {
        this.props.populateCurrentDistrictingSummary(this.props.districting)
      })
      .then((x) =>
        this.setState({
          loading: false,
        })
      );
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
            disabled={
              this.state.loading ||
              this.props.CurrentDistrictingSummary.id ==
                this.props.districting.id
            }
          >
            {this.props.CurrentDistrictingSummary.id ==
            this.props.districting.id
              ? "Currently Displaying this Districting"
              : this.state.loading
              ? "Loading"
              : SelectionMenuUtilities.LABELS.LOAD_THIS_DISTRICTING}
          </Button>
        </div>
      </div>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    populateCurrentDistrictingGeoJson: (districting) => {
      dispatch(populateCurrentDistrictingGeoJson(districting));
    },
    populateCurrentDistrictingSummary : (summary) => {
      dispatch(populateCurrentDistrictingSummary(summary))
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
    CurrentDistrictingSummary: state.CurrentDistrictingSummary,
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DistrictingInfoSection);
