import React, { Component } from 'react'
import { Button } from 'react-materialize'
import * as SelectionMenuUtilities from '../../utilities/SelectionMenuUtilities'
import { connect } from 'react-redux'
import { setInSelectionMenu, updateFilterSettings, updateIncumbentProtection } from '../../redux/actions/settingActions'

class FilterSection extends Component {
    constructor(props) {
        super(props)
    }
    
    render() {
        return (
            <div className="SelectionMenuSection FilterSection">
                <Button className="ReturnToMapBtn"
                    onClick={(e) => this.props.setInSelectionMenu(false)}>
                    {SelectionMenuUtilities.LABELS.RETURN_TO_MAP}
                </Button>
            </div>
        )
    }
}



const mapDispatchToProps = (dispatch) => {
    return {
        updateFilterSettings : (key, newVal) => {dispatch(updateFilterSettings(key, newVal))},
        updateIncumbentProtection : (key, newVal) => {dispatch(updateIncumbentProtection(key, newVal))},
        setInSelectionMenu : (bool) => {dispatch(setInSelectionMenu(bool))}
    }
  }

const mapStateToProps = (state, ownProps) => {
    return {
        FilterSettings : state.FilterSettings,
        IncumbentProtectionInfo : state.IncumbentProtectionInfo
    }
}
  
export default connect(mapStateToProps, mapDispatchToProps)(FilterSection);