import React, { Component } from "react";
import CollapsibleSection from "./CollapsibleSection";
import * as SelectionMenuUtilities from "../../../utilities/SelectionMenuUtilities";
import { connect } from "react-redux";

class SummaryListing extends Component {
  render() {
    return (
      <div>
        {Object.keys(this.props.AnalysisDistrictings).map((key) => {
          return (
            <CollapsibleSection
              key={key}
              header={
                SelectionMenuUtilities.ANALYSIS_CATEGORIES_USER_FRIENDLY[key]
              }
              districtings={this.props.AnalysisDistrictings[key]}
            />
          );
        })}
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return {
    AnalysisDistrictings: state.AnalysisDistrictings,
  };
};

export default connect(mapStateToProps)(SummaryListing);
