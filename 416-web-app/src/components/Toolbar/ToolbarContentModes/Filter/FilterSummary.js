import React, { Component } from 'react'
import { Row, Col, Collapsible, CollapsibleItem, Button} from 'react-materialize'
import PersonIcon from '@material-ui/icons/Person';
import { connect } from 'react-redux'
import * as ToolbarUtilities from '../../../../utilities/ToolbarUtilities'

class FilterSummary extends Component {
    render() {
        return (
            <div className="ToolbarContent">
                <h5>{ToolbarUtilities.LABELS.FILTER_SUMMARY_LABEL}</h5>
                {Object.keys(this.props.FilterSettings).map((key) => {
                      let filter = this.props.FilterSettings[key]
                      return(
                          <Row key={key}>
                              <Col s={8}>
                              <h6>{filter.name}</h6>
                              </Col>
                              <Col s={3}>
                                {/* Exception for ranges such as the objective function*/}
                              <h6>{Array.isArray(filter.value) ? filter.value[0] + "-" + filter.value[1] : filter.value}</h6>
                              </Col>
                          </Row>
                      )
                })}
                <Row>
                <Collapsible className="incumbent-protection-collapsible">
                    <CollapsibleItem
                        expanded={false}
                        header={ToolbarUtilities.LABELS.PROTECTED_POLITICANS_LABEL}
                        node="div"
                        >
                        {Object.keys(this.props.IncumbentProtectionInfo).map((name) => {
                            if (this.props.IncumbentProtectionInfo[name]) {
                                return(
                                    <Row
                                        key={name}>
                                    <div className="iconAndLabel"><PersonIcon/><span className="spanNeedsSpace"> {name}</span></div>
                                    </Row>
                                )
                            }
                        })}
                    </CollapsibleItem>
                    </Collapsible>
                </Row>
                <Row>
                    <Button className="confirmButton">{ToolbarUtilities.LABELS.FILTER_DISTRICTING_CONFIRMATION_LABEL}</Button>
                </Row>
            </div>
        )
    }
}


const mapStateToProps = (state, ownProps) => {
    return {
        FilterSettings : state.FilterSettings,
        IncumbentProtectionInfo : state.IncumbentProtectionInfo
    }
  }
  
export default connect(mapStateToProps)(FilterSummary);