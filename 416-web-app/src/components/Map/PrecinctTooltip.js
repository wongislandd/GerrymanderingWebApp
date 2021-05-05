import React, { Component } from "react";
import { connect } from "react-redux";
import * as MapUtilities from "../../utilities/MapUtilities";
import * as StatUtilities from "../../utilities/StatUtilities";

class PrecinctTooltip extends Component {
  constructor(props) {
    super(props);
  }
  render() {
    const locationToFeature = this.props.FeaturedPrecinct;
    if (locationToFeature == null) {
      return (
        <div
          className="tooltip"
          style={{ left: this.props.MouseX, top: this.props.MouseY }}
        >
          <div>{MapUtilities.MESSAGES.NoInfoToDisplayMsg}</div>
        </div>
      );
    } else {
      return (
        /* Placeholder for better design */
        <div
          className="tooltip"
          style={{ left: this.props.MouseX, top: this.props.MouseY }}
        >
          <ul>
            <li>
              <b>Precinct : {locationToFeature.properties.name}</b>
            </li>
            <li>County : {locationToFeature.properties.county}</li>
            <li>
              Population :{" "}
              {StatUtilities.addCommas(locationToFeature.properties.population)}
            </li>
            <li>
              White Population:{" "}
              {StatUtilities.formatResult(
                locationToFeature.properties.white,
                locationToFeature.properties.population
              )}
            </li>
            <li>
              Black Population:{" "}
              {StatUtilities.formatResult(
                locationToFeature.properties.black,
                locationToFeature.properties.population
              )}
            </li>
            <li>
              Hispanic Population:{" "}
              {StatUtilities.formatResult(
                locationToFeature.properties.hispanic,
                locationToFeature.properties.population
              )}
            </li>
          </ul>
        </div>
      );
    }
  }
}

const mapStateToProps = (state, ownProps) => {
  return {
    DisplayDistricts: state.DisplayDistricts,
    DisplayPrecincts: state.DisplayPrecincts,
    FeaturedDistrict: state.FeaturedDistrict,
    FeaturedPrecinct: state.FeaturedPrecinct,
    MouseX: state.MouseX,
    MouseY: state.MouseY,
    MapRef: state.MapRef,
  };
};

export default connect(mapStateToProps)(PrecinctTooltip);
