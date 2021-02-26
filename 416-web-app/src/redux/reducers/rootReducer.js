import * as ActionTypes from '../actions/ActionTypes'
import Districting from '../../utilities/Districting'

/* Initial State */
const initState = {
    DisplayPrecincts : true,
    DisplayDistricts : true,
    CurrentDistricting : new Districting("Enacted Districting", "NC/EnactedDistrictingPlan"),


    /* History */
    DistrictingHistory : [
    new Districting("Enacted Districting", "NC/EnactedDistrictingPlan"),
    new Districting("New Districting 1", "NC/A"), 
    new Districting("New Districting 2", "NC/B")],
    /* The tentative districting is for when a user 
    selected a districting in the history tab but 
    hasn't yet loaded it*/
    TentativeDistricting : null,
}


/* Action Dispatcher
Add action type to ./ActionTypes.js and then make use of it here as well as in the action.
*/
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
        case ActionTypes.SET_TENTATIVE_DISTRICTING:
            return {
                ...state,
                TentativeDistricting : action.TentativeDistricting
            }
        case ActionTypes.SET_CURRENT_DISTRICTING:
            return {
                ...state,
                CurrentDistricting : action.CurrentDistricting
            }
        default:
            return state;
    }
}

export default rootReducer