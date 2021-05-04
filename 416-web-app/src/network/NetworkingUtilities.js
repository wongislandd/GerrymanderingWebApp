import state from "../redux/store"; 
import * as ParsingUtilities from './ParsingUtilities'
import * as ViewportUtilities from '../utilities/ViewportUtilities'
import * as SelectionMenuUtilities from '../utilities/SelectionMenuUtilities'

const axios = require('axios').default
const baseURL = "http://localhost:8080";

export const HTTPMETHODS = {
  GET: "GET",
  POST: "POST",
}


function getFullRequestURLWithParams(endpoint, params) {
  const URLParams = new URLSearchParams(params);
  return baseURL + endpoint + "?" + URLParams.toString();
}





/* Load an individual districting based on ID */
export async function loadDistricting(id) {
  let fullUrl = baseURL + "/districtings/load/" + id;
  const response = await fetch(fullUrl); 
  let body = await response.json();
  return body;
}



export async function loadStateOutlines() {
  let fullUrl = baseURL + "/states/getOutlines";
  const response = await fetch(fullUrl);
  let body = await response.json();
  let result = {
   [ViewportUtilities.STATE_OPTIONS.NORTH_CAROLINA] : JSON.parse(body.NC),
   [ViewportUtilities.STATE_OPTIONS.LOUISIANA] : JSON.parse(body.LA),
   [ViewportUtilities.STATE_OPTIONS.TEXAS] : JSON.parse(body.TX),
  }
  return result;
}


export async function loadEnacted(state) {
  let fullUrl = baseURL + "/districtings/" + state + "/enacted/load";
  const response = await fetch(fullUrl);
  let body = await response.json();
  return body;
}


export async function loadJobs(state) {
  let fullUrl = baseURL + "/jobs/" + state + "/loadJobs";
  const response = await fetch(fullUrl);
  let body = await response.json();
  let jobs = ParsingUtilities.parseJobJSONToObjects(body)
  return jobs;
}

export async function loadIncumbents(state) {
  let fullUrl = baseURL + "/incumbents/" + state + "/loadIncumbents";
  const response = await fetch(fullUrl);
  let body = await response.json();
  console.log(body);
  let incumbents = ParsingUtilities.parseIncumbentsJSONToObjects(body);
  return incumbents;
}


export async function loadPrecincts(state) {
    let fullUrl = baseURL + "/precincts/" + state + "/loadPrecincts";
    const response = await fetch(fullUrl);
    let body = await response.json();
    return body;
}

export async function loadCounties(state) {
  let fullUrl = baseURL + "/counties/" + state + "/loadCounties";
  const response = await fetch(fullUrl);
  let body = await response.json();
  return body;
}

export async function applyConstraints() {
  let currentState = state.getState()
  let fullUrl = baseURL + "/districtings/constrain";
  const payload = {
    jobId : currentState.CurrentJob.id,
    votingPopulation : currentState.PopulationSelection,
    minorityThreshold : currentState.ConstraintSliderSettings[SelectionMenuUtilities.CONSTRAINT_KEYS.MinorityThreshold].value,
    maxPopulationDifference : currentState.ConstraintSliderSettings[SelectionMenuUtilities.CONSTRAINT_KEYS.PopulationDifference].value,
    minorityPopulation : currentState.MinoritySelection,
    minMinorityDistricts : currentState.ConstraintSliderSettings[SelectionMenuUtilities.CONSTRAINT_KEYS.MajorityMinorityDistricts].value,
    compactnessType : currentState.CompactnessSelection,
    compactnessThreshold : currentState.ConstraintSliderSettings[SelectionMenuUtilities.CONSTRAINT_KEYS.Compactness].value,
  }
  let resultSize = await axios.post(fullUrl, payload, {withCredentials: true}).then(response => {
    return response.data;
  });
  return resultSize;
}

export async function applyWeights() {
  let currentState = state.getState()
  let fullUrl = baseURL + "/districtings/applyWeights";
  const payload = {
    populationEquality : currentState.ObjectiveFunctionSettings[0].value,
    splitCounties : currentState.ObjectiveFunctionSettings[1].value,
    deviationFromAverage : currentState.ObjectiveFunctionSettings[2].value,
    deviationFromEnacted : currentState.ObjectiveFunctionSettings[3].value,
    compactness : currentState.ObjectiveFunctionSettings[4].value,
  }
  let analysis = await axios.post(fullUrl, payload, {withCredentials: true}).then(response => {
    return ParsingUtilities.parseAnalysis(response.data);
  });
  return analysis;
}