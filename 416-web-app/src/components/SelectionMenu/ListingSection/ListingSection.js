import React, { Component } from "react";
import { connect } from "react-redux";
import { Collapsible, CollapsibleItem, Row, Col, Select, Switch } from "react-materialize";
import DistrictingItem from "./DistrictingItem";
import { setNewDistrictingSelected, setShowFullListing } from "../../../redux/actions/settingActions";
import * as SelectionMenuUtilities from '../../../utilities/SelectionMenuUtilities'
import SortingCollapsible from "./SortingCollapsible";
import CollapsibleSection from "./CollapsibleSection";

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
            onChange={(e)=> this.props.setShowFullListing(e.target.checked)}
            onLabel="Full Listing"
            checked={this.props.ShowFullListing}
            className="switched"
          />
        </div>
        </Row>
          {this.props.ShowFullListing ?
          /* If yes, show full listing */
          <Collapsible accordian>
          <SortingCollapsible showSortOption = {true}/>
          {this.props.FilteredDistrictings.map((districting, key) => {
            return (
              <DistrictingItem
                key={key}
                index={key}
                districting={districting}
              />
            );
          })}
          </Collapsible> :

          /* Otherwise show summary info */
          Object.keys(this.props.AnalysisDistrictings).map((key) => {
              return (<CollapsibleSection key = {key} header={SelectionMenuUtilities.ANALYSIS_CATEGORIES_USER_FRIENDLY[key]} districtings = {this.props.AnalysisDistrictings[key]}/>)
          })}

      </div>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    setNewDistrictingSelected: (bool) => {
      dispatch(setNewDistrictingSelected(bool));
    },
    setShowFullListing : (bool) => {
      dispatch(setShowFullListing(bool));
    }
  };
};

const mapStateToProps = (state, ownProps) => {
  return {
    FilteredDistrictings: state.FilteredDistrictings,
    SortedBy: state.SortedBy,
    AnalysisDistrictings : state.AnalysisDistrictings,
    ShowFullListing : state.ShowFullListing
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ListingSection);
