import React, { Component } from "react";
import { connect } from "react-redux";
import { Collapsible, CollapsibleItem, Row, Col, Select } from "react-materialize";
import DistrictingItem from "./DistrictingItem";
import { setNewDistrictingSelected } from "../../../redux/actions/settingActions";
import * as SelectionMenuUtilities from '../../../utilities/SelectionMenuUtilities'
import SortingCollapsible from "./SortingCollapsible";

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
        </Row>
        <Collapsible accordion>

          <SortingCollapsible/>
          
          {this.props.FilteredDistrictings.map((districting, key) => {
            return (
              <DistrictingItem
                key={key}
                index={key}
                districting={districting}
              />
            );
          })}
        </Collapsible>
      </div>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    setNewDistrictingSelected: (bool) => {
      dispatch(setNewDistrictingSelected(bool));
    },
  };
};

const mapStateToProps = (state, ownProps) => {
  return {
    FilteredDistrictings: state.FilteredDistrictings,
    SortedBy: state.SortedBy,
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ListingSection);
