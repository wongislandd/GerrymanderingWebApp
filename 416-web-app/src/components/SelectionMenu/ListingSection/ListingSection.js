import React, { Component } from "react";
import { connect } from "react-redux";
import {
  Collapsible,
  CollapsibleItem,
  Row,
  Col,
  Select,
  Switch,
} from "react-materialize";
import DistrictingItem from "./DistrictingItem";
import {
  setNewDistrictingSelected,
  setShowFullListing,
} from "../../../redux/actions/settingActions";
import * as SelectionMenuUtilities from "../../../utilities/SelectionMenuUtilities";
import SortingCollapsible from "./SortingCollapsible";
import CollapsibleSection from "./CollapsibleSection";
import FullListing from "./FullListing";
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
            <h5>{SelectionMenuUtilities.LABELS.DISTRICTING_RESULTS}</h5>
          </div>
          <div className="centerWithinMe listingOptions">
            <Switch
              id="listingOptionSwitch"
              offLabel="Summary"
              onChange={(e) => this.props.setShowFullListing(e.target.checked)}
              onLabel="Full Listing"
              checked={this.props.ShowFullListing}
              className="switched"
            />
          </div>
        </Row>
        {this.props.ShowFullListing ? (
          /* If yes, show full listing */
          <FullListing />
        ) : (
          /* Otherwise show summary info */
          <SummaryListing />
        )}
      </div>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    setNewDistrictingSelected: (bool) => {
      dispatch(setNewDistrictingSelected(bool));
    },
    setShowFullListing: (bool) => {
      dispatch(setShowFullListing(bool));
    },
  };
};

const mapStateToProps = (state, ownProps) => {
  return {
    ShowFullListing: state.ShowFullListing,
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ListingSection);
