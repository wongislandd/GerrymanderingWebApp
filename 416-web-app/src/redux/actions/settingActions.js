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

export const setTentativeDistricting = (districting) => {
    return {
        type: ActionTypes.SET_TENTATIVE_DISTRICTING,
        TentativeDistricting : {
            name : districting.name,
            geoJson : districting.geoJson
        }
    }
}

export const setCurrentDistricting = (districting) => {
    return {
        type: ActionTypes.SET_CURRENT_DISTRICTING,
        CurrentDistricting : districting
    }
}