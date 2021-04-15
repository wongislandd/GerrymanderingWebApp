import React, { Component } from "react";
import { connect } from "react-redux";
import ReactMapGL, { Layer, Source } from "react-map-gl";
import {
  setCurrentState,
  setTentativeState,
  setViewport,
  loadStateOutlines,
  restoreDefaultStateForNewDistricting,
  setDistrictingsAreConstrained
} from "../../redux/actions/settingActions";
import * as ViewportUtilities from "../../utilities/ViewportUtilities";
import * as MapUtilities from "../../utilities/MapUtilities";
import * as NetworkingUtilities from '../../network/NetworkingUtilities'


class StateSelectionMap extends Component {
  componentDidMount() {
    // Fresh start
    this.props.restoreDefaultStateForNewDistricting()
    this.props.setCurrentState(ViewportUtilities.STATE_OPTIONS.UNSELECTED);
    this.populateStateCounties()
  }

  async populateStateCounties() {
    NetworkingUtilities.loadStateOutlines().then(results => this.props.loadStateOutlines(results))
  }

  _onClick = (event) => {
    const { features } = event;
    const hoveredFeature =
      features &&
      features.find(
        (f) =>
          f.layer.id === MapUtilities.IDs.COUNTY_FILL_LAYER_ID + "NC" ||
          f.layer.id === MapUtilities.IDs.COUNTY_FILL_LAYER_ID + "LA" ||
          f.layer.id === MapUtilities.IDs.COUNTY_FILL_LAYER_ID + "TX"
      );
    if (hoveredFeature != undefined) {
      switch (hoveredFeature.layer.id) {
        case MapUtilities.IDs.COUNTY_FILL_LAYER_ID + "NC":
          this.props.setViewport(ViewportUtilities.NORTH_CAROLINA.Maximized);
          this.props.setTentativeState(
            ViewportUtilities.STATE_OPTIONS.NORTH_CAROLINA
          );
          break;
        case MapUtilities.IDs.COUNTY_FILL_LAYER_ID + "LA":
          this.props.setViewport(ViewportUtilities.LOUISIANA.Maximized);
          this.props.setTentativeState(
            ViewportUtilities.STATE_OPTIONS.LOUISIANA
          );
          break;
        case MapUtilities.IDs.COUNTY_FILL_LAYER_ID + "TX":
          this.props.setViewport(ViewportUtilities.TEXAS.Maximized);
          this.props.setTentativeState(ViewportUtilities.STATE_OPTIONS.TEXAS);
          break;
        default:
          this.props.setViewport(ViewportUtilities.UNSELECTED.Maximized);
          this.props.setTentativeState(
            ViewportUtilities.STATE_OPTIONS.UNSELECTED
          );
      }
    }
  };

  render() {
    if (this.props.StateCounties == null) {
      return(
        <div>
          <ReactMapGL
          className="map-display"
          {...this.props.MapViewport}
          mapboxApiAccessToken={process.env.REACT_APP_MAPBOX_TOKEN}
          onViewportChange={(viewport) => {
            this.props.setViewport(viewport);
          }}
            onClick={(e)=>{}}
          />
      </div>
      )
    }
    /* Only load these if it's not empty*/
    else {
      const NCCountyGeoData = this.props.StateCounties[ViewportUtilities.STATE_OPTIONS.NORTH_CAROLINA]
      const LACountyGeoData = this.props.StateCounties[ViewportUtilities.STATE_OPTIONS.LOUISIANA]
      const TXCountyGeoData = this.props.StateCounties[ViewportUtilities.STATE_OPTIONS.TEXAS]
    return (
      <div>
        <ReactMapGL
          className="map-display"
          {...this.props.MapViewport}
          mapboxApiAccessToken={process.env.REACT_APP_MAPBOX_TOKEN}
          onViewportChange={(viewport) => {
            this.props.setViewport(viewport);
          }}
          onClick={this._onClick.bind(this)}
        >
          <Source
            id={MapUtilities.IDs.COUNTY_SOURCE_ID + "NC"}
            type="geojson"
            data={NCCountyGeoData}
            generateId={true}
          />
          <Layer
            id={MapUtilities.IDs.COUNTY_FILL_LAYER_ID + "NC"}
            type="fill"
            source={MapUtilities.IDs.COUNTY_SOURCE_ID + "NC"}
            paint={{
              "fill-color": "#abcdef",
              "fill-opacity": [
                "case",
                ["boolean", ["feature-state", "hover"], false],
                0.1,
                0.6,
              ],
            }}
          />
          <Source
            id={MapUtilities.IDs.COUNTY_SOURCE_ID + "LA"}
            type="geojson"
            data={LACountyGeoData}
            generateId={true}
          />
          <Layer
            id={MapUtilities.IDs.COUNTY_FILL_LAYER_ID + "LA"}
            type="fill"
            source={MapUtilities.IDs.COUNTY_SOURCE_ID + "LA"}
            paint={{
              "fill-color": "#8effba",
              "fill-opacity": [
                "case",
                ["boolean", ["feature-state", "hover"], false],
                0.1,
                0.6,
              ],
            }}
          />
          <Source
            id={MapUtilities.IDs.COUNTY_SOURCE_ID + "TX"}
            type="geojson"
            data={TXCountyGeoData}
            generateId={true}
          />
          <Layer
            id={MapUtilities.IDs.COUNTY_FILL_LAYER_ID + "TX"}
            type="fill"
            source={MapUtilities.IDs.COUNTY_SOURCE_ID + "TX"}
            paint={{
              "fill-color": "#2ea3fa",
              "fill-opacity": [
                "case",
                ["boolean", ["feature-state", "hover"], false],
                0.1,
                0.6,
              ],
            }}
          />
        </ReactMapGL>
      </div>

    )
  };
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    setCurrentState: (state) => {
      dispatch(setCurrentState(state));
    },
    setViewport: (viewport) => {
      dispatch(setViewport(viewport));
    },
    setTentativeState: (state) => {
      dispatch(setTentativeState(state));
    },
    loadStateOutlines : (dict) => {
      dispatch(loadStateOutlines(dict));
    },
    setDistrictingsAreConstrained : (bool) => {
      dispatch(setDistrictingsAreConstrained(bool))
    },
    restoreDefaultStateForNewDistricting : () => {
      dispatch(restoreDefaultStateForNewDistricting())
    },
}};

const mapStateToProps = (state, ownProps) => {
  return {
    MapViewport: state.MapViewport,
    StateCounties : state.StateCounties
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(StateSelectionMap);
