import state from "../redux/store"; 
import * as ParsingUtilities from './ParsingUtilities'

const baseURL = "http://localhost:8080";

export const HTTPMETHODS = {
  GET: "GET",
  POST: "POST",
};

function getFullRequestURLWithParams(endpoint, params) {
  const URLParams = new URLSearchParams(params);
  return baseURL + endpoint + "?" + URLParams.toString();
}

/* Load an individual districting based on ID */
export async function loadDistricting(id) {
  // The constraints, formatted into an array with only values, to become the JSON payload
  const params = {
    id: id,
  };
  let fullUrl = getFullRequestURLWithParams("/districting/load", params);
  const response = await fetch(fullUrl); 
  let body = await response.json();
  return body;
}


const stateFullToId = {
  "TEXAS" : "TX",
  "NORTH_CAROLINA" : "NC",
  "LOUISIANA" : "LA",
  // Default to NC
  "UNSELECTED" : "NC",
}

export async function loadStateOutlines() {
  let fullUrl = baseURL + "/states/getOutlines";
  const response = await fetch(fullUrl);
  let body = await response.json();

  /* Keys must line up with Viewport Utilities State Options*/
  // Parse results into dict which will be loaded into state
  let result = {
    "NORTH_CAROLINA" : JSON.parse(body.NC),
    "LOUISIANA" : JSON.parse(body.LA),
    "TEXAS" : JSON.parse(body.TX),
  }
  return result;
}


export async function getJobs(state) {
  let fullUrl = baseURL + "/states/" + stateFullToId[state] + "/loadJobs";
  const response = await fetch(fullUrl);
  let body = await response.json();
  console.log(body)
  var jobs = ParsingUtilities.parseJobJSONToObjects(body)
  return jobs;
}

export async function loadIncumbents(state) {
  let fullUrl = baseURL + "/states/" + stateFullToId[state] + "/loadIncumbents";
  const response = await fetch(fullUrl);
  let body = await response.json();
  let incumbents = ParsingUtilities.parseIncumbentsJSONToObjects(body);
  return incumbents;
}


export async function loadPrecincts(state) {
    console.log("LOAD PRECINCTS CALLED");
    let fullUrl = baseURL + "/states/" + state + "/loadPrecincts";
    const response = await fetch(fullUrl);
    let body = await response.json();
    return body;
}

export async function loadCounties(state) {
  console.log("LOAD COUNTIES CALLED");
  let fullUrl = baseURL + "/states/" + state + "/loadCounties";
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