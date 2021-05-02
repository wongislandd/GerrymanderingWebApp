import state from "../redux/store"; 
import * as ParsingUtilities from './ParsingUtilities'
import * as ViewportUtilities from '../utilities/ViewportUtilities'

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
  // const params = {
  //   compactness : new Compactness(state.ConstraintSliderSettings[2].value, state.ConstraintSliderSettings[3].value, state.ConstraintSliderSettings[4].value)
  // }
  // let fullUrl = getFullRequestURLWithParams("/districtings/constrain", params)
  // const response = await fetch(fullUrl)
  // let body = await response.json();
  // console.log(body)
  // return body
}