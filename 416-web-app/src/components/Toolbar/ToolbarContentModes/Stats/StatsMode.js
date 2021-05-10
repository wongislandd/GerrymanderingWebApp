import React, { Component } from "react";
import { Row } from "react-materialize";
import DistrictingSummary from "../../../StatisticComponents/DistrictingSummary";
import { connect } from "react-redux";
import {
  maximizeMap,
  minimizeMap,
} from "../../../../redux/actions/settingActions";

class StatsMode extends Component {
  componentDidMount() {
    this.props.minimizeMap();
  }

  componentWillUnmount() {
    this.props.maximizeMap();
  }

  render() {
    return (
      <div className="ToolbarContent">
        <h5 className="centerWithinMe">Displayed District Stats ({this.props.CurrentDistrictingSummary.id})</h5>
        <div></div>
        <Row>
          <DistrictingSummary
            DistrictingToDisplay={this.props.CurrentDistrictingSummary}
          />
        </Row>
      </div>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    maximizeMap: () => {
      dispatch(maximizeMap());
    },
    minimizeMap: () => {
      dispatch(minimizeMap());
    },
  };
};

const mapStateToProps = (state, ownProps) => {
  return {
    CurrentDistrictingSummary: state.CurrentDistrictingSummary,
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(StatsMode);
