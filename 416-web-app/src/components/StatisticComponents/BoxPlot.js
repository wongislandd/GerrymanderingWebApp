import React, { Component } from "react";
import Plot from "react-plotly.js";
import { connect } from 'react-redux'
import * as NetworkingUtilities from '../../network/NetworkingUtilities'
import { updateBWPoints } from "../../redux/actions/settingActions";

class BoxPlot extends Component {
  componentDidMount() {
    NetworkingUtilities.getPointsData(this.districtSummary.id).then((pointsData) =>
      this.props.updateBWPoints(pointsData)
    )
  }

  dataToShowInPlot() {
    if (this.props.BWBoxes == null) {
      return []
    } else if (this.props.BWPoints == null) {
      return this.props.BWBoxes
    } else {
        return this.props.BWBoxes.concat(this.props.BWPoints)
    }
  }

  render() {
    return (
      <div className="centerWithinMe">
        <Plot
          data={this.dataToShowInPlot()}
          layout={{
            width: 900,
            height: 400,
            title: "Enacted Districting Evaluation",
            showlegend: false,
          }}
        />
      </div>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    updateBWPoints : (pointsData) => {
      dispatch(updateBWPoints(pointsData))
    }
  }
}

const mapStateToProps = (state, ownProps) => {
  return {
    BWBoxes : state.BWBoxes,
    BWPoints : state.BWPoints,
    CurrentDistrictingSummary : state.CurrentDistrictingSummary,
    Jobs: state.Jobs,
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BoxPlot);
