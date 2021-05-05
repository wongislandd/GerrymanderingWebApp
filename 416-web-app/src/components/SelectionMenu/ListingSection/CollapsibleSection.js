import React, { Component } from "react";
import { Collapsible, CollapsibleItem } from "react-materialize";
import DistrictingItem from "./DistrictingItem";
import SortingCollapsible from "./SortingCollapsible";

export default class CollapsibleSection extends Component {
  render() {
    return (
      <div>
        <h5 className="centerWithinMe">{this.props.header}</h5>
        <Collapsible>
          {this.props.districtings.length != 0 ? (
            <SortingCollapsible showSortOption={false} />
          ) : (
            ""
          )}
          {Object.keys(this.props.districtings).map((key) => {
            let districting = this.props.districtings[key];
            return <DistrictingItem key={key} districting={districting} />;
          })}
        </Collapsible>
      </div>
    );
  }
}
