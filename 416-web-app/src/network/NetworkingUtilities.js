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



// Loads in a set of districtings based on the constraints set by the user.
export async function loadDistricting(id) {
    console.log("Load Districting called.")
    const state = store.getState();
    console.log(state)
    // The constraints, formatted into an array with only values
    const input = {
        maxPopulationDifference : state.ConstraintSliderSettings[0].value,
        minMajorityMinorityDistricts: state.ConstraintSliderSettings[1].value,
        compactnessPP: state.ConstraintSliderSettings[2].value,
        compactnessGC: state.ConstraintSliderSettings[3].value,
        compactnessPF: state.ConstraintSliderSettings[4].value
      };

      let payload = JSON.stringify(input)

      // Construct HTTP request; change the URL later to something more fitting..
      const response = await fetch(baseURL + "/districting/load", buildRequest(HTTPMETHODS.POST, payload));

      let body = await response.json();
      console.log(body)
      return body;
}