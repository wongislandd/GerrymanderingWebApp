import React, { Component } from "react";
import Plot from "react-plotly.js";

export default class BoxPlot extends Component {
  render() {
    // Boxes
    const boxes = []
    for (var i=0;i<this.props.DistrictingToDisplay.geoJson.features.length;i++) {
        var traceValues = []
        for (var j = 0; j < 50; j++) {
            traceValues[j] = Math.random()*(i+1) / this.props.DistrictingToDisplay.geoJson.features.length
        }
        boxes[i] = {
            y: traceValues,
            type: "box",
            name : "District " + (i+1),
            fillcolor : 'white',
            color : 'white',
            marker : {color : 'black'}
        }
    }
    // Points
    const points = []
    for (var i=0;i<this.props.DistrictingToDisplay.geoJson.features.length;i++) {
        // Have the same name to match the marker plot on top of the box plot
        points.push({
            x : ["District " + (i+1)],
            y : [Math.random()*(i+1) / this.props.DistrictingToDisplay.geoJson.features.length],
            marker : {
                size : 5,
                color : 'red'
            }
        })
    }

    const traces = boxes.concat(points)

    return (
      <div>
        <Plot
          data={traces}
          layout={{ width: 900, height: 400, title: "Enacted Districting Evaluation", showlegend : false }}
        />
      </div>
    );
  }
}
