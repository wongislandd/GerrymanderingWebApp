import React, { Component } from "react";
import * as SelectionMenuUtilities from "../../../utilities/SelectionMenuUtilities";
import {
  updateObjectiveFunctionSettings,
  resetExpandedSummaries,
} from "../../../redux/actions/settingActions";
import { Row } from "react-materialize";
import { Slider } from "@material-ui/core";
import { connect } from "react-redux";

class WeightSelection extends Component {
  render() {
    return (
      <div className="filterSectionItem">
        <h4 className="center-title">
          {SelectionMenuUtilities.LABELS.OBJECTIVE_FUNCTION_WEIGHTS_HEADER}
        </h4>
        {Object.keys(this.props.ObjectiveFunctionSettings).map((key) => {
          let filter = this.props.ObjectiveFunctionSettings[key];
          if (!Array.isArray(filter.value)) {
            return (
              <Row key={key}>
                <h6>
                  {filter.name}{" "}
                  <b>({this.props.ObjectiveFunctionSettings[key].value})</b>
                </h6>
                <Slider
                  onChange={(e, newValue) =>
                    this.props.updateObjectiveFunctionSettings(key, newValue)
                  }
                  value={filter.value}
                  max={filter.maxVal}
                  min={filter.minVal}
                  name={filter.name}
                  step={filter.step}
                  marks
                  valueLabelDisplay="auto"
                />
              </Row>
            );
          }
        })}
      </div>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    updateObjectiveFunctionSettings: (key, newVal) => {
      dispatch(updateObjectiveFunctionSettings(key, newVal));
    },
  };
};

const mapStateToProps = (state, ownProps) => {
  return {
    ObjectiveFunctionSettings: state.ObjectiveFunctionSettings,
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(WeightSelection);
