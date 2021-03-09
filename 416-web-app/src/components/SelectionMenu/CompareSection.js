import React, { Component } from 'react'
import { Button, Row, Col, Icon } from 'react-materialize'
import { connect } from 'react-redux'
import { setComparisonDistrictingA, setComparisonDistrictingB } from '../../redux/actions/settingActions'

class CompareSection extends Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <div className="SelectionMenuSection CompareSection">
                <h5>Compare Districtings</h5>
                    <div className="DistrictingHolderBox labelAndIcon">
                        Districting A: {this.props.ComparisonDistrictingA == null ? "" : this.props.ComparisonDistrictingA.name} 
                        <Icon className={this.props.ComparisonDistrictingA == null ? "hideMe" : ""} 
                        onClick={(e)=>this.props.setComparisonDistrictingA(null)}>cancel</Icon>
                    </div>
                    <div className="DistrictingHolderBox labelAndIcon">
                        Districting B: {this.props.ComparisonDistrictingB == null ? "" : this.props.ComparisonDistrictingB.name}
                        <Icon className={this.props.ComparisonDistrictingB == null ? "hideMe" : ""} 
                        onClick={(e)=>this.props.setComparisonDistrictingB(null)}>cancel</Icon>
                    </div>
            </div>
        )
        }
    }

const mapDispatchToProps = (dispatch) => {
    return {
        setComparisonDistrictingA : (districting) => {dispatch(setComparisonDistrictingA(districting))},
        setComparisonDistrictingB : (districting) => {dispatch(setComparisonDistrictingB(districting))}
    }
  }
  
const mapStateToProps = (state, ownProps) => {
    return {
        ComparisonDistrictingA : state.ComparisonDistrictingA,
        ComparisonDistrictingB : state.ComparisonDistrictingB
    }
  }
  
  export default connect(mapStateToProps, mapDispatchToProps)(CompareSection);