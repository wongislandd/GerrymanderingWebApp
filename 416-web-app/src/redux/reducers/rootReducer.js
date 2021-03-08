import * as ActionTypes from '../actions/ActionTypes'
import Districting from '../../utilities/classes/Districting'
import React from 'react'
import Filter from '../../utilities/classes/Filter'



/* Initial State */
const initState = {
    DisplayPrecincts : false,
    DisplayCounties : false,
    DisplayDistricts : true,
    CurrentDistricting : null,
    FeaturedDistrict : null,
    FeaturedPrecinct : null,


    FeaturesToHighlight : [],
    FeaturesToUnhighlight : [],

    /* Map Reference */
    MapRef : React.createRef(),

    InSelectionMenu : true,

    SortedBy : {
        value : "Stat 1",
        descending : true
    },


    /* Objective Function Weights */
    ObjectiveFunctionSettings : [
        new Filter("Population Equality", .5, 0, 1, .05),
        new Filter("Split Counties", .5, 0, 1, .05),
        new Filter("Deviation from Average Districting", .5, 0, 1, .05),
        new Filter("Deviation from Enacted Plan (Area and Population)", .5, 0, 1, .05),
        new Filter("Compactness (Polsby-Popper)", .5, 0, 1, .05),
    ],

    /* Constraint Settings */
    ConstraintSettings : [
        new Filter("Minimum Majority-Minority Districts", 5, 0, 10, 1),
    ],

    
    /* Gonna need like a function run early on to populate these names based on the
    provided information for the state
    All incumbents start off as false (not protected) */
    IncumbentProtectionInfo : {
                    "Stella Pang" : false,
                    "Jihu Mun" : false,
                    "Jim Hyunh" : false,
                    "Christopher Wong" : true},

    /* Usable Map */
    Map : null,
    Loaded : false,

    /* Mouse tracking for feature identification */
    MouseX : 0,
    MouseY : 0,
    MouseEntered : false,
    


    /* History */
    FilteredDistrictings : [],
    
    /* The tentative districting is for when a user 
    selected a districting in the history tab but 
    hasn't yet loaded it*/
    TentativeDistricting : null,


    ComparisonDistrictingA : null,
    ComparisonDistrictingB : null,
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
        case ActionTypes.TOGGLE_COUNTY_SWITCH:
            return {
                ...state,
                DisplayCounties : action.DisplayCounties
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
        case ActionTypes.SET_LOADED_STATUS:
            return {
                ...state,
                Loaded : action.Loaded
            }
        case ActionTypes.UPDATE_OBJECTIVE_FUNCTION_SETTINGS:
            var newSettings = [...state.ObjectiveFunctionSettings]
            newSettings[action.Key].value = action.NewValue
            return {
                ...state,
                ObjectiveFunctionSettings : newSettings
            }
        case ActionTypes.UPDATE_CONSTRAINT_SETTINGS:
            var newSettings = [...state.ConstraintSettings]
            newSettings[action.Key].value = action.NewValue
            return {
                ...state,
                ConstraintSettings : newSettings
            }
        case ActionTypes.UPDATE_INCUMBENT_PROTECTION:
            return {
                ...state,
                IncumbentProtectionInfo: {
                    ...state.IncumbentProtectionInfo, [action.Key] : action.NewValue
                }
            }
        case ActionTypes.SET_IN_SELECTION_MENU:
            return {
                ...state,
                InSelectionMenu : action.In
            }
        case ActionTypes.CHANGE_VALUE_SORTED_BY:
            return {
                ...state,
                SortedBy : {
                    value : action.Value,
                    descending : action.Descending
                }
            }
        case ActionTypes.CHANGE_COMPARISON_DISTRICTING_A:
            return {
                ...state,
                ComparisonDistrictA : action.Districting
            }
        case ActionTypes.CHANGE_COMPARISON_DISTRICTING_B:
            return {
                ...state,
                ComparisonDistrictB : action.Districting
            }
        case ActionTypes.ADD_FEATURE_TO_HIGHLIGHT:
            return {
                ...state,
                FeaturesToHighlight : [...state.FeaturesToHighlight, action.Feature]
            }
        case ActionTypes.REMOVE_FEATURE_HIGHLIGHTING:
            const NewFeaturesToHighlight = state.FeaturesToHighlight.filter(feature => feature.id != action.Feature.id);
            return {
                ...state,
                FeaturesToHighlight : NewFeaturesToHighlight,
                FeaturesToUnhighlight : [...state.FeaturesToUnhighlight, action.Feature]
            }
        case ActionTypes.RESET_ALL_HIGHLIGHTING:
            return {
                ...state,
                FeaturesToUnhighlight : state.FeaturesToHighlight,
                FeaturesToHighlight : []
            }
        case ActionTypes.CLEAR_FEATURES_TO_UNHIGHLIGHT:
            return {
                ...state,
                FeaturesToUnhighlight : []
            }
        case ActionTypes.LOAD_IN_DISTRICTINGS:
            return {
                ...state,
                FilteredDistrictings : action.Districtings
            }
        default:
            return state;
    }
}

export default rootReducer