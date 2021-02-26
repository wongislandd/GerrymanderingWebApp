import * as ActionTypes from '../actions/ActionTypes'
import Districting from '../../utilities/Districting'
import React from 'react'
import EnactedDistrictingPlan2011 from '../../data/NC/EnactedDistrictingPlan2011.json'
import EnactedDistrictingPlan2016 from '../../data/NC/EnactedDistrictingPlan2016.json'
import EnactedDistrictingPlan2019 from '../../data/NC/EnactedDistrictingPlan2019.json'

/* Eventually I think it'll be best to load all these JSON files in through network instead of local storage. */
const placeholderHistory = [
        new Districting("Enacted Districting Nov 2011 - Feb 2016", EnactedDistrictingPlan2011),
        new Districting("Enacted Districting Feb 2016 - Nov 2019", EnactedDistrictingPlan2016), 
        new Districting("Enacted Districting Nov 2019 - Dec 2021", EnactedDistrictingPlan2019)]

/* Initial State */
const initState = {
    DisplayPrecincts : false,
    DisplayDistricts : true,
    CurrentDistricting : placeholderHistory[2],
    FeaturedDistrict : null,
    FeaturedPrecinct : null,
    /* Map Reference */
    MapRef : React.createRef(),

    /* Usable Map */
    Map : null,

    /* Mouse tracking for feature identification */
    MouseX : 0,
    MouseY : 0,
    MouseEntered : false,
    


    /* History */
    DistrictingHistory : placeholderHistory
    ,
    /* The tentative districting is for when a user 
    selected a districting in the history tab but 
    hasn't yet loaded it*/
    TentativeDistricting : null,
}


/* Action Dispatcher
Add action type to ./ActionTypes.js and then make use of it here as well as in the action.
*/
const rootReducer = (state = initState, action) => {
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
                CurrentDistricting : action.CurrentDistricting,
                TentativeDistricting : null
            }
        case ActionTypes.MOVE_MOUSE:
            return {
                ...state,
                MouseX : action.MouseX,
                MouseY : action.MouseY
            }
        case ActionTypes.SET_MOUSE_ENTERED:
            return {
                ...state,
                MouseEntered : action.MouseEntered
            }
        case ActionTypes.SET_FEATURED_DISTRICT:
            return {
                ...state,
                FeaturedDistrict : action.FeaturedDistrict
            }
        case ActionTypes.SET_FEATURED_PRECINCT:
            return {
                ...state,
                FeaturedPrecinct : action.FeaturedPrecinct
            }
        default:
            return state;
    }
}

export default rootReducer