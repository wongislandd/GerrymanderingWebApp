import React, { Component } from "react";
import * as MapUtilities from "../../utilities/MapUtilities";
import { Chart } from "react-google-charts";

export default class RacialPieChart extends Component {
  constructor(props) {
    super(props);
  }
  render() {
    return (
      <Chart
        width={"400px"}
        height={"200px"}
        chartType="PieChart"
        loader={<div>Loading Chart</div>}
        data={[
          ["Race", "Count"],
          [
            "White",
            this.props.feature.properties[
              MapUtilities.PROPERTY_LABELS.WHITE_COUNT
            ],
          ],
          [
            "Black",
            this.props.feature.properties[
              MapUtilities.PROPERTY_LABELS.BLACK_COUNT
            ],
          ],
          [
            "Asian",
            this.props.feature.properties[
              MapUtilities.PROPERTY_LABELS.ASIAN_COUNT
            ],
          ],
          [
            "Native American / Alaskan",
            this.props.feature.properties[
              MapUtilities.PROPERTY_LABELS.NATIVE_COUNT
            ],
          ],
          [
            "Pacific Islander / Hawaiian",
            this.props.feature.properties[
              MapUtilities.PROPERTY_LABELS.PACIFIC_ISLANDER_COUNT
            ],
          ],
          [
            "Undesignated",
            this.props.feature.properties[
              MapUtilities.PROPERTY_LABELS.UNDESIGNATED_COUNT
            ],
          ],
          [
            "Other",
            this.props.feature.properties[
              MapUtilities.PROPERTY_LABELS.RACE_OTHER_COUNT
            ],
          ],
        ]}
        options={{
          title: "Racial Demographics",
          pieSliceText: "none",
          pieHole: 0.2,
        }}
      />
    );
  }
}
