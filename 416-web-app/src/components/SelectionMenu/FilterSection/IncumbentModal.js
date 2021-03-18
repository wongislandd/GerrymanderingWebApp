import React, { Component } from "react";
import * as SelectionMenuUtilities from "../../../utilities/SelectionMenuUtilities";
import { connect } from "react-redux";
import {
  setEnabledStateOfConstraint,
  updateConstraintSliderSettings,
  updateIncumbentProtection,
  updatePopulationConstraint,
} from "../../../redux/actions/settingActions";
import { Row, Col, Modal, Button } from "react-materialize";
import { FormControlLabel, Slider, Checkbox } from "@material-ui/core";

class IncumbentModal extends Component {
  render() {
    return (
      <div>
        <Modal
          actions={[
            <Button flat modal="close" node="button" waves="green">
              Close
            </Button>,
          ]}
          bottomSheet={false}
          fixedFooter
          header={SelectionMenuUtilities.LABELS.INCUMBENT_PROTECTION_OPTIONS}
          id="Incumbent-Selection"
          open={false}
          options={{
            dismissible: true,
            endingTop: "10%",
            inDuration: 250,
            onCloseEnd: null,
            onCloseStart: null,
            onOpenEnd: null,
            onOpenStart: null,
            opacity: 0.5,
            outDuration: 250,
            preventScrolling: true,
            startingTop: "4%",
          }}
          trigger={
            <Button node="button" className="menuButtons">
              {SelectionMenuUtilities.LABELS.INCUMBENT_PROTECTION_OPTIONS}
            </Button>
          }
        >
          <Row>
            {Object.keys(this.props.IncumbentProtectionInfo).map((key) => {
              return (
                <Col s={4} key={key}>
                  <Row>
                    <FormControlLabel
                      control={
                        <Checkbox
                          id={key + "-protection-checkbox"}
                          className="incumbent-protection-option"
                          value={key}
                          checked={this.props.IncumbentProtectionInfo[key]}
                          onChange={(e) =>
                            this.props.updateIncumbentProtection(
                              key,
                              e.target.checked
                            )
                          }
                        />
                      }
                      label={key}
                    />
                  </Row>
                </Col>
              );
            })}
          </Row>
        </Modal>
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
    PopulationConstraintSelection: state.PopulationConstraintSelection,
    ConstraintSettings: state.ConstraintSettings,
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(IncumbentModal);
