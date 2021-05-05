import React, { Component } from "react";
import { Tabs, Tab } from "react-materialize";
import ToolbarContentContainer from "./ToolbarContentContainer";
import * as ToolbarUtilities from "../../utilities/ToolbarUtilities";
import { setCurrentTab } from "../../redux/actions/settingActions";
import { connect } from "react-redux";

const TabOptions = {
  duration: 300,
  onShow: null,
  responsiveThreshold: Infinity,
  swipeable: false,
};

/**
 * ToolbarTabsManager
 * Used for controlling what content gets displayed in the toolbar under each tab.
 */
class ToolbarTabsManager extends Component {
  render() {
    return (
      <div className="toolbar-tabs">
        {/* Mode label must match innerText, that is, all caps as the tabs make it in order for this check to work */}
        <Tabs onChange={(e) => this.props.setCurrentTab(e.target.innerHTML)}>
          <Tab
            active={this.props.CurrentTab == ToolbarUtilities.MODES.SETTINGS}
            options={TabOptions}
            title={ToolbarUtilities.MODES.SETTINGS}
          >
            <ToolbarContentContainer
              topMode={ToolbarUtilities.MODES.SETTINGS}
            />
          </Tab>
          <Tab
            active={this.props.CurrentTab == ToolbarUtilities.MODES.STATS}
            options={TabOptions}
            title={ToolbarUtilities.MODES.STATS}
            disabled={this.props.CurrentDistrictingSummary == null}
          >
            <ToolbarContentContainer topMode={ToolbarUtilities.MODES.STATS} />
          </Tab>
        </Tabs>
      </div>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    setCurrentTab: (tab) => {
      dispatch(setCurrentTab(tab));
    },
  };
};

const mapStateToProps = (state, ownProps) => {
  return {
    CurrentTab: state.CurrentTab,
    CurrentDistrictingSummary: state.CurrentDistrictingSummary,
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ToolbarTabsManager);
