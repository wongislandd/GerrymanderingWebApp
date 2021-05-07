import Incumbent from "../utilities/classes/models/Incumbent";
import Job from "../utilities/classes/models/Job";
export function parseStateCountyDict(stateCountyJSON) {
  console.log(stateCountyJSON.TX);
}

export function parseJobJSONToObjects(jobsJSON) {
  let jobs = [];
  for (let job of jobsJSON) {
    jobs.push(
      new Job(
        job.id,
        job.summary.description,
        job.summary.size,
        job.summary.params
      )
    );
  }
  return jobs;
}



export function parseAnalysis(analysisJSON) {
  let newAnalysis = {
    TopScoring: analysisJSON.topOFScoring,
    HighScoringSimilarEnacted: analysisJSON.closeToEnacted,
    HighScoringMajorityMinority: analysisJSON.highScoringWithMajorityMinority,
    TopAreaPairDeviation: analysisJSON.topAreaPairDeviation,
  };
  return newAnalysis;
}


export function parseBoxAndWhisker(boxAndWhiskerJSON) {
  // TODO See how Jackson serializes, parse the data here
  const boxes = [];
  for (let i = 0; i < 13; i++) {
    let traceValues = [];
    for (let j = 0; j < 50; j++) {
      traceValues[j] =
        (Math.random() * (i + 1)) /
        13;
    }
    boxes[i] = {
      y: traceValues,
      type: "box",
      name: "District " + (i + 1),
      fillcolor: "white",
      color: "white",
      marker: { color: "black" },
    };
  }
  return boxes;
}

export function parsePoints(pointsJSON) {
  // TODO See how Jackson serializes, parse the data here
  const points = [];
  for (let i = 0; i < 13; i++) {
    // Have the same name to match the marker plot on top of the box plot
    points.push({
      x: ["District " + (i + 1)],
      y: [
        (Math.random() * (i + 1)) /
          13,
      ],
      marker: {
        size: 5,
        color: "red",
      },
    });
  }
  return points;
}