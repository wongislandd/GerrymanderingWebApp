import React, { Component } from 'react'
import { Button } from 'react-materialize'
import * as SelectionMenuUtilities from '../../utilities/SelectionMenuUtilities'
import { connect } from 'react-redux'
import { setInSelectionMenu, updateFilterSettings, updateIncumbentProtection } from '../../redux/actions/settingActions'
import { Collapsible, Range, Row, CollapsibleItem} from 'react-materialize'
import { FormControlLabel, Checkbox, Slider } from '@material-ui/core'
import * as ToolbarUtilities from '../../utilities/ToolbarUtilities'

class FilterSection extends Component {
    constructor(props) {
        super(props)
    }
    
    render() {
        return (
            <div className="SelectionMenuSection FilterSection">
                <Button className="ReturnToMapBtn"
                    onClick={(e) => this.props.setInSelectionMenu(false)}>
                    {SelectionMenuUtilities.LABELS.RETURN_TO_MAP}
                </Button>
                <div className="filterSectionItem">
                <h4 className="center-title">{SelectionMenuUtilities.LABELS.CONSTRAINTS_HEADER}</h4>
                {Object.keys(this.props.FilterSettings).map((key) => {
                      let filter = this.props.FilterSettings[key]
                      if(!Array.isArray(filter.value)) {
                        return(
                            <Row key={key}>
                                <h6>{filter.name} ({this.props.FilterSettings[key].value})</h6>
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
                <Button>{SelectionMenuUtilities.LABELS.APPLY_FILTERINGS}</Button>
            </div>
        )
    }
}



const mapDispatchToProps = (dispatch) => {
    return {
        updateFilterSettings : (key, newVal) => {dispatch(updateFilterSettings(key, newVal))},
        updateIncumbentProtection : (key, newVal) => {dispatch(updateIncumbentProtection(key, newVal))},
        setInSelectionMenu : (bool) => {dispatch(setInSelectionMenu(bool))}
    }
  }

const mapStateToProps = (state, ownProps) => {
    return {
        FilterSettings : state.FilterSettings,
        IncumbentProtectionInfo : state.IncumbentProtectionInfo
    }
}
  
export default connect(mapStateToProps, mapDispatchToProps)(FilterSection);