const baseURL = "http://localhost:8080";

// Loads in a set of districtings based on the constraints set by the user.
export async function loadDistrictings(state) {
    // The constraints, formatted into an array with only values
    const input = {
        maxPopulationDifference: state.ConstraintSliderSettings[0].value,
        minMajorityMinorityDistricts: state.ConstraintSliderSettings[1].value,
        compactnessPP: state.ConstraintSliderSettings[2].value,
        compactnessGC: state.ConstraintSliderSettings[3].value,
        compactnessPF: state.ConstraintSliderSettings[4].value
      };

      console.log(input);

      // Construct HTTP request; change the URL later to something more fitting..
      const response = await fetch(baseURL + "/districting/load", {
        method: "POST",
        mode: "no-cors", // Web security policies..
        cache: "no-cache",
        credentials: "same-origin", // .. If they cause issues, try commenting out
        headers: {
          //"Content-Type": "application/json" // For JSON
          "Content-Type": "text/plain" // For plaintext
        },
        redirect: "follow",
        referrerPolicy: "no-referrer",
        // This is the payload!
        body: "Hello again!" // for passing JSON objects
      });

      let body = await response.json();
      console.log(body.id);
      //setMessage(body.id ? "Districting successfully loaded!" : "Failed to load districting.")
}