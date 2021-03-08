import React, { Component } from 'react'
import { Row, Select } from 'react-materialize'
import * as ViewportUtilities from '../../utilities/ViewportUtilities'

export default class StateSelectionToolbar extends Component {
    render() {
        return (
            <div className="toolbar">
                <Row className="centerWithinMe">
                    <h5> Select a State </h5>
                    <Select
                            id="Select-9"
                            multiple={false}
                            onChange={function noRefCheck(){}}
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
                            value=""
                            >
                            <option
                                disabled
                                value=""
                            >
                                Choose your option
                            </option>
                            <option value={ViewportUtilities.STATE_OPTIONS.NORTH_CAROLINA}>
                                North Carolina
                            </option>
                            <option value={ViewportUtilities.STATE_OPTIONS.CALIFORNIA}>
                                California
                            </option>
                            <option value={ViewportUtilities.STATE_OPTIONS.TEXAS}>
                                Texas
                            </option>
                    </Select>
                </Row>
            </div>
        )
    }
}
