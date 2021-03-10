import React, { Component } from 'react'
import * as SelectionMenuUtilities from '../../../utilities/SelectionMenuUtilities'
import {updateIncumbentProtection, updatePopulationConstraint } from '../../../redux/actions/settingActions'
import { Row, Select, Icon, Collapsible, CollapsibleItem} from 'react-materialize'
import { FormControlLabel, Checkbox, Slider } from '@material-ui/core'
import { connect } from 'react-redux'

class ConstraintSelection extends Component {
    render() {
        return (
            <div className="filterSectionItem">
                <h4 className="center-title">{SelectionMenuUtilities.LABELS.CONSTRAINTS_HEADER}</h4>
                
                {/* Population Constraint Selection */}
                <h6>{SelectionMenuUtilities.LABELS.POPULATION_TO_ACCOUNT_FOR}</h6>
                <Select
                    icon={<Icon>people</Icon>}
                    id="Select-9"
                    multiple={false}
                    onChange={(e)=> 
                        this.props.updatePopulationConstraint(e.target.value)
                    }
                    options={{
                        classes: '',
                        dropdownOptions: {
                        alignment: 'left',
                        autoTrigger: true,
                        closeOnClick: true,
                        constrainWidth: true,
                        coverTrigger: true,
                        hover: false,
                        inDuration: 150,
                        onCloseEnd: null,
                        onCloseStart: null,
                        onOpenEnd: null,
                        onOpenStart: null,
                        outDuration: 250
                        }
                    }}
                    value=""
                    >
                    <option
                        disabled
                        value=""
                    >
                        Choose a Population
                    </option>
                    {Object.keys(this.props.PopulationConstraintInfo).map((key) => {
                    return (
                        <option key = {key} value={key}>
                            {key}
                        </option>
                    )
                    })}
                    </Select>
                {/* Incumbent protection, part of constraints  */}
                <Row>
                    <Collapsible className="constraint-collapsible">
                    <CollapsibleItem
                        expanded={false}
                        header={SelectionMenuUtilities.LABELS.INCUMBENT_PROTECTION_OPTIONS}
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
        updateIncumbentProtection : (key, newVal) => {dispatch(updateIncumbentProtection(key, newVal))},
        updatePopulationConstraint : (key, newVal) => {dispatch(updatePopulationConstraint(key, newVal))},
    }
  }

const mapStateToProps = (state, ownProps) => {
    return {
        IncumbentProtectionInfo : state.IncumbentProtectionInfo,
        PopulationConstraintInfo : state.PopulationConstraintInfo
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(ConstraintSelection);