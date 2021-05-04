import React, { Component } from "react";
import { Button } from "react-materialize";
import * as SelectionMenuUtilities from "../../../utilities/SelectionMenuUtilities";
import { connect } from "react-redux";
import {
  loadInDistricting,
  setInSelectionMenu,
  updateConstraintSettings,
  updateIncumbentProtection,
  updatePopulationConstraint,
  updateObjectiveFunctionSettings,
  setNumberOfDistrictingsAvailable,
  resetExpandedSummaries,
  setDistrictingsAreConstrained,
  updateAnalysisDistrictings
} from "../../../redux/actions/settingActions";
import * as StatUtilities from "../../../utilities/StatUtilities";
import { makeStyles } from "@material-ui/core/styles";
import Stepper from "@material-ui/core/Stepper";
import Step from "@material-ui/core/Step";
import StepButton from "@material-ui/core/StepButton";
import Typography from "@material-ui/core/Typography";
import ConstraintSelection from "./ConstraintSelection";
import WeightSelection from "./WeightSelection";
import ReturnToMapButton from "./ReturnToMapButton";
import * as NetworkingUtilities from "../../../network/NetworkingUtilities";



const useStyles = makeStyles((theme) => ({
  root: {
    width: "100%",
  },
  button: {
    marginRight: theme.spacing(1),
  },
  completed: {
    display: "inline-block",
  },
  instructions: {
    marginTop: theme.spacing(1),
    marginBottom: theme.spacing(1),
  },
}));

function shouldDisableConstrain(props) {
  return (props.PopulationSelection == null || props.MinoritySelection == null || props.CompactnessSelection == null);
}


function getSteps() {
  return ["Select Constraints", "Apply Weights"];
}

function getStepContent(step) {
  switch (step) {
    case 0:
      return <ConstraintSelection />;
    case 1:
      return <WeightSelection />;
    default:
      return <div>huh</div>;
  }
}

function getStepCompleteMsg(step) {
  switch (step) {
    case 0:
      return "Apply These Constraints";
    case 1:
      return "Update Weights";
    default:
      return "huh";
  }
}


async function constrainDistrictings(props) {
  NetworkingUtilities.applyConstraints().then(resultSize => {
    props.setNumberOfDistrictingsAvailable(resultSize)
  })
}

async function populateAnalysis(props) {
  NetworkingUtilities.applyWeights().then(analysis => props.updateAnalysisDistrictings(analysis))
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
    console.log()
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

  /* This will be where a POST request is sent to the server; the client will send
   * constraints to spring.
   */

  const handleComplete = async () => {
    if (activeStep == 0) {
      // CONSTRAIN DISTRICTINGS
      constrainDistrictings(props)
      props.setDistrictingsAreConstrained(true)
    }
    if (activeStep == 1) {
      // UPDATE WEIGHTS
      populateAnalysis(props);
      // RESET CURRENTLY DISPLAYED DISTRICTINGS
      props.resetExpandedSummaries()
      // QUERY SERVER FOR NEW SUMMARY
    }
    const newCompleted = completed;
    newCompleted[activeStep] = true;
    setCompleted(newCompleted);
  };
  return (
    <div className="SelectionMenuSection FilterSection">
      {/* Button for returning to map */}
      <ReturnToMapButton />
      <div className={classes.root}>
        <Stepper nonLinear activeStep={activeStep} className="stepInStepper">
          {steps.map((label, index) => (
            <Step key={label}>
              <StepButton
                onClick={handleStep(index)}
                completed={completed[index]}
              >
                {label}
              </StepButton>
            </Step>
          ))}
        </Stepper>
        <div>
          <div className="stepperButtons">
            <Typography component={"span"} className={classes.instructions}>
              {getStepContent(activeStep)}
            </Typography>
            <div className="padAboveMe">
              <div className="districtingCountDiv centerWithinMe">
                <h6>
                  Districting Result Count:{" "}
                  {StatUtilities.addCommas(props.NumDistrictingsAvailable)}{" "}
                </h6>
              </div>
              <Button
                disabled={activeStep === 0}
                onClick={handleBack}
                className={classes.button + " redBrownBtn"}
              >
                Back
              </Button>
              <Button
                onClick={handleNext}
                disabled={activeStep == getSteps().length - 1 || !props.DistrictingsAreConstrained || props.NumDistrictingsAvailable == 0}
                className={classes.button + " redBrownBtn"}
              >
                Next
              </Button>
              <Button
                className={classes.button + " redBrownBtn"}
                onClick={handleComplete}
                disabled={shouldDisableConstrain(props)}
              >
                {getStepCompleteMsg(activeStep)}
              </Button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

const mapDispatchToProps = (dispatch) => {
  return {
    loadInDistricting: (districting) => {
      dispatch(loadInDistricting(districting));
    },
    setNumberOfDistrictingsAvailable: (number) => {
      dispatch(setNumberOfDistrictingsAvailable(number));
    },
    resetExpandedSummaries : () => {
      dispatch(resetExpandedSummaries())
    },
    setDistrictingsAreConstrained : (bool) => {
      dispatch(setDistrictingsAreConstrained(bool))
    },
    updateAnalysisDistrictings : (analysisDict) => {
      dispatch(updateAnalysisDistrictings(analysisDict))
    }
  };
};

const mapStateToProps = (state, ownProps) => {
  return {
    NumDistrictingsAvailable: state.NumDistrictingsAvailable,
    DistrictingsAreConstrained : state.DistrictingsAreConstrained,
    PopulationSelection : state.PopulationSelection,
    MinoritySelection : state.MinoritySelection,
    CompactnessSelection : state.CompactnessSelection,
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(FilterSection);
