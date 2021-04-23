import React, { Component } from "react";
import { Row, Col, Switch } from "react-materialize";
import * as ToolbarUtilities from "../../../../utilities/ToolbarUtilities.js";
import { connect } from "react-redux";
import {
  togglePrecinctSwitch,
  toggleDistrictSwitch,
  toggleCountySwitch,
} from "../../../../redux/actions/settingActions";

class SettingsMode extends Component {
  render() {
    return (
      <div className="ToolbarContent">
        <h5>Toggles</h5>
        <Col s={8}>
          <Row>{ToolbarUtilities.LABELS.TOGGLE_PRECINCT_DISPLAY_LABEL}</Row>
          <Row>{ToolbarUtilities.LABELS.TOGGLE_COUNTY_DISPLAY_LABEL}</Row>
          <Row>{ToolbarUtilities.LABELS.TOGGLE_DISTRICT_DISPLAY_LABEL}</Row>
        </Col>
        <Col>
          <Row>
            <Switch
              id={ToolbarUtilities.CONSTANTS.PRECINCT_SWITCH_ID}
              offLabel="Off"
              onLabel="On"
              onChange={(e) =>
                this.props.togglePrecinctSwitch(!this.props.DisplayPrecincts)
              }
              checked={this.props.DisplayPrecincts}
              disabled={this.props.PrecinctsGeoJson == null}
            />
          </Row>
          <Row>
            <Switch
              id={ToolbarUtilities.CONSTANTS.COUNTY_SWITCH_ID}
              offLabel="Off"
              onLabel="On"
              onChange={(e) =>
                this.props.toggleCountySwitch(!this.props.DisplayCounties)
              }
              checked={this.props.DisplayCounties}
            />
          </Row>
          <Row>
            <Switch
              id={ToolbarUtilities.CONSTANTS.DISTRICT_SWITCH_ID}
              offLabel="Off"
              onLabel="On"
              onChange={(e) =>
                this.props.toggleDistrictSwitch(!this.props.DisplayDistricts)
              }
              checked={this.props.DisplayDistricts}
            />
          </Row>
        </Col>
      </div>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    togglePrecinctSwitch: (bool) => {
      dispatch(togglePrecinctSwitch(bool));
    },
    toggleCountySwitch: (bool) => {
      dispatch(toggleCountySwitch(bool));
    },
    toggleDistrictSwitch: (bool) => {
      dispatch(toggleDistrictSwitch(bool));
    },
  };
};

const mapStateToProps = (state, ownProps) => {
  return {
    DisplayPrecincts: state.DisplayPrecincts,
    DisplayDistricts: state.DisplayDistricts,
    DisplayCounties: state.DisplayCounties,
    PrecinctsGeoJson : state.PrecinctsGeoJson,
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(SettingsMode);
