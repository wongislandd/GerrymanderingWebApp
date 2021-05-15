import React, { Component } from "react";
import { CollapsibleItem, Row, Col } from "react-materialize";
import DistrictingInfoSection from "./DistrictingInfoSection";
import * as MapUtilities from "../../../utilities/MapUtilities";
import * as StatUtilities from "../../../utilities/StatUtilities";
import * as SelectionMenuUtilities from "../../../utilities/SelectionMenuUtilities";
import StarIcon from '@material-ui/icons/Star';
import { Chip} from '@material-ui/core'
import { connect } from "react-redux";
import { toggleExpandedSummary } from "../../../redux/actions/settingActions";

class DistrictingItem extends Component {
  /* Pass this component a districting and it'll do the rest. */
  constructor(props) {
    super(props);
  }

  isDisplayed() {
    return this.props.ExpandedSummaries.includes(this.props.districting.id);
  }

  handleSelect(event) {
    this.props.toggleExpandedSummary(this.props.districting.id);
  }

  render() {
    return (
      <CollapsibleItem
        expanded={this.isDisplayed()}
        className={"districtSummaryCollapsible"}
        header={
          //Line up with the Sorting Collapsible
          <Row className="ListingColumnsContainer" onClick={(e) => {}}>
            <Col s={2}>{this.props.districting.id}</Col>
            <Col s={2}>{this.props.districting.objectiveFunctionScore.toFixed(2)}</Col>
            <Col s={3}>
              {this.props.districting.normalizedMeasures.populationEquality.toFixed(2)}
            </Col>
            <Col s={3}>
              {this.props.districting.normalizedMeasures.splitCountyScore.toFixed(2)}
            </Col>
            <Col s={2}>
              {this.props.districting.measures.majorityMinorityDistricts}
            </Col>
          </Row>
        }
        onSelect={() => this.handleSelect()}
      >
        {this.isDisplayed() ? (
          <div className="centerWithinMe">
            <h5 className="padBelowMe">
              {"Districting Breakdown (" + this.props.districting.id + ")"}
            </h5>
            <Row>
              {this.props.districting.tags.map((tag => {
                return <Col
                key={tag}>
                <Chip 
                label={SelectionMenuUtilities.TAGS[tag]}
                variant="outlined" 
                size="small" 
                icon={<StarIcon />}
                />
                </Col>
              }))}
            </Row>
            <DistrictingInfoSection districting={this.props.districting} />
          </div>
        ) : (
          ""
        )}
      </CollapsibleItem>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    toggleExpandedSummary: (id) => {
      dispatch(toggleExpandedSummary(id));
    },
  };
};

const mapStateToProps = (state, ownProps) => {
  return {
    ExpandedSummaries: state.ExpandedSummaries,
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(DistrictingItem);
