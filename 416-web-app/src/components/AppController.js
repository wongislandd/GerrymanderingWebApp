import React, { Component } from "react";
import Toolbar from "./Toolbar/Toolbar";
import MapBoxComponent from "./Map/MapBoxComponent";
import SelectionMenuContainer from "./SelectionMenu/SelectionMenuContainer";
import { connect } from "react-redux";
import StateSelection from "./StateSelection/StateSelection";
import * as ViewportUtilities from "../utilities/ViewportUtilities";
import JobSelection from "./JobSelection/JobSelection";
import BoxPlot from "./StatisticComponents/BoxPlot";

class AppController extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    if (this.props.CurrentState == ViewportUtilities.STATE_OPTIONS.UNSELECTED) {
      return <StateSelection />;
    } else if (this.props.CurrentJob == null) {
      return <JobSelection />;
    } else if (this.props.InSelectionMenu) {
      return (
        <div className="full-screen-flex-container">
          <SelectionMenuContainer />
        </div>
      );
    } else {
      return (
        <div className="full-screen-flex-container MapView">
          <MapBoxComponent />
          <Toolbar />
        </div>
      );
    }
  }
}

const mapStateToProps = (state, ownProps) => {
  return {
    CurrentState: state.CurrentState,
    CurrentJob: state.CurrentJob,
    InSelectionMenu: state.InSelectionMenu,
  };
};

export default connect(mapStateToProps)(AppController);
