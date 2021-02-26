import * as ActionTypes from './ActionTypes'
/* Initial State */
const initState = {
    DisplayPrecincts : true,
    DisplayDistricts : true
}

const rootReducer = (state = initState, action) => {
    console.log(action)
    switch (action.type) {
        case ActionTypes.TOGGLE_PRECINCT_SWITCH:
            return {
                ...state,
                DisplayPrecincts : action.DisplayPrecincts
            }
        case ActionTypes.TOGGLE_DISTRICT_SWITCH:
            return {
                ...state,
                DisplayDistricts : action.DisplayDistricts
            }
        default:
            return state;
    }
}

export default rootReducer