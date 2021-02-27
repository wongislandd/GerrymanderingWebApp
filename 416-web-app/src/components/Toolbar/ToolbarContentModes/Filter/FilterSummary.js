import React, { Component } from 'react'
import { Button, Row, Col } from 'react-materialize'
import { connect } from 'react-redux'

class FilterSummary extends Component {
    render() {
        return (
            <div className="ToolbarContent">
                <h5>Filter Summary</h5>
                {Object.keys(this.props.FilterSettings).map((key) => {
                      let filter = this.props.FilterSettings[key]
                      return(
                          <Row key={key}>
                              <Col s={8}>
                              <h6>{filter.name}</h6>
                              </Col>
                              <Col s={3}>
                              <h6>{Array.isArray(filter.value) ? filter.value[0] + "-" + filter.value[1] : filter.value}</h6>
                              </Col>
                          </Row>
                      )
                })}
                <Row>
                    <Button className="confirmButton">Filter Districtings With These Settings</Button>
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