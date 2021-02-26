import React, { Component } from 'react'
import { Button, Row, Col } from 'react-materialize'
import { connect } from 'react-redux'

class FilterSummary extends Component {
    render() {
        return (
            <div className="ToolbarContent">
                <h5>Filter Summary</h5>
                {/* <Row>
                {Object.keys(this.props.FilterSettings).map((key) => {
                      let filter = this.props.FilterSettings[key]
                      return(
                          <Row key={key}>
                              <Col>
                              <h6>{filter.name}</h6>
                              </Col>
                              <Col>
                              <h6>{filter.value}</h6>
                              </Col>
                          </Row>
                      )
                })}
                </Row> */}
                <Row>
                    <Button className="confirmationBtn">Filter Districtings</Button>
                </Row>
            </div>
        )
    }
}


const mapStateToProps = (state, ownProps) => {
    return {
        FilterSettings : state.FilterSettings
    }
  }
  
export default connect(mapStateToProps)(FilterSummary);