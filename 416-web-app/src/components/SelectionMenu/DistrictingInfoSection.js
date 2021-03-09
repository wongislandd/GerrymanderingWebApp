import React, { Component } from 'react'
import { connect } from 'react-redux'
import { setCurrentDistricting } from '../../redux/actions/settingActions'
import { Button } from 'react-materialize'
import * as SelectionMenuUtilities from '../../utilities/SelectionMenuUtilities'
import DistrictingSummary from '../StatisticComponents/DistrictingSummary'

/* Properties: 
    this.props.districting => the associated districting to display info for
*/

class DistrictingInfoSection extends Component {
    render() {
        return (
            <div className="districtingInfoSection">
                <DistrictingSummary DistrictingToDisplay={this.props.districting}/>
                <div className="centerWithinMe">
                <Button className="redBrownBtn" onClick={(e)=>this.props.setCurrentDistricting(this.props.districting)}>
                    {SelectionMenuUtilities.LABELS.LOAD_THIS_DISTRICTING}
                </Button>
                </div>
            </div>
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        setCurrentDistricting : (districting) => {dispatch(setCurrentDistricting(districting))}
    }
  }
  

const mapStateToProps = (state, ownProps) => {
    return({})
  }
  
export default connect(mapStateToProps, mapDispatchToProps)(DistrictingInfoSection);