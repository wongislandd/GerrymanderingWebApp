import React, { Component } from 'react'
import { Icon, Row } from 'react-materialize'
import * as StatUtilities from '../../utilities/StatUtilities'


export default class ComparisonItem extends Component {
    /* What direction to display ^ or v, the value, label, and the difference % that caused the direction */
    constructor(props) {
        super(props)
    }
    render() {
        return (
            <div className="centerWithinMe">
                <Row>
                    <b>{this.props.label}</b>
                </Row>
                <Row>
                <Icon medium>{this.props.direction == StatUtilities.COMPARISON_DIRECTIONS.NONE ? "cloud"
                : this.props.direction == StatUtilities.COMPARISON_DIRECTIONS.UP ? "arrow_upward" : "arrow_downward"}</Icon>
                </Row>
                <Row className={this.props.direction == StatUtilities.COMPARISON_DIRECTIONS.NONE ? ""
                : this.props.direction == StatUtilities.COMPARISON_DIRECTIONS.UP ? "greenText" : "redText"}>
                    {this.props.value} ({this.props.pct})
                </Row>
            </div>
        )
    }
}
