import React, { Component } from 'react'
import SettingsMode from './ToolbarContentModes/Options/SettingsMode'
import StatsMode from './ToolbarContentModes/Stats/StatsMode'
import HistoryMode from './ToolbarContentModes/History/HistoryMode'
import * as ToolbarUtilities from '../../utilities/ToolbarUtilities'
import TentativeStatsMode from './ToolbarContentModes/History/TentativeStatsMode'
import { connect } from 'react-redux'

class DynamicToolbarContent extends Component {
    constructor(props) {
        super(props)
    }
    
    render() {
        switch(this.props.mode) {
            case ToolbarUtilities.MODES.SETTINGS:
                return (this.props.CurrentTab == this.props.mode ? <SettingsMode/> : <div/>)
            case ToolbarUtilities.MODES.STATS:
                return (this.props.CurrentTab == this.props.mode ? <StatsMode/> : <div/>)
            default:
                return <div>Default, no mode specified</div>
        } 

    }
}



  
  const mapStateToProps = (state, ownProps) => {
    return {
        CurrentTab : state.CurrentTab
    }
  }
  
  export default connect(mapStateToProps)(DynamicToolbarContent);