import React, { Component } from "react";
import { connect } from "react-redux";
import SortingCollapsible from "./SortingCollapsible";
import { Collapsible } from "react-materialize";
import DistrictingItem from "./DistrictingItem";

class FullListing extends Component {
  render() {
    return (
      <Collapsible accordian>
        <SortingCollapsible showSortOption={true} />
        {this.props.FilteredDistrictings.map((districting, key) => {
          return (
            <DistrictingItem key={key} index={key} districting={districting} />
          );
        })}
      </Collapsible>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return {
    FilteredDistrictings: state.FilteredDistrictings,
  };
};

export default connect(mapStateToProps)(FullListing);
