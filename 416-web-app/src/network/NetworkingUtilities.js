import store from '../redux/store'

const baseURL = "http://localhost:8080";

export const HTTPMETHODS = {
  GET : "GET",
  POST : "POST"
}


function getFullRequestURL(endpoint, params) {
  const URLParams = new URLSearchParams(params)
  return baseURL + endpoint + "?" + URLParams.toString()
}


/* Load an individual districting based on ID */
export async function loadDistricting(id) {
    // The constraints, formatted into an array with only values, to become the JSON payload
    const params = {
        id : id
    };

    let fullUrl = getFullRequestURL("/districting/load", params)
    console.log(fullUrl)
    const response = await fetch(fullUrl)
    let body = await response.json();
    return body;
}

