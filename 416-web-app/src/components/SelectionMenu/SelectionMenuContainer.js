import React, { Component } from "react";
import { Col, Row } from "react-materialize";
import FilterSection from "./FilterSection/FilterSection";
import ListingSection from "./ListingSection/ListingSection";
import CompareSection from "./CompareSection/CompareSection";

export default class SelectionMenuContainer extends Component {
  render() {
    return (
      <div className="SelectionMenuContainer">
        <FilterSection />
        <ListingSection />
        <CompareSection />
      </div>
    );
  }
}
