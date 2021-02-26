import * as ActionTypes from './ActionTypes'

export const togglePrecinctSwitch = (bool) => {
    return {
        type: ActionTypes.TOGGLE_PRECINCT_SWITCH, 
        DisplayPrecincts : bool
    }
}

export const toggleDistrictSwitch = (bool) => {
    return {
        type: ActionTypes.TOGGLE_DISTRICT_SWITCH, 
        DisplayDistricts : bool
    }
}