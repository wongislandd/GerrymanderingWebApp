import React, { Component } from 'react'
import { Button } from 'react-materialize'
import * as SelectionMenuUtilities from '../../../utilities/SelectionMenuUtilities'
import { connect } from 'react-redux'
import { loadInDistrictings, restoreDefaultStateForNewDistricting, setInSelectionMenu, updateConstraintSettings, updateIncumbentProtection, updatePopulationConstraint, updateObjectiveFunctionSettings, setNumberOfDistrictingsAvailable } from '../../../redux/actions/settingActions'
import { Collapsible, Row, CollapsibleItem, Select, Icon} from 'react-materialize'
import { FormControlLabel, Checkbox, Slider } from '@material-ui/core'
import * as StatUtilities from '../../../utilities/StatUtilities'
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


const districtingsToLoad = [
    new Districting("Enacted Districting Nov 2011 - Feb 2016", EnactedDistrictingPlan2011),
    new Districting("Enacted Districting Feb 2016 - Nov 2019", EnactedDistrictingPlan2016), 
    new Districting("Enacted Districting Nov 2019 - Dec 2021", EnactedDistrictingPlan2019)]

const useStyles = makeStyles((theme) => ({
    root: {
      width: '100%',
    },
    button: {
      marginRight: theme.spacing(1),
    },
    completed: {
      display: 'inline-block',
    },
    instructions: {
      marginTop: theme.spacing(1),
      marginBottom: theme.spacing(1),
    },
  }));
  
  function getSteps() {
    return ['Select Constraints', 'Apply Weights'];
  }
  
  function getStepContent(step) {
    switch (step) {
      case 0:
        return(
            <ConstraintSelection/>
        );
      case 1:
        return(
            <WeightSelection/>
        );
      default:
        return(
            <div>huh</div>
        );
    }
  }

  function getStepCompleteMsg(step) {
    switch (step) {
        case 0:
          return "Apply These Constraints"
        case 1:
          return "Update Weights"
        default:
          return "huh"
      }
  }
  
function FilterSection(props) {
    const classes = useStyles();
    const [activeStep, setActiveStep] = React.useState(0);
    const [completed, setCompleted] = React.useState({});
    const steps = getSteps();
  
    const totalSteps = () => {
      return steps.length;
    };
  
    const completedSteps = () => {
      return Object.keys(completed).length;
    };
  
    const isLastStep = () => {
      return activeStep === totalSteps() - 1;
    };
  
    const allStepsCompleted = () => {
      return completedSteps() === totalSteps();
    };
  
    const handleNext = () => {
      const newActiveStep =
        isLastStep() && !allStepsCompleted()
          ? // It's the last step, but not all steps have been completed,
            // find the first step that has been completed
            steps.findIndex((step, i) => !(i in completed))
          : activeStep + 1;
      setActiveStep(newActiveStep);
    };
  
    const handleBack = () => {
      setActiveStep((prevActiveStep) => prevActiveStep - 1);
    };
  
    const handleStep = (step) => () => {
      setActiveStep(step);
    };
  
    const handleComplete = () => {
      if(activeStep == 0) {
        // LOAD DISTRICTINGS, UPDATE COUNT NUMBER
        props.loadInDistrictings(districtingsToLoad)
        props.setNumberOfDistrictingsAvailable(StatUtilities.rollARandomNumberOfDistrictings())
      }
      if(activeStep == 1) {
        // UPDATE WEIGHTS
      }
      const newCompleted = completed;
      newCompleted[activeStep] = true;
      setCompleted(newCompleted);
    };

      console.log(props)
        return (
        <div className="SelectionMenuSection FilterSection">
           {/* Button for returning to map */}
            <ReturnToMapButton/>
            <div className={classes.root}>
                <Stepper nonLinear activeStep={activeStep} className="stepInStepper">
                    {steps.map((label, index) => (
                    <Step key={label}>
                        <StepButton onClick={handleStep(index)} completed={completed[index]}>
                        {label}
                        </StepButton>
                    </Step>
                    ))}
                </Stepper>
                <div>
                    <div className="stepperButtons">
                        <Typography component={'span'} className={classes.instructions}>{getStepContent(activeStep)}</Typography>
                        <div className="padAboveMe">
                        <div className="districtingCountDiv centerWithinMe"><h6>Districting Result Count: {StatUtilities.addCommas(props.NumDistrictingsAvailable)} </h6></div>
                        <Button disabled={activeStep === 0} onClick={handleBack} className={classes.button + " redBrownBtn"} >
                            Back
                        </Button>
                        <Button
                            onClick={handleNext}
                            disabled={activeStep == getSteps().length-1}
                            className= {classes.button + " redBrownBtn"}
                        >
                            Next
                        </Button>
                            <Button className={classes.button + " redBrownBtn"} onClick={handleComplete}>
                                {getStepCompleteMsg(activeStep)}
                            </Button>   
                        </div>
                      </div>

                </div>
                </div>
                </div>
        )
    }



const mapDispatchToProps = (dispatch) => {
    return {
        loadInDistrictings : (districtings) => {dispatch(loadInDistrictings(districtings))},
        setNumberOfDistrictingsAvailable : (number) => {dispatch(setNumberOfDistrictingsAvailable(number))},
    }
  }

const mapStateToProps = (state, ownProps) => {
    return {
      NumDistrictingsAvailable : state.NumDistrictingsAvailable
    }
}
  
export default connect(mapStateToProps, mapDispatchToProps)(FilterSection);