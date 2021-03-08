import React, { Component } from 'react'
import { Row } from 'react-materialize'
import CollapsibleStats from '../CollapsibleStats'
import { connect } from 'react-redux'
import { setViewingDistrictDetails } from '../../../../redux/actions/settingActions'

class StatsMode extends Component {
    
    componentDidMount() {
        this.props.setViewingDistrictDetails(true)
    }

    componentWillUnmount() {
        this.props.setViewingDistrictDetails(false)
    }

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

const mapDispatchToProps = (dispatch) => {
    return {
        setViewingDistrictDetails : (bool) => {dispatch(setViewingDistrictDetails(bool))}
    }
}


const mapStateToProps = (state, ownProps) => {
    return {
        CurrentDistricting : state.CurrentDistricting
    }
  }
  
  export default connect(mapStateToProps, mapDispatchToProps)(StatsMode);