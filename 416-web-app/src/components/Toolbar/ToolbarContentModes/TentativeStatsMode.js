import React, { Component } from 'react'
import { connect } from 'react-redux'
import { Row, Collapsible, CollapsibleItem, Table } from 'react-materialize'
import CollapsibleStats from './CollapsibleStats'

class TentativeStatsMode extends Component {
    render() {
        if (this.props.TentativeDistricting == null) {
            return(
                <h5>Your selected districting's stats will be displayed here.</h5>
            )
        } else {
            return (
                <div className="ToolbarContent">
                    <h5>Stat Breakdown</h5>
                    <Row>
                        <CollapsibleStats DistrictingToDisplay={this.props.TentativeDistricting}/>
                    </Row>
                </div>
            )
        }/** Probably need a section for like a stat overview here. Also maybe when you hover over the district it highlights it on the map?
        Should get that working on the main map first  */
    }
}



const mapStateToProps = (state, ownProps) => {
    return {
        TentativeDistricting : state.TentativeDistricting,
    }
  }

export default connect(mapStateToProps)(TentativeStatsMode);
