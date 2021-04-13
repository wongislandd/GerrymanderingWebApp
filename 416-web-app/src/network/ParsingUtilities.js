import Job from '../utilities/classes/models/Job'
export function parseStateCountyDict(stateCountyJSON) {
    console.log(stateCountyJSON.TX)
}

export function parseJobJSONToObjects(jobsJSON) {
    var fakeData = [new Job(1, "First job", 100000, {'Weight 1' : 1, 'Weight 2': 2, 'Weight 3' : 3})]
    return fakeData
}
