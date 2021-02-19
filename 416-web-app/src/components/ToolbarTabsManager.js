import React, { Component } from 'react'
import {Tabs, Tab} from 'react-materialize'
import ToolbarContentContainer from './ToolbarContentContainer'

const TabOptions = {
    duration: 300,
      onShow: null,
      responsiveThreshold: Infinity,
      swipeable: false
}

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
            <ToolbarContentContainer/>
        </Tab>
        <Tab
            options={TabOptions}
            title="Stats"
        >
            <ToolbarContentContainer/>
        </Tab>
        <Tab
            options={TabOptions}
            title="Big"
        >
            <ToolbarContentContainer/>
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
