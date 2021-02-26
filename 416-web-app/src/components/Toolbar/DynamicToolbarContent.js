import React, { Component } from 'react'
import {Table} from 'react-materialize'
import SettingsMode from './ToolbarContentModes/SettingsMode'
import StatsMode from './ToolbarContentModes/StatsMode'
import HistoryMode from './ToolbarContentModes/HistoryMode'
import * as ToolbarUtilities from '../../utilities/ToolbarUtilities'

export default class DynamicToolbarContent extends Component {
    constructor(props) {
        super(props)
    }
    
    render() {
        switch(this.props.mode) {
            case ToolbarUtilities.MODES.SETTINGS:
                return <SettingsMode/>
            case ToolbarUtilities.MODES.STATS:
                return <StatsMode/>
            case ToolbarUtilities.MODES.HISTORY:
                return <HistoryMode/>
            default:
                return <div>default, no mode specified</div>
        } 

    }
}
