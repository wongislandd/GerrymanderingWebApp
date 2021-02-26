import React, { Component } from 'react'
import { Row, Col, Switch } from 'react-materialize'

export default class SettingsMode extends Component {
    constructor(props) {
        super(props)
        this.state = {
            PrecinctSwitched : true,
            DistrictSwitched : true
        }
    }

    PRECINCT_SWITCH_ID = "precinct-switch"
    DISTRICT_SWITCH_ID = "district-switch"

    
    handleToggleSwitch(event) {
        switch(event.target.id) {
            case this.PRECINCT_SWITCH_ID:
                this.setState(prevState => ({
                    PrecinctSwitched : !prevState.PrecinctSwitched
                }))
                break
            case this.DISTRICT_SWITCH_ID:
                this.setState(prevState => ({
                    DistrictSwitched : !prevState.DistrictSwitched
                }))
                break
        }
    }


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
                        id={this.PRECINCT_SWITCH_ID}
                        offLabel="Off"
                        onLabel="On"
                        onChange={(e) => this.handleToggleSwitch(e)}
                        checked={this.state.PrecinctSwitched}
                    />
                    </Row>
                    <Row>
                    <Switch
                        id={this.DISTRICT_SWITCH_ID}
                        offLabel="Off"
                        onLabel="On"
                        onChange={(e) => this.handleToggleSwitch(e)}
                        checked={this.state.DistrictSwitched}
                    />
                    </Row>
                </Col>
            </div>
        )
    }
}
