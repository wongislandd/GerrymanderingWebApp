import React, { Component } from 'react'
import { Row } from 'react-materialize'
import DistrictingSummary from '../DistrictingSummary'
import { connect } from 'react-redux'
import { maximizeMap, minimizeMap, setViewingDistrictDetails } from '../../../../redux/actions/settingActions'

class StatsMode extends Component {
    
    componentDidMount() {
        /* Do the minification of the map here p*/
        this.props.minimizeMap()
        this.props.setViewingDistrictDetails(true)
    }

    componentWillUnmount() {
        this.props.maximizeMap()
        this.props.setViewingDistrictDetails(false)
    }

    render() {
        return (
            <div className="ToolbarContent">
                <h5>Displayed District Stats</h5>
                <Row>
                    <DistrictingSummary DistrictingToDisplay={this.props.CurrentDistricting}/>
                </Row>
            </div>
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        setViewingDistrictDetails : (bool) => {dispatch(setViewingDistrictDetails(bool))},
        maximizeMap : () => {dispatch(maximizeMap())},
        minimizeMap : () => {dispatch(minimizeMap())},
    }
}


const mapStateToProps = (state, ownProps) => {
    return {
        CurrentDistricting : state.CurrentDistricting
    }
  }
  
  export default connect(mapStateToProps, mapDispatchToProps)(StatsMode);