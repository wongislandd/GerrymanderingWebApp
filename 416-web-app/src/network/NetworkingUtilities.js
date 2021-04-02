import store from '../redux/store'

const baseURL = "http://localhost:8090";

export const HTTPMETHODS = {
  GET : "GET",
  POST : "POST"
}

const buildRequest = (method, body) => {
  return(
    {
      method: method,
      mode: "cors", // Web security policies..
      cache: "no-cache",
      credentials: "same-origin", // .. If they cause issues, try commenting out
      headers: {
        "Content-Type": "application/json" // For JSON
      },
      redirect: "follow",
      referrerPolicy: "no-referrer",
      // This is the payload!
      body: body // for passing JSON objects
  })
}



/* Load an individual districting based on ID */
export async function loadDistricting(id) {
    // The constraints, formatted into an array with only values, to become the JSON payload
    const input = {
        id : id
    };

    let payload = JSON.stringify(input)

    // Construct HTTP request; change the URL later to something more fitting..
    const response = await fetch(baseURL + "/districting/load", buildRequest(HTTPMETHODS.POST, payload));

    let body = await response.json();
    return body;
}
