import React, { Component } from 'react'
import { Row } from 'react-materialize'
import CollapsibleStats from './CollapsibleStats'
import { connect } from 'react-redux'

class StatsMode extends Component {
    render() {
        return (
            <div className="ToolbarContent">
                <h5>Displayed District Stats</h5>
                <Row>
                    <CollapsibleStats DistrictingToDisplay={this.props.CurrentDistricting}/>
                </Row>
            </div>
        )
    }
}


const mapStateToProps = (state, ownProps) => {
    return {
        CurrentDistricting : state.CurrentDistricting
    }
  }
  
  export default connect(mapStateToProps)(StatsMode);