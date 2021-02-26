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
            title="Options"
        >
            <ToolbarContentContainer topMode={ToolbarUtilities.MODES.SETTINGS} bottomMode={ToolbarUtilities.MODES.STATS}/>
        </Tab>
        <Tab
            options={TabOptions}
            title="Stats"
        >
            <ToolbarContentContainer />
        </Tab>
        <Tab
            options={TabOptions}
            title="History"
        >
            <ToolbarContentContainer topMode={ToolbarUtilities.MODES.HISTORY} bottomMode={ToolbarUtilities.MODES.STATS}/>
        </Tab>
        <Tab
            options={TabOptions}
            title="Yoshi"
        >
            <ToolbarContentContainer/>
        </Tab>
        </Tabs>
            </div>
        )
    }
}
