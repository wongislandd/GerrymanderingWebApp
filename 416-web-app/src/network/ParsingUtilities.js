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
