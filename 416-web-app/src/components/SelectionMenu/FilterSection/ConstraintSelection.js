import React, { Component } from "react";
import * as SelectionMenuUtilities from "../../../utilities/SelectionMenuUtilities";
import {
  setEnabledStateOfConstraint,
  updateConstraintSettings,
  updateIncumbentProtection,
  updatePopulationConstraint,
} from "../../../redux/actions/settingActions";
import {
  Row,
  Select,
  Icon,
  Collapsible,
  CollapsibleItem,
  Switch,
} from "react-materialize";
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
        <h6>{SelectionMenuUtilities.LABELS.POPULATION_TO_ACCOUNT_FOR}</h6>
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
            Choose a Population
          </option>
          {Object.keys(this.props.PopulationConstraintInfo).map((key) => {
            return (
              <option key={key} value={key}>
                {key}
              </option>
            );
          })}
        </Select>
        {/* Section for Setting Constriants */}
        <div className="filterSectionItem">
          {Object.keys(this.props.ConstraintSettings).map((key) => {
            let filter = this.props.ConstraintSettings[key];
            if (!Array.isArray(filter.value)) {
              return (
                <Row key={key}>
                  <div className="constraintAndCheckbox">
                    <h6>
                      {filter.name}{" "}
                      <b>({this.props.ConstraintSettings[key].value})</b>
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
                      this.props.updateConstraintSettings(key, newValue)
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
          {/* <Collapsible className="constraint-collapsible">
                    <CollapsibleItem
                        expanded={false}
                        header={SelectionMenuUtilities.LABELS.INCUMBENT_PROTECTION_OPTIONS}
                        node="div"
                        >
                        {Object.keys(this.props.IncumbentProtectionInfo).map((key) => {
                            return(
                                <Row
                                    key={key}>
                                <FormControlLabel 
                                    control =  {
                                        <Checkbox
                                            id={key + "-protection-checkbox"}
                                            className="incumbent-protection-option"
                                            value={key}
                                            checked={this.props.IncumbentProtectionInfo[key]}
                                            onChange={(e) => this.props.updateIncumbentProtection(key,e.target.checked)}
                                        />
                                    }
                                    label = {key}/>
                                </Row>
                            )
                        })}
                    </CollapsibleItem>
                    </Collapsible> */}
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
    updatePopulationConstraint: (key, newVal) => {
      dispatch(updatePopulationConstraint(key, newVal));
    },
    updateConstraintSettings: (key, newVal) => {
      dispatch(updateConstraintSettings(key, newVal));
    },
    setEnabledStateOfConstraint: (key, bool) => {
      dispatch(setEnabledStateOfConstraint(key, bool));
    },
  };
};

const mapStateToProps = (state, ownProps) => {
  return {
    IncumbentProtectionInfo: state.IncumbentProtectionInfo,
    PopulationConstraintInfo: state.PopulationConstraintInfo,
    ConstraintSettings: state.ConstraintSettings,
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ConstraintSelection);
