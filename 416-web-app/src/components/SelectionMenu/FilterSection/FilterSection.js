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
    return ['Select Constraints', 'Apply Weights', 'View Districtings'];
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
      case 2:
        return(
            <div>Show something useful here. Box and Whisker?</div>
        );
      default:
        return(
            <div>huh</div>
        );
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
      const newCompleted = completed;
      newCompleted[activeStep] = true;
      setCompleted(newCompleted);
      handleNext();
    };
  
    const handleReset = () => {
      setActiveStep(0);
      setCompleted({});
    };

        return (
        <div className="SelectionMenuSection FilterSection">
           {/* Button for returning to map */}
            <ReturnToMapButton/>
            <div className={classes.root}>
                <Stepper nonLinear activeStep={activeStep}>
                    {steps.map((label, index) => (
                    <Step key={label}>
                        <StepButton onClick={handleStep(index)} completed={completed[index]}>
                        {label}
                        </StepButton>
                    </Step>
                    ))}
                </Stepper>
                <div>
                    {allStepsCompleted() ? (
                    <div>
                        <Typography component={'span'} className={classes.instructions}>
                        All steps completed - you&apos;re finished
                        </Typography>
                        <Button onClick={handleReset}>Reset</Button>
                    </div>
                    ) : (
                    <div>
                        <Typography component={'span'} className={classes.instructions}>{getStepContent(activeStep)}</Typography>
                        <div>
                        <Button disabled={activeStep === 0} onClick={handleBack} className={classes.button}>
                            Back
                        </Button>
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={handleNext}
                            className={classes.button}
                        >
                            Next
                        </Button>
                        {activeStep !== steps.length &&
                            (completed[activeStep] ? (
                            <Typography component={'span'} variant="caption" className={classes.completed}>
                                Step {activeStep + 1} already completed
                            </Typography>
                            ) : (
                            <Button variant="contained" color="primary" onClick={handleComplete}>
                                {completedSteps() === totalSteps() - 1 ? 'Finish' : 'Complete Step'}
                            </Button>
                            ))}
                        </div>
                    </div>
                    )}
                </div>
                </div>
                </div>
            // <div className="SelectionMenuSection FilterSection">
            //     {/* Button for returning to map */}
            //     <ReturnToMapButton/>
            //     {/* Section for Setting Objective Function Weights */}
            //     <WeightSelection/>
            //     {/* Section for Setting Constriants */}
            //     <ConstraintSelection/>
            //     {/* Button to apply the filters */}
            //     <Button className="redBrownBtn" onClick={(e) => props.loadInDistrictings(this.districtingsToLoad)}>{SelectionMenuUtilities.LABELS.APPLY_JOB}</Button>
            // </div>
        )
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