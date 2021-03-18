import React, { Component } from "react";
import * as SelectionMenuUtilities from "../../../utilities/SelectionMenuUtilities";
import {
  setEnabledStateOfConstraint,
  updateConstraintSliderSettings,
  updateIncumbentProtection,
  updatePopulationConstraint,
  updateMinorityConstraint,
} from "../../../redux/actions/settingActions";
import {
  Row,
  Select,
  Icon,
  Collapsible,
  CollapsibleItem,
  Switch,
} from "react-materialize";
import LabelAndInfoIcon from '../../StatisticComponents/LabelAndInfoIcon'
import { FormControlLabel, Slider, Checkbox } from "@material-ui/core";
import { connect } from "react-redux";
import IncumbentModal from "./IncumbentModal";

class ConstraintSelection extends Component {
  handleSwitch(e, key) {
    this.props.setEnabledStateOfConstraint(key, e.target.checked);
  }

  render() {
    return (
      <div className="filterSectionItem">
        <h4 className="center-title">
          {SelectionMenuUtilities.LABELS.CONSTRAINTS_HEADER}
        </h4>

        {/* Population Constraint Selection */}
        <LabelAndInfoIcon
                      label={SelectionMenuUtilities.LABELS.VOTING_POPULATION_TO_CONSTRAIN}
                      description= {SelectionMenuUtilities.DESCRIPTIONS.VOTING_POPULATION_CONSTRAINT}
          />
        <Select
          icon={<Icon>people</Icon>}
          id="Select-9"
          multiple={false}
          onChange={(e) =>
            this.props.updatePopulationConstraint(e.target.value)
          }
          options={{
            classes: "",
            dropdownOptions: {
              alignment: "left",
              autoTrigger: true,
              closeOnClick: true,
              constrainWidth: true,
              coverTrigger: true,
              hover: false,
              inDuration: 150,
              onCloseEnd: null,
              onCloseStart: null,
              onOpenEnd: null,
              onOpenStart: null,
              outDuration: 250,
            },
          }}
          value=""
        >
          <option disabled value="">
            {SelectionMenuUtilities.LABELS.CHOOSE_A_VOTING_POPULATION}
          </option>
          {Object.keys(SelectionMenuUtilities.POPULATIONS).map((key) => {
            return (
              <option key={key} value={key}>
                {SelectionMenuUtilities.POPULATIONS[key]}
              </option>
            );
          })}
        </Select>
        {/* Section for Setting Constriants */}
        <div className="filterSectionItem">
          {Object.keys(this.props.ConstraintSliderSettings).map((key) => {
            let filter = this.props.ConstraintSliderSettings[key];
            if (!Array.isArray(filter.value)) {
              return (
                <Row key={key}>
                  <div className="constraintAndCheckbox">
                    <h6>
                      {filter.name}{" "}
                      <b>({this.props.ConstraintSliderSettings[key].value})</b>
                    </h6>
                    <Switch
                      id={"Switch-" + key}
                      offLabel=""
                      onChange={(e) => {
                        this.handleSwitch(e, key);
                      }}
                      onLabel=""
                      checked={filter.enabled}
                      className="constraintSwitch"
                    />
                  </div>
                  <Slider
                    disabled={!filter.enabled}
                    onChange={(e, newValue) =>
                      this.props.updateConstraintSliderSettings(key, newValue)
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
        {/* Incumbent protection, part of constraints  */}
        <Row>
          <IncumbentModal />
        </Row>
        <Row>
        <LabelAndInfoIcon
                      label={SelectionMenuUtilities.LABELS.MINORITY_POPULATION_TO_CONSTRAIN}
                      description= {SelectionMenuUtilities.DESCRIPTIONS.MINORITY_POPULATION_CONSTRAINT}
          />
        <Select
          icon={<Icon>people</Icon>}
          id="Select-9"
          multiple={false}
          onChange={(e) =>
            this.props.updatePopulationConstraint(e.target.value)
          }
          options={{
            classes: "",
            dropdownOptions: {
              alignment: "left",
              autoTrigger: true,
              closeOnClick: true,
              constrainWidth: true,
              coverTrigger: true,
              hover: false,
              inDuration: 150,
              onCloseEnd: null,
              onCloseStart: null,
              onOpenEnd: null,
              onOpenStart: null,
              outDuration: 250,
            },
          }}
          value=""
        >
          <option disabled value="">
            {SelectionMenuUtilities.LABELS.CHOOSE_A_MINORITY_POPULATION}
          </option>
          {Object.keys(SelectionMenuUtilities.MINORITIES).map((key) => {
            return (
              <option key={key} value={key}>
                {SelectionMenuUtilities.MINORITIES[key]}
              </option>
            );
          })}
        </Select>
        </Row>
      </div>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    updateIncumbentProtection: (key, newVal) => {
      dispatch(updateIncumbentProtection(key, newVal));
    },
    updatePopulationConstraint: (key) => {
      dispatch(updatePopulationConstraint(key));
    },
    updateMinorityConstraint: (key) => {
      dispatch(updateMinorityConstraint(key));
    },
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
    IncumbentProtectionInfo: state.IncumbentProtectionInfo,
    ConstraintSliderSettings: state.ConstraintSliderSettings,
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ConstraintSelection);
