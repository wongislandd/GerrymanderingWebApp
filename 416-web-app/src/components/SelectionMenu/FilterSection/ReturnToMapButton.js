import React, { Component } from "react";
import { connect } from "react-redux";
import { Button, Icon } from "react-materialize";
import * as SelectionMenuUtilities from "../../../utilities/SelectionMenuUtilities";
import {
  setInSelectionMenu,
  restoreDefaultStateForNewDistricting,
} from "../../../redux/actions/settingActions";

class ReturnToMapButton extends Component {
  render() {
    return (
      <Button
        className="ReturnToMapBtn"
        onClick={(e) => {
          this.props.setInSelectionMenu(false);
          if (this.props.NewDistrictingSelected) {
            this.props.restoreDefaultStateForNewDistricting();
          }
        }}
        disabled={this.props.CurrentDistrictingGeoJson == null}
      >
        <Icon left>arrow_back</Icon>
        {SelectionMenuUtilities.LABELS.RETURN_TO_MAP}
      </Button>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    setInSelectionMenu: (bool) => {
      dispatch(setInSelectionMenu(bool));
    },
    restoreDefaultStateForNewDistricting: () => {
      dispatch(restoreDefaultStateForNewDistricting());
    },
  };
};

const mapStateToProps = (state, ownProps) => {
  return {
    NewDistrictingSelected: state.NewDistrictingSelected,
    CurrentDistrictingGeoJson: state.CurrentDistrictingGeoJson,
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ReturnToMapButton);
