import React, { Component } from 'react'
import { Button } from 'react-materialize'
import * as SelectionMenuUtilities from '../../../utilities/SelectionMenuUtilities'
import { connect } from 'react-redux'
import { loadInDistrictings, restoreDefaultStateForNewDistricting, setInSelectionMenu, updateConstraintSettings, updateIncumbentProtection, updatePopulationConstraint, updateObjectiveFunctionSettings } from '../../../redux/actions/settingActions'
import { Collapsible, Row, CollapsibleItem, Select, Icon} from 'react-materialize'
import { FormControlLabel, Checkbox, Slider } from '@material-ui/core'
import { makeStyles } from '@material-ui/core/styles';
import Stepper from '@material-ui/core/Stepper';
import Step from '@material-ui/core/Step';
import StepButton from '@material-ui/core/StepButton';
import Typography from '@material-ui/core/Typography';
import ConstraintSelection from './ConstraintSelection'



import Districting from '../../../utilities/classes/Districting'
import EnactedDistrictingPlan2011 from '../../../data/NC/EnactedDistrictingPlan2011WithData.json'
import EnactedDistrictingPlan2016 from '../../../data/NC/EnactedDistrictingPlan2016WithData.json'
import EnactedDistrictingPlan2019 from '../../../data/NC/EnactedDistrictingPlan2019WithData.json'
import WeightSelection from './WeightSelection'
import ReturnToMapButton from './ReturnToMapButton'

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
                <ReturnToMapButton/>
                {/* Section for Setting Objective Function Weights */}
                <WeightSelection/>
                {/* Section for Setting Constriants */}
                <ConstraintSelection/>
                {/* Button to apply the filters */}
                <Button className="redBrownBtn" onClick={(e) => this.props.loadInDistrictings(this.districtingsToLoad)}>{SelectionMenuUtilities.LABELS.APPLY_JOB}</Button>
            </div>
        )
    }
}



const mapDispatchToProps = (dispatch) => {
    return {
        loadInDistrictings : (districtings) => {dispatch(loadInDistrictings(districtings))},
    }
  }

const mapStateToProps = (state, ownProps) => {
    return {
    }
}
  
export default connect(mapStateToProps, mapDispatchToProps)(FilterSection);