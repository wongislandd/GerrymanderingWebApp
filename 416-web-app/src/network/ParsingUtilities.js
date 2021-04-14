import Job from '../utilities/classes/models/Job'
export function parseStateCountyDict(stateCountyJSON) {
    console.log(stateCountyJSON.TX)
}

export function parseJobJSONToObjects(jobsJSON) {    
    var jobs = []
    for (var job of jobsJSON) {
        jobs.push(new Job(job.summary.id, job.summary.description, job.summary.size, job.summary.params))
    }
    console.log(jobs)
    return jobs
}
