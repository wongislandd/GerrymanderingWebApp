import React, { Component } from 'react'
import { Button } from 'react-materialize'
import * as SelectionMenuUtilities from '../../utilities/SelectionMenuUtilities'
import { connect } from 'react-redux'
import { loadInDistrictings, setInSelectionMenu, setViewingDistrictDetails, updateConstraintSettings, updateIncumbentProtection, updateObjectiveFunctionSettings } from '../../redux/actions/settingActions'
import { Collapsible, Range, Row, CollapsibleItem} from 'react-materialize'
import { FormControlLabel, Checkbox, Slider } from '@material-ui/core'
import * as ToolbarUtilities from '../../utilities/ToolbarUtilities'


import Districting from '../../utilities/classes/Districting'
import EnactedDistrictingPlan2011 from '../../data/NC/EnactedDistrictingPlan2011WithData.json'
import EnactedDistrictingPlan2016 from '../../data/NC/EnactedDistrictingPlan2016WithData.json'
import EnactedDistrictingPlan2019 from '../../data/NC/EnactedDistrictingPlan2019WithData.json'

class FilterSection extends Component {
    constructor(props) {
        super(props)
    }

    /* Eventually I think it'll be best to load all these JSON files in through network instead of local storage. */ 
    districtingsToLoad = [
            new Districting("Enacted Districting Nov 2011 - Feb 2016", EnactedDistrictingPlan2011),
            new Districting("Enacted Districting Feb 2016 - Nov 2019", EnactedDistrictingPlan2016), 
            new Districting("Enacted Districting Nov 2019 - Dec 2021", EnactedDistrictingPlan2019)]

    render() {
        return (
            <div className="SelectionMenuSection FilterSection">
                {/* Button for returning to map */}
                <Button className="ReturnToMapBtn"
                    onClick={(e) => {
                        this.props.setViewingDistrictDetails(false)
                        this.props.setInSelectionMenu(false)
                    }}
                    disabled={this.props.CurrentDistricting == null}>
                    {SelectionMenuUtilities.LABELS.RETURN_TO_MAP}
                </Button>
                {/* Section for Setting Objective Function Weights */}
                <div className="filterSectionItem">
                <h4 className="center-title">{SelectionMenuUtilities.LABELS.OBJECTIVE_FUNCTION_WEIGHTS_HEADER}</h4>
                {Object.keys(this.props.ObjectiveFunctionSettings).map((key) => {
                      let filter = this.props.ObjectiveFunctionSettings[key]
                      if(!Array.isArray(filter.value)) {
                        return(
                            <Row key={key}>
                                <h6>{filter.name} <b>({this.props.ObjectiveFunctionSettings[key].value})</b></h6>
                                <Slider
                                onChange={(e,newValue)=>this.props.updateObjectiveFunctionSettings(key, newValue)}
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
                </div>
                {/* Section for Setting Constriants */}
                <div className="filterSectionItem">
                <h4 className="center-title">{SelectionMenuUtilities.LABELS.CONSTRAINTS_HEADER}</h4>
                {Object.keys(this.props.ConstraintSettings).map((key) => {
                      let filter = this.props.ConstraintSettings[key]
                      if(!Array.isArray(filter.value)) {
                        return(
                            <Row key={key}>
                                <h6>{filter.name} <b>({this.props.ConstraintSettings[key].value})</b></h6>
                                <Slider
                                onChange={(e,newValue)=>this.props.updateConstraintSettings(key, newValue)}
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
                {/* Incumbent protection, part of constraints  */}
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
                {/* Button to apply the filters */}
                <Button className="redBrownBtn" onClick={(e) => this.props.loadInDistrictings(this.districtingsToLoad)}>{SelectionMenuUtilities.LABELS.APPLY_JOB}</Button>
            </div>
        )
    }
}



const mapDispatchToProps = (dispatch) => {
    return {
        updateObjectiveFunctionSettings : (key, newVal) => {dispatch(updateObjectiveFunctionSettings(key, newVal))},
        updateConstraintSettings : (key, newVal) => {dispatch(updateConstraintSettings(key, newVal))},
        updateIncumbentProtection : (key, newVal) => {dispatch(updateIncumbentProtection(key, newVal))},
        setViewingDistrictDetails : (bool) => {dispatch(setViewingDistrictDetails(bool))},
        loadInDistrictings : (districtings) => {dispatch(loadInDistrictings(districtings))},
        setInSelectionMenu : (bool) => {dispatch(setInSelectionMenu(bool))}
    }
  }

const mapStateToProps = (state, ownProps) => {
    return {
        ObjectiveFunctionSettings : state.ObjectiveFunctionSettings,
        ConstraintSettings : state.ConstraintSettings,
        IncumbentProtectionInfo : state.IncumbentProtectionInfo,
        CurrentDistricting : state.CurrentDistricting,
    }
}
  
export default connect(mapStateToProps, mapDispatchToProps)(FilterSection);