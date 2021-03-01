import React, { Component } from 'react'
import { connect } from 'react-redux'
import { Collapsible, Range, Row, CollapsibleItem, Checkbox } from 'react-materialize'
import { Slider } from '@material-ui/core'
import { updateFilterSettings, updateIncumbentProtection } from '../../../../redux/actions/settingActions'
import * as ToolbarUtilities from '../../../../utilities/ToolbarUtilities'

class FilterSettings extends Component {
    render() {
        return (
            <div className="ToolbarContent settings-sliders">
                  {Object.keys(this.props.FilterSettings).map((key) => {
                      let filter = this.props.FilterSettings[key]
                      if(!Array.isArray(filter.value)) {
                        return(
                            <Row key={key}>
                                <h6>{filter.name}</h6>
                                <Slider
                                onChange={(e,newValue)=>this.props.updateFilterSettings(key, newValue)}
                                value={filter.value}
                                max={filter.maxVal}
                                min={filter.minVal}
                                name={filter.name}
                                step={filter.step}
                                marks
                                valueLabelDisplay="auto"/>
                            </Row>
                        )
                      } else { // Else it's for a range slider, ex. The objective function 
                      return(
                        <Row key={key}>
                            <h6>{filter.name}</h6>
                            <Slider
                            onChange={(e,newValue)=>this.props.updateFilterSettings(key, newValue)}
                            value={filter.value}
                            aria-labelledby="range-slider"
                            name={filter.name}
                            marks
                            max={filter.maxVal}
                            min={filter.minVal}
                            step={filter.step}
                            valueLabelDisplay="auto"/>
                        </Row>
                    )
                      }
                })}
                {/* Incumbent protection  */}
                <Row>
                    <Collapsible className="incumbent-protection-collapsible">
                    <CollapsibleItem
                        expanded={false}
                        header={ToolbarUtilities.LABELS.INCUMBENT_PROTECTION_OPTIONS_LABEL}
                        node="div"
                        >
                        {Object.keys(this.props.IncumbentProtectionInfo).map((key) => {
                            return(
                                <Row
                                    key={key}>
                                <Checkbox
                                    id={key + "-protection-checkbox"}
                                    className="incumbent-protection-option"
                                    label={key}
                                    value={key}
                                    checked={this.props.IncumbentProtectionInfo[key]}
                                    onChange={(e) => this.props.updateIncumbentProtection(key,e.target.checked)}
                                />
                                </Row>
                            )
                        })}
                    </CollapsibleItem>
                    </Collapsible>

                </Row>
            </div>
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        updateFilterSettings : (key, newVal) => {dispatch(updateFilterSettings(key, newVal))},
        updateIncumbentProtection : (key, newVal) => {dispatch(updateIncumbentProtection(key, newVal))}
    }
  }

const mapStateToProps = (state, ownProps) => {
    return {
        FilterSettings : state.FilterSettings,
        IncumbentProtectionInfo : state.IncumbentProtectionInfo
    }
  }
  
export default connect(mapStateToProps, mapDispatchToProps)(FilterSettings);