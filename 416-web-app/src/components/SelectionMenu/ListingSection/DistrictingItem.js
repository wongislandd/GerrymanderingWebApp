import React, { Component } from "react";
import { CollapsibleItem, Row, Col } from "react-materialize";
import DistrictingInfoSection from "./DistrictingInfoSection";
import * as MapUtilities from "../../../utilities/MapUtilities";
import * as StatUtilities from "../../../utilities/StatUtilities";
import * as SelectionMenuUtilities from "../../../utilities/SelectionMenuUtilities";
import { connect } from 'react-redux'
import { toggleExpandedSummary } from "../../../redux/actions/settingActions";

class DistrictingItem extends Component {
  /* Pass this component a districting and it'll do the rest. */
  constructor(props) {
    super(props);
  }

  isDisplayed() {
    return (this.props.ExpandedSummaries.includes(this.props.districting.name))
  }

  handleSelect(event) {
    this.props.toggleExpandedSummary(this.props.districting.name)
  }

  render() {
    return (
      <CollapsibleItem
        expanded={this.isDisplayed()}
        className={"districtSummaryCollapsible"}
        header={
          // Line up with the Sorting Collapsible
          <Row
            className="ListingColumnsContainer"
            onClick={(e)=>{}}
          >
            <Col s={2}>{this.props.districting.name}</Col>
            <Col s={2}>{StatUtilities.getRandomInt(10)}</Col>
            <Col s={3}>
              {
                this.props.districting.objectivefunc[
                  MapUtilities.PROPERTY_LABELS.AVG_POPULATION_EQUALITY
                ]
              }
            </Col>
            <Col s={3}>{StatUtilities.getRandomInt(3)}</Col>
            <Col s={2}>
              {
                this.props.districting.objectivefunc[
                  MapUtilities.PROPERTY_LABELS.SPLIT_COUNTIES_SCORE
                ]
              }
            </Col>
          </Row>
        }
        onSelect={() => this.handleSelect()}
      >
        {this.isDisplayed() ? <div className="centerWithinMe">
          <h5 className="padBelowMe">
            {SelectionMenuUtilities.LABELS.DISTRICTING_BREAKDOWN}
          </h5>
          <DistrictingInfoSection districting={this.props.districting} />
        </div> : ""
      }
      </CollapsibleItem>
    );
  }
}


const mapDispatchToProps = (dispatch) => {
  return {
    toggleExpandedSummary : (name) => {
      dispatch(toggleExpandedSummary(name))
    },
  };
};


const mapStateToProps = (state, ownProps) => {
  return {
    ExpandedSummaries : state.ExpandedSummaries,
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(DistrictingItem);
