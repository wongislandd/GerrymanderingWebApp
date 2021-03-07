import React, { Component } from 'react'
import { connect } from 'react-redux'
import { Collapsible, Range, Row, CollapsibleItem} from 'react-materialize'
import { Slider } from '@material-ui/core'
import { updateFilterSettings, updateIncumbentProtection } from '../../../../redux/actions/settingActions'
import * as ToolbarUtilities from '../../../../utilities/ToolbarUtilities'
import { FormControlLabel, Checkbox } from '@material-ui/core'

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
                                <FormControlLabel 
                                    control =  {
                                        <Checkbox
                                            id={key + "-protection-checkbox"}
                                            className="incumbent-protection-option"
                                            value={key}
                                            checked={this.props.IncumbentProtectionInfo[key]}
                                            onChange={(e) => this.props.updateIncumbentProtection(key,e.target.checked)}
                                        />
                                    }
                                    label = {key}/>
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