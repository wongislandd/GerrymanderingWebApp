import React, { Component } from 'react'
import { Row, Select, Button } from 'react-materialize'
import * as ViewportUtilities from '../../utilities/ViewportUtilities'
import { connect } from 'react-redux'
import { setCurrentState, setTentativeState, setViewport } from '../../redux/actions/settingActions'

class StateSelectionToolbar extends Component {
    handleChange(e) {
        switch(e.target.value) {
            case ViewportUtilities.STATE_OPTIONS.NORTH_CAROLINA:
                this.props.setTentativeState(ViewportUtilities.STATE_OPTIONS.NORTH_CAROLINA)
                this.props.setViewport(ViewportUtilities.NORTH_CAROLINA.Maximized)
                break
            case ViewportUtilities.STATE_OPTIONS.LOUISIANA:
                this.props.setTentativeState(ViewportUtilities.STATE_OPTIONS.LOUISIANA)
                this.props.setViewport(ViewportUtilities.LOUISIANA.Maximized)
                break
            case ViewportUtilities.STATE_OPTIONS.TEXAS:
                this.props.setTentativeState(ViewportUtilities.STATE_OPTIONS.TEXAS)
                this.props.setViewport(ViewportUtilities.TEXAS.Maximized)
                break
            default:
                this.props.setTentativeState(ViewportUtilities.STATE_OPTIONS.UNSELECTED)
                this.props.setViewport(ViewportUtilities.UNSELECTED.Maximized)
                break
        }
    }
    
    selectTentativeState(state) {
        const selector = document.getElementById("state-selector")
        if (selector != null) {
            for (var i =0; i< selector.options.length; i++) {
                if (selector.options[i].value == state) {
                    selector.options[i].selected = true;
                }
            }
        }
    }

    handleClick(e) {
        this.props.setCurrentState(document.getElementById("state-selector").value)
    }

    render() {
        return (
            <div className="toolbar">
                {this.selectTentativeState(this.props.TentativeState)}
                <Row className="centerWithinMe">
                    <h5> Select a State </h5>
                    <Select
                            id="state-selector"
                            multiple={false}
                            onChange={(e) => this.handleChange(e)}
                            options={{
                                classes: '',
                                dropdownOptions: {
                                    alignment: 'left',
                                    autoTrigger: true,
                                    closeOnClick: true,
                                    constrainWidth: true,
                                    coverTrigger: true,
                                    hover: false,
                                    inDuration: 150,
                                    onCloseEnd: null,
                                    onCloseStart: null,
                                    onOpenEnd: null,
                                    onOpenStart: null,
                                    outDuration: 250
                                }
                            }}
                            >
                            <option
                                disabled
                                value={ViewportUtilities.STATE_OPTIONS.UNSELECTED}
                            >
                                Choose your option
                            </option>
                            <option
                            value={ViewportUtilities.STATE_OPTIONS.NORTH_CAROLINA}>
                                North Carolina
                            </option>
                            <option 
                            value={ViewportUtilities.STATE_OPTIONS.LOUISIANA}
                            >
                                Louisiana
                            </option>
                            <option
                            value={ViewportUtilities.STATE_OPTIONS.TEXAS}>
                                Texas
                            </option>
                    </Select>
                    <Button className="redBrownBtn" onClick={(e)=>this.handleClick(e)}>Select this State</Button>
                </Row>
            </div>
        )
    }
}


const mapDispatchToProps = (dispatch) => {
    return {
        setViewport : (viewport) => {dispatch(setViewport(viewport))},
        setCurrentState : (state) => {dispatch(setCurrentState(state))},
        setTentativeState : (state) => {dispatch(setTentativeState(state))},
    }
  }
  
  const mapStateToProps = (state, ownProps) => {
    return {
       TentativeState : state.TentativeState
    }
  }
  
export default connect(mapStateToProps, mapDispatchToProps)(StateSelectionToolbar);