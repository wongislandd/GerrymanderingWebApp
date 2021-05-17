import React, { Component } from "react";
import { Row, Col, Switch } from "react-materialize";
import * as ToolbarUtilities from "../../../../utilities/ToolbarUtilities.js";
import { connect } from "react-redux";
import {
  togglePrecinctSwitch,
  toggleDistrictSwitch,
  toggleCountySwitch,
  toggleEnactedSwitch,
} from "../../../../redux/actions/settingActions";
import ReactMapGL, { Layer, Source } from "react-map-gl";
import * as MapUtilities from '../../../../utilities/MapUtilities'

class SettingsMode extends Component {
  render() {
    return (
      <div className="ToolbarContent">
        <h5>Toggles</h5>
        <Col s={8}>
          <Row>{ToolbarUtilities.LABELS.TOGGLE_PRECINCT_DISPLAY_LABEL}</Row>
          <Row>{ToolbarUtilities.LABELS.TOGGLE_COUNTY_DISPLAY_LABEL}</Row>
          <Row>{ToolbarUtilities.LABELS.TOGGLE_DISTRICT_DISPLAY_LABEL}</Row>
          <Row>{ToolbarUtilities.LABELS.TOGGLE_ENACTED_DISPLAY_LABEL}</Row>
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
              disabled={this.props.CountiesGeoJson == null}
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
          <Row>
            <Switch
              id={ToolbarUtilities.CONSTANTS.ENACTED_SWITCH_ID}
              offLabel="Off"
              onLabel="On"
              onChange={(e) =>
                this.props.toggleEnactedSwitch(!this.props.DisplayEnacted)
              }
              checked={this.props.DisplayEnacted}
            />
          </Row>
        </Col>
        {this.props.DisplayEnacted ? 
          <div className="centerWithinMe lowerMap">
          <h5>Enacted Districting</h5>
          <ReactMapGL
          className="map-display"
          mapboxApiAccessToken={process.env.REACT_APP_MAPBOX_TOKEN}
          {...this.props.MapViewport}
          width="400px"
          height="200px"
          zoom={4}
          ref={this.props.MapRef}
        >
          {this.props.EnactedGeoJson != null ? (
            <div>
              <Source
                id={MapUtilities.IDs.ENACTED_SOURCE_ID}
                type="geojson"
                data={this.props.EnactedGeoJson}
                generateId={true}
              />
              <Layer
                id={MapUtilities.IDs.ENACTED_DISTRICT_FILL_LAYER_ID}
                type="fill"
                source={MapUtilities.IDs.ENACTED_SOURCE_ID}
                layout={{
                  visibility: this.props.DisplayEnacted ? "visible" : "none",
                }}
                paint={{
                  "fill-color": [
                    "rgb",
                    ["get", "rgb-R"],
                    ["get", "rgb-G"],
                    ["get", "rgb-B"],
                  ],
                  "fill-opacity": [
                    "case",
                    ["boolean", ["feature-state", "hover"], false],
                    0.6,
                    0.3,
                  ],
                }}
              />
              <Layer
                id={MapUtilities.IDs.ENACTED_DISTRICT_LINE_LAYER_ID}
                type="line"
                source={MapUtilities.IDs.ENACTED_SOURCE_ID}
                layout={{
                  visibility: this.props.DisplayEnacted ? "visible" : "none",
                }}
                paint={{
                  "line-opacity": 1,
                }}
              />
            </div>
          ) : (
            <div></div>
          )}
          </ReactMapGL>              
          </div>: <div/>}
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
    toggleEnactedSwitch: (bool) => {
      dispatch(toggleEnactedSwitch(bool))
    }
  };
};

const mapStateToProps = (state, ownProps) => {
  return {
    DisplayPrecincts: state.DisplayPrecincts,
    DisplayDistricts: state.DisplayDistricts,
    DisplayCounties: state.DisplayCounties,
    DisplayEnacted : state.DisplayEnacted,
    EnactedGeoJson : state.EnactedGeoJson,
    PrecinctsGeoJson: state.PrecinctsGeoJson,
    CountiesGeoJson: state.CountiesGeoJson,
    MapViewport : state.MapViewport
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(SettingsMode);
