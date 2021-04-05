import state from '../redux/store'

const baseURL = "http://localhost:8080";

export const HTTPMETHODS = {
  GET : "GET",
  POST : "POST"
}


function getFullRequestURLWithParams(endpoint, params) {
  const URLParams = new URLSearchParams(params)
  return baseURL + endpoint + "?" + URLParams.toString()
}


/* Load an individual districting based on ID */
export async function loadDistricting(id) {
    // The constraints, formatted into an array with only values, to become the JSON payload
    const params = {
        id : id
    };
    let fullUrl = getFullRequestURLWithParams("/districting/load", params)
    const response = await fetch(fullUrl)
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
