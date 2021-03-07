import React, { Component } from 'react'
import SettingsMode from './ToolbarContentModes/Options/SettingsMode'
import StatsMode from './ToolbarContentModes/Stats/StatsMode'
import HistoryMode from './ToolbarContentModes/History/HistoryMode'
import * as ToolbarUtilities from '../../utilities/ToolbarUtilities'
import TentativeStatsMode from './ToolbarContentModes/History/TentativeStatsMode'

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
            case ToolbarUtilities.MODES.TENTATIVE_STATS:
                return <TentativeStatsMode/>
            default:
                return <div>Default, no mode specified</div>
        } 

    }
}
