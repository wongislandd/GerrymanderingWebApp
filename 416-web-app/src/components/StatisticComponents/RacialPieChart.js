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
        className="pieChart"
        width={"400px"}
        height={"200px"}
        chartType="PieChart"
        loader={<div>Loading Chart</div>}
        data={[
          ["Race", "Count"],
          ["White", this.props.district.demographics.white],
          ["Black", this.props.district.demographics.black],
          ["Hispanic", this.props.district.demographics.hispanic],
          ["Asian", this.props.district.demographics.asian],
          [
            "Native American / Alaskan",
            this.props.district.demographics.natives,
          ],
          [
            "Pacific Islander / Hawaiian",
            this.props.district.demographics.pacific,
          ],
          ["Other", this.props.district.demographics.other],
        ]}
        options={{
          pieSliceText: "none",
          pieHole: 0.2,
        }}
      />
    );
  }
}
