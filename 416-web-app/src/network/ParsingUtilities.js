import Incumbent from "../utilities/classes/models/Incumbent";
import Job from "../utilities/classes/models/Job";

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



// export function parseAnalysis(analysisJSON) {
//   let newAnalysis = {
//     TopScoring: analysisJSON.topOFScoring,
//     HighScoringSimilarEnacted: analysisJSON.closeToEnacted,
//     HighScoringMajorityMinority: analysisJSON.highScoringWithMajorityMinority,
//     TopAreaPairDeviation: analysisJSON.topAreaPairDeviation,
//   };
//   return newAnalysis;
// }

const minorityNumberedDistrictNames = [
  "1st District",
  "2nd District",
  "3rd District",
  "4th District",
  "5th District",
  "6th District",
  "7th District",
  "8th District",
  "9th District",
  "10th District",
  "11th District",
  "12th District",
  "13th District"
]

export function parseBoxAndWhisker(boxAndWhiskerData) {
  // TODO See how Jackson serializes, parse the data here
  const boxes = [];
  for (let i = 0; i < boxAndWhiskerData.length; i++) {
    let traceValues = [];
    for (let j = 0; j < boxAndWhiskerData[i].length; j++) {
      traceValues[j] = boxAndWhiskerData[i][j];
    }
    boxes[i] = {
      y: traceValues,
      type: "box",
      name: minorityNumberedDistrictNames[i],
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
  for (let i = 0; i < pointsJSON.length; i++) {
    // Have the same name to match the marker plot on top of the box plot
    points.push({
      x: [minorityNumberedDistrictNames[i]],
      y: [
        pointsJSON[i],
      ],
      marker: {
        size: 5,
        color: "red",
      },
    });
  }
  return points;
}