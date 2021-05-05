import React, { Component } from "react";
import {
  Button,
  Col,
  Row,
  Collapsible,
  Icon,
  CollapsibleItem,
} from "react-materialize";
import { connect } from "react-redux";
import {
  loadInJobs,
  setCurrentState,
  setCurrentJob,
  populatePrecincts,
  populateCounties,
  populateCurrentDistrictingGeoJson,
  populateCurrentDistrictingSummary,
} from "../../redux/actions/settingActions";
import * as SelectionMenuUtilities from "../../utilities/SelectionMenuUtilities";
import * as ViewportUtilities from "../../utilities/ViewportUtilities";
import * as NetworkingUtilities from "../../network/NetworkingUtilities";

class JobSelection extends Component {
  componentDidMount() {
    this.loadJobs();
    this.loadEnactedDistricting();
    this.populateCounties();
    this.populatePrecincts();
  }


  async loadJobs() {
    NetworkingUtilities.loadJobs(this.props.CurrentState).then((jobs) =>
      this.props.loadInJobs(jobs)
    );
  }

  async loadEnactedDistricting() {
    NetworkingUtilities.loadEnactedSummary(this.props.CurrentState)
      .then((summary) => this.props.populateCurrentDistrictingSummary(summary))
      .then(
        NetworkingUtilities.loadEnacted(this.props.CurrentState).then(
          (results) => {
            this.props.populateCurrentDistrictingGeoJson(results);
          }
        )
      );
  }

  async populatePrecincts() {
    NetworkingUtilities.loadPrecincts(this.props.CurrentState).then(
      (results) => {
        this.props.populatePrecincts(results);
      }
    );
  }

  async populateCounties() {
    NetworkingUtilities.loadCounties(this.props.CurrentState).then(
      (results) => {
        console.log(results);
        this.props.populateCounties(results);
      }
    );
  }

  async selectJob(job) {
    NetworkingUtilities.initializeJob(job.id)
    this.props.setCurrentJob(job)
  }

  render() {
    return (
      <div className="job-selection-screen centered">
        <Row>
          <Col s={5}>
            <Button
              className="redBrownBtn returnToStateSelectionBtn"
              onClick={(e) =>
                this.props.setCurrentState(
                  ViewportUtilities.STATE_OPTIONS.UNSELECTED
                )
              }
            >
              {SelectionMenuUtilities.LABELS.BACK_TO_STATE_SELECTION}
            </Button>
          </Col>
          <Col>
            <h5>{SelectionMenuUtilities.LABELS.SELECT_A_JOB}</h5>
          </Col>
        </Row>
        {/** Job Selection Area**/}
        <div className="job-selection-screen-display">
          <Collapsible accordion>
            {this.props.Jobs.length === 0 ? (
              <div>{"Initializing " + this.props.CurrentState + "."}</div>
            ) : (
              <div />
            )}
            {Object.keys(this.props.Jobs).map((key) => {
              let job = this.props.Jobs[key];
              return (
                <CollapsibleItem
                  expanded={false}
                  key={key}
                  header={<div>Job {job.id}</div>}
                  icon={<Icon>archive</Icon>}
                  node="div"
                >
                  <Row>
                    <h5>Districtings in this Job</h5>
                  </Row>
                  <Row>
                    <b>{job.size}</b>
                  </Row>
                  <Row>
                    <h5>Job Description</h5>
                  </Row>
                  <Row>{job.description}</Row>
                  <Row>
                    <h5>MGGG Parameters</h5>
                  </Row>
                  {Object.keys(job.params).map((key) => {
                    if (key == "id") {
                      return <div key={key}></div>;
                    }
                    return (
                      <Row key={key}>
                        <b>{key}</b> : {job.params[key]}
                      </Row>
                    );
                  })}
                  <Button
                    className="redBrownBtn"
                    onClick={(e) => this.selectJob(job)}
                  >
                    {SelectionMenuUtilities.LABELS.CONTINUE_WITH_THIS_JOB}
                  </Button>
                </CollapsibleItem>
              );
            })}
          </Collapsible>
        </div>
      </div>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    setCurrentState: (state) => {
      dispatch(setCurrentState(state));
    },
    loadInJobs: (state) => {
      dispatch(loadInJobs(state));
    },
    populatePrecincts: (precincts) => {
      dispatch(populatePrecincts(precincts));
    },
    populateCounties: (counties) => {
      dispatch(populateCounties(counties));
    },
    populateCurrentDistrictingGeoJson: (districting) => {
      dispatch(populateCurrentDistrictingGeoJson(districting));
    },
    populateCurrentDistrictingSummary: (summary) => {
      dispatch(populateCurrentDistrictingSummary(summary));
    },
    setCurrentJob: (job) => {
      dispatch(setCurrentJob(job));
    },
  };
};

const mapStateToProps = (state, ownProps) => {
  return {
    CurrentState: state.CurrentState,
    Jobs: state.Jobs,
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(JobSelection);
