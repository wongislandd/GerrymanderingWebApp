import React, { Component } from "react";
import { connect } from "react-redux";
import * as MapUtilities from "../../utilities/MapUtilities";
import * as StatUtilities from "../../utilities/StatUtilities";

class DistrictTooltip extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const locationToFeature = this.props.FeaturedDistrict;
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
        /* Need to see if total population == voter count or not, do non-voters count towards population? So these % go off of total pop or # voters? */
        <div
          className="tooltip"
          style={{ left: this.props.MouseX, top: this.props.MouseY }}
        >
          <ul>
            <li>
              <b>
                District :{" "}
                {
                  locationToFeature.properties[
                    MapUtilities.PROPERTY_LABELS.DISTRICT_ID
                  ]
                }
              </b>
            </li>
            <li>
              Total Population :{" "}
              {StatUtilities.addCommas(
                locationToFeature.properties[
                  MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION
                ]
              )}
            </li>
            <li>
              White Population:{" "}
              {StatUtilities.formatResult(
                locationToFeature.properties[
                  MapUtilities.PROPERTY_LABELS.WHITE_COUNT
                ],
                locationToFeature.properties[
                  MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION
                ]
              )}
            </li>
            <li>
              Black Population:{" "}
              {StatUtilities.formatResult(
                locationToFeature.properties[
                  MapUtilities.PROPERTY_LABELS.BLACK_COUNT
                ],
                locationToFeature.properties[
                  MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION
                ]
              )}
            </li>
            <li>
              Hispanic Population:{" "}
              {StatUtilities.formatResult(
                locationToFeature.properties[
                  MapUtilities.PROPERTY_LABELS.HISPANIC_COUNT
                ],
                locationToFeature.properties[
                  MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION
                ]
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

export default connect(mapStateToProps)(DistrictTooltip);
