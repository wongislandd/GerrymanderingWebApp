import React, { Component } from 'react'
import { Button, Row, Col } from 'react-materialize'
import { connect } from 'react-redux'


class CompareSection extends Component {
    constructor(props) {
        super(props)
    }

    render() {
        if (this.props.ComparisonDistrictingA == null || this.props.ComparisonDistrictingB == null) {
            return (
                <div className="SelectionMenuSection CompareSection">
                <Row><h5>Compare Districtings</h5></Row>
                <Row>
                    <Col>
                        <div className="DistrictingHolderBox">
                            Districting A
                        </div>
                    </Col>
                    <Col>
                        <div className="DistrictingHolderBox">
                            Districting B
                        </div>
                    </Col>
                </Row>
                </div>
            )
        } else {
            return (
                <div className="SelectionMenuSection CompareSection">
                <Row><h5>Compare Districtings</h5></Row>
                    <Col>
                        <div className="DistrictingHolderBox">
                            {this.props.ComparisonDistrictingA.name}
                        </div>
                    </Col>
                    <Col>
                        <div className="DistrictingHolderBox">
                            {this.props.ComparisonDistrictingB.name}
                        </div>
                    </Col>
                </div>
            )
        }
    }
}


const mapStateToProps = (state, ownProps) => {
    return {
        ComparisonDistrictingA : state.ComparisonDistrictingA,
        ComparisonDistrictingB : state.ComparisonDistrictingB
    }
  }
  
  export default connect(mapStateToProps)(CompareSection);