import React, { Component } from "react";
import CollapsibleSection from "./CollapsibleSection";
import * as SelectionMenuUtilities from "../../../utilities/SelectionMenuUtilities";
import { connect } from "react-redux";
import TagFilter from "./TagFilter";

class SummaryListing extends Component {
  sortByScore(districtings = []) {
    districtings.sort((d1, d2) => {
        let d1Val = d1.objectiveFunctionScore
        let d2Val = d2.objectiveFunctionScore
        if (d1Val < d2Val) {
          return 1
        }
        if (d1Val > d2Val) {
          return -1
        }
        else {
          return 0
        }
    })
    return districtings;
}


  filterDistrictingsBasedOnTags() {
    // Case of no tags
    if (this.props.SelectedTags.length === 0) {
      return this.props.AnalysisDistrictings
    }
    // Otherwise there are tags
    let filteredDistrictings = []
    this.props.AnalysisDistrictings.forEach((districting) => {
      let passAll = true;
      this.props.SelectedTags.forEach((tag) => {
        if (!districting.tags.includes(tag)) {
          passAll = false;
        }
      })
      if (passAll) {
        filteredDistrictings.push(districting)
      }
    })
    return filteredDistrictings
  }


  render() {
    let tagFilteredDistrictings = this.filterDistrictingsBasedOnTags();
    let sortedDistrictings = this.sortByScore(tagFilteredDistrictings)
    return (
      <CollapsibleSection
              key = ""
              header={
                <TagFilter/>
              }
              districtings={sortedDistrictings}
        />
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return {
    AnalysisDistrictings: state.AnalysisDistrictings,
    SelectedTags : state.SelectedTags,
  };
};

export default connect(mapStateToProps)(SummaryListing);
