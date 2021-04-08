import React, { Component } from "react";
import { connect } from "react-redux";
import {
  Row,
  Switch,
} from "react-materialize";
import {
  setNewDistrictingSelected,
  setShowFullListing,
} from "../../../redux/actions/settingActions";
import * as SelectionMenuUtilities from "../../../utilities/SelectionMenuUtilities";
import SummaryListing from "./SummaryListing";

class ListingSection extends Component {
  /* Once this loads, it's at first false until something is chosen*/
  componentDidMount() {
    this.props.setNewDistrictingSelected(false);
  }

  render() {
    return (
      <div className="SelectionMenuSection ListingSection">
        <Row>
          <div className="DistrictingResultsHeader">
            <h5>{SelectionMenuUtilities.LABELS.CONSTRAINED_DISTRICTING_RESULTS}</h5>
          </div>
        </Row>
          <SummaryListing />
      </div>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    setNewDistrictingSelected: (bool) => {
      dispatch(setNewDistrictingSelected(bool));
    },
  };
};

const mapStateToProps = (state, ownProps) => {
  return {
    
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ListingSection);
