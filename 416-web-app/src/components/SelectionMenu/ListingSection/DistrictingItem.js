import React, { Component } from "react";
import { CollapsibleItem, Row, Col } from "react-materialize";
import DistrictingInfoSection from "./DistrictingInfoSection";
import * as MapUtilities from '../../../utilities/MapUtilities'
import * as StatUtilities from '../../../utilities/StatUtilities'
import * as SelectionMenuUtilities from '../../../utilities/SelectionMenuUtilities'

export default class DistrictingItem extends Component {
  /* Pass this component a districting and it'll do the rest. */
  constructor(props) {
    super(props);
  }

  handleClick(event) {}

  render() {
    return (
      <CollapsibleItem
        className={"districtSummaryCollapsible"}
        header={
          // Line up with the Sorting Collapsible
                <Row className="ListingColumnsContainer" onClick={(e) => {e.stopPropagation()}}>
                    <Col s={2}>
                    {this.props.districting.name}
                    </Col>
                    <Col s={2}>
                      {StatUtilities.getRandomInt(10)}
                    </Col>
                    <Col s={3}>
                    {this.props.districting.geoJson.objectivefunc[
                        MapUtilities.PROPERTY_LABELS.AVG_POPULATION_EQUALITY]}
                    </Col>
                    <Col s={3}>
                      {StatUtilities.getRandomInt(3)}
                    </Col>
                    <Col s={2}>
                    {this.props.districting.geoJson.objectivefunc[
                        MapUtilities.PROPERTY_LABELS.SPLIT_COUNTIES_SCORE]}
                    </Col>
                    </Row>
        }
        onSelect={(e) => this.handleClick(e)}
      >
        <div className="centerWithinMe">
        <h5 className="padBelowMe">{SelectionMenuUtilities.LABELS.DISTRICTING_BREAKDOWN}</h5>
        <DistrictingInfoSection districting={this.props.districting} />
        </div>
      </CollapsibleItem>
    );
  }
}
