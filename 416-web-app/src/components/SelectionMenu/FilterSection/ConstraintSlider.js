import React, { Component } from "react";
import {
  updateConstraintSliderSettings,
  setEnabledStateOfConstraint,
} from "../../../redux/actions/settingActions";
import { Row, Switch } from "react-materialize";
import { Slider } from "@material-ui/core";
import { connect } from "react-redux";
class ConstraintSlider extends Component {
  handleSwitch(e, key) {
    this.props.setEnabledStateOfConstraint(key, e.target.checked);
  }

  render() {
    let filter = this.props.filter;
    if(!Array.isArray(filter.value)) {
      return (
        <Row key={this.props.filterKey}>
           <div className="constraintAndCheckbox">
           <h6>
             {filter.name} <b>({filter.value})</b>
           </h6>
          </div>
            <Slider
              disabled={!filter.enabled}
              onChange={(e, newValue) =>
                this.props.updateConstraintSliderSettings(
                  this.props.filterKey,
                  newValue
                )
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
      )}
    else {
      return(
        <Row key={this.props.filterKey}>
           <div className="constraintAndCheckbox">
           <h6>
             {filter.name} <b>({filter.value[0]}-{filter.value[1]})</b>
           </h6>
          </div>
          <Slider
            value={filter.value}
            onChange={(e, newValue) =>
              this.props.updateConstraintSliderSettings(
                this.props.filterKey,
                newValue
              )}
            max={filter.maxVal}
            min={filter.minVal}
            name={filter.name}
            valueLabelDisplay="auto"
            aria-labelledby="range-slider"
          />
          </Row>
      )
    }
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    updateConstraintSliderSettings: (key, newVal) => {
      dispatch(updateConstraintSliderSettings(key, newVal));
    },
    setEnabledStateOfConstraint: (key, bool) => {
      dispatch(setEnabledStateOfConstraint(key, bool));
    },
  };
};

const mapStateToProps = (state, ownProps) => {
  return {
    ConstraintSliderSettings: state.ConstraintSliderSettings,
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ConstraintSlider);
