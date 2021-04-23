import Incumbent from '../utilities/classes/models/Incumbent'
import Job from '../utilities/classes/models/Job'
export function parseStateCountyDict(stateCountyJSON) {
    console.log(stateCountyJSON.TX)
}

export function parseJobJSONToObjects(jobsJSON) {    
    var jobs = []
    for (var job of jobsJSON) {
        jobs.push(new Job(job.id, job.summary.description, job.summary.size, job.summary.params))
    }
    return jobs
}

export function parseIncumbentsJSONToObjects(incumbentsJSON) {
    var incumbents = []
    for (var incumbent of incumbentsJSON) {
        incumbents.push(new Incumbent(incumbent.name, incumbent.residence, incumbent.id))
    }
    return incumbents;
}
