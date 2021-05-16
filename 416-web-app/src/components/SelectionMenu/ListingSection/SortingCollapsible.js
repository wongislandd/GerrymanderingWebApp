import React, { Component } from "react";
import { CollapsibleItem, Row, Col } from "react-materialize";
import { connect } from "react-redux";
import * as SelectionMenuUtilities from "../../../utilities/SelectionMenuUtilities";

/* This should be able to change the sort direction and line up with the numbers displayed in the districting item component*/
class SortingCollapsible extends Component {
  render() {
    return (
      <CollapsibleItem
        className={"districtSummaryCollapsible sortingCollapsible"}
        header={
          <Row className="ListingColumnsContainer align-items-start">
            <Col s={2}>
              <div>ID</div>
            </Col>
            <Col s={2}>
              <Row>OF Score</Row>
            </Col>
            <Col s={3}>
              <Row>Population Equality Score</Row>
            </Col>
            <Col s={3}>
            <Row>Split County Score</Row>     
            </Col>
            <Col s={2}>
            <Row>Majority Minority Districts</Row>
            </Col>
          </Row>
        }
        onSelect={(e) => {}}
      ></CollapsibleItem>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {};
};

const mapStateToProps = (state, ownProps) => {
  return {};
};

export default connect(mapStateToProps, mapDispatchToProps)(SortingCollapsible);
