import React, { Component } from "react";
import Plot from "react-plotly.js";
import { connect } from 'react-redux'
import * as NetworkingUtilities from '../../network/NetworkingUtilities'
import * as SelectionMenuUtilities from '../../utilities/SelectionMenuUtilities'
import { updateBWPoints } from "../../redux/actions/settingActions";

class BoxPlot extends Component {
  componentDidMount() {
    NetworkingUtilities.getPointsData(this.props.districtingId).then((pointsData) =>
      this.props.updateBWPoints(pointsData)
    )
  }

  dataToShowInPlot() {
    let ret = []
    if (this.props.BWBoxes != null) {
      ret = ret.concat(this.props.BWBoxes)
    }
    if (this.props.BWPoints != null) {
      ret = ret.concat(this.props.BWPoints)
    }
    if (this.props.BWEnacted != null) {
      ret = ret.concat(this.props.BWEnacted)
    }
    return ret
  }

  render() {
    return (
      <div className="centerWithinMe">
        <Plot
          data={this.dataToShowInPlot()}
          layout={{
            width: 900,
            height: 400,
            title: SelectionMenuUtilities.MINORITIES[this.props.MinoritySelection] + " Minority Population Evaluation",
            showlegend: true,
            xaxis: {
              title: {
                text: 'Minority-Percentage Ranked Districts',
              }
            },
            yaxis: {
              title: {
                text: 'Minority Percentage',
              }
            }
          }
        }
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
    BWEnacted : state.BWEnacted,
    MinoritySelection : state.MinoritySelection,
    CurrentDistrictingSummary : state.CurrentDistrictingSummary,
    Jobs: state.Jobs,
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BoxPlot);
