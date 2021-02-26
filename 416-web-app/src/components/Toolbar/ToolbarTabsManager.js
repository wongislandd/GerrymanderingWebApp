import React, { Component } from 'react'
import {Tabs, Tab} from 'react-materialize'
import ToolbarContentContainer from './ToolbarContentContainer'
import * as ToolbarUtilities from '../../utilities/ToolbarUtilities'

const TabOptions = {
    duration: 300,
      onShow: null,
      responsiveThreshold: Infinity,
      swipeable: false
}

/**
 * ToolbarTabsManager
 * Used for controlling what content gets displayed in the toolbar under each tab.
 */
export default class ToolbarTabsManager extends Component {
    render() {
        return (
            <div className = "toolbar-tabs">
    <Tabs>
        <Tab
            active
            options={TabOptions}
            title={ToolbarUtilities.LABELS.TOOLBAR_OPTIONS_HEADER_LABEL}
        >
            <ToolbarContentContainer topMode={ToolbarUtilities.MODES.SETTINGS}/>
        </Tab>
        <Tab
            options={TabOptions}
            title={ToolbarUtilities.LABELS.TOOLBAR_STATISTICS_HEADER_LABEL}
        >
            <ToolbarContentContainer topMode={ToolbarUtilities.MODES.STATS} bottomMode={ToolbarUtilities.MODES.STATS}/>
        </Tab>
        <Tab
            options={TabOptions}
            title={ToolbarUtilities.LABELS.TOOLBAR_HISTORY_HEADER_LABEL}
        >
            <ToolbarContentContainer topMode={ToolbarUtilities.MODES.HISTORY} bottomMode={ToolbarUtilities.MODES.TENTATIVE_STATS}/>
        </Tab>
        <Tab
            options={TabOptions}
            title="Big Yoshi"
        >
            <ToolbarContentContainer />
        </Tab>
        </Tabs>
            </div>
        )
    }
}
