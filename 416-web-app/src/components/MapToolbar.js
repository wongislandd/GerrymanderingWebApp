import React, { Component } from 'react'
import ToolbarTabsManager from './ToolbarTabsManager'

export default class MapSidebar extends Component {
    render() {
        return (
            <div class="toolbar-right">
                <ToolbarTabsManager/>
            </div>
        )
    }
}
