import React, { Component } from 'react'
import { Row, Col, Switch } from 'react-materialize'
import * as ToolbarUtilities from '../../../utilities/ToolbarUtilities.js'
import * as ActionTypes from '../../../redux/reducers/ActionTypes'
import { connect } from 'react-redux'

class SettingsMode extends Component {
    render() {
        return (
            <div className="ToolbarSettingsMode">
                <Col s={8}>
                <Row>
                    Outline Precincts
                </Row>
                <Row>
                    Outline Districts
                </Row>
                </Col>
                <Col>
                    <Row>
                    <Switch
                        id={ToolbarUtilities.CONSTANTS.PRECINCT_SWITCH_ID}
                        offLabel="Off"
                        onLabel="On"
                        onChange = {(e)=>this.props.togglePrecinctSwitch(!this.props.DisplayPrecincts)}
                        checked = {this.props.DisplayPrecincts}
                    />
                    </Row>
                    <Row>
                    <Switch
                        id={ToolbarUtilities.CONSTANTS.DISTRICT_SWITCH_ID}
                        offLabel="Off"
                        onLabel="On"
                        onChange = {(e)=>this.props.toggleDistrictSwitch(!this.props.DisplayDistricts)}
                        checked = {this.props.DisplayDistricts}
                    />
                    </Row>
                </Col>
            </div>
        )
    }
}



const mapDispatchToProps = (dispatch) => {
    return {
        togglePrecinctSwitch : (bool) => {dispatch({type: ActionTypes.TOGGLE_PRECINCT_SWITCH, DisplayPrecincts : bool})},
        toggleDistrictSwitch : (bool) => {dispatch({type: ActionTypes.TOGGLE_DISTRICT_SWITCH, DisplayDistricts : bool})}
    }
}


const mapStateToProps = (state, ownProps) => {
    return {
        DisplayPrecincts : state.DisplayPrecincts,
        DisplayDistricts : state.DisplayDistricts
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(SettingsMode)