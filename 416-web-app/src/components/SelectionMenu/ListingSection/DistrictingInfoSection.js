import React, { Component } from 'react'
import { connect } from 'react-redux'
import { setCurrentDistricting, setNewDistrictingSelected, setComparisonDistrictingA, setComparisonDistrictingB } from '../../../redux/actions/settingActions'
import { Button } from 'react-materialize'
import * as SelectionMenuUtilities from '../../../utilities/SelectionMenuUtilities'
import DistrictingSummary from '../../StatisticComponents/DistrictingSummary'

/* Properties: 
    this.props.districting => the associated districting to display info for
*/

class DistrictingInfoSection extends Component {
    render() {
        return (
            <div className="districtingInfoSection">
                <DistrictingSummary DistrictingToDisplay={this.props.districting}/>

                <div className="compareOptionsDiv centerWithinMe">
                    <Button className="compareOptionsButton" 
                    onClick={(e)=>this.props.setComparisonDistrictingA(this.props.districting)}
                    disabled={this.props.ComparisonDistrictingA == this.props.districting || this.props.ComparisonDistrictingB == this.props.districting}>
                        {this.props.ComparisonDistrictingA == this.props.districting ? "Chosen as District A" : "Set as District A for Compare"} 
                    </Button>
                    <Button className="compareOptionsButton" 
                    onClick={(e)=>this.props.setComparisonDistrictingB(this.props.districting)}
                    disabled={this.props.ComparisonDistrictingA == this.props.districting || this.props.ComparisonDistrictingB == this.props.districting}>
                        {this.props.ComparisonDistrictingB == this.props.districting ? "Chosen as District B" : "Set as District B for Compare"} 
                        </Button>
                </div>
                <div className="centerWithinMe">
                <Button className="redBrownBtn" onClick={(e)=> {
                    this.props.setCurrentDistricting(this.props.districting)
                    this.props.setNewDistrictingSelected(true)
                }
                }>
                    {SelectionMenuUtilities.LABELS.LOAD_THIS_DISTRICTING}
                </Button>
                </div>
            </div>
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        setCurrentDistricting : (districting) => {dispatch(setCurrentDistricting(districting))},
        setNewDistrictingSelected : (bool) => {dispatch(setNewDistrictingSelected(bool))},
        setComparisonDistrictingA : (districting) => {dispatch(setComparisonDistrictingA(districting))},
        setComparisonDistrictingB : (districting) => {dispatch(setComparisonDistrictingB(districting))},
    }
  }
  

const mapStateToProps = (state, ownProps) => {
    return({
        ComparisonDistrictingA : state.ComparisonDistrictingA,
        ComparisonDistrictingB : state.ComparisonDistrictingB,
    })
  }
  
export default connect(mapStateToProps, mapDispatchToProps)(DistrictingInfoSection);