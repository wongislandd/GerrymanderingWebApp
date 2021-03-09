import * as ActionTypes from '../actions/ActionTypes'
import Districting from '../../utilities/classes/Districting'
import * as ToolbarUtilities from '../../utilities/ToolbarUtilities'
import * as MapUtilities from '../../utilities/MapUtilities'
import EnactedDistrictingPlan2016 from '../../data/NC/EnactedDistrictingPlan2016WithData.json'
import React from 'react'
import Filter from '../../utilities/classes/Filter'
import * as ViewportUtilities from '../../utilities/ViewportUtilities'

const defaultDistricting = new Districting("Enacted Districting Feb 2016 - Nov 2019", EnactedDistrictingPlan2016)

/* Initial State */
const initState = {
    DisplayPrecincts : false,
    DisplayCounties : false,
    DisplayDistricts : true,
    CurrentDistricting : defaultDistricting,
    FeaturedDistrict : null,
    FeaturedPrecinct : null,


    FeaturesToHighlight : [],
    FeaturesToUnhighlight : [],

    CurrentState : ViewportUtilities.STATE_OPTIONS.UNSELECTED,

    /* Map Reference */
    MapRef : React.createRef(),

    /* Start off the map as the U.S. map (unselected) */
    MapViewport : ViewportUtilities.UNSELECTED.Maximized,

    /* Determines where the user starts, if this is false we need a districting to display by default as well */
    InSelectionMenu : false,

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
        new Filter("Maximum Population Difference (%)", 20, 0, 100, 1)
    ],

    PopulationConstraintInfo : {
        "Total Population" : false,
        "Voting Age Population" : false,
        "Citizen Voting Age Population" : false
    },
    
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
    
    MinimizedMap : false,

    NewDistrictingSelected : false,


    /* History */
    FilteredDistrictings : [],
    
    TentativeState : ViewportUtilities.STATE_OPTIONS.UNSELECTED,


    CurrentTab : ToolbarUtilities.MODES.SETTINGS,
    StatShowcasedDistrictID : null,



    ComparisonDistrictingA : null,
    ComparisonDistrictingB : null,
}


/* Action Dispatcher
Add action type to ./ActionTypes.js and then make use of it here as well as in the action.
*/
const rootReducer = (state = initState, action) => {
   //console.log(action)
    switch (action.type) {
        case ActionTypes.SET_CURRENT_STATE:
            var newViewport = null;
            switch (action.State) {
                case ViewportUtilities.STATE_OPTIONS.NORTH_CAROLINA:
                    newViewport = ViewportUtilities.NORTH_CAROLINA.Maximized
                    break
                case ViewportUtilities.STATE_OPTIONS.TEXAS:
                    newViewport = ViewportUtilities.TEXAS.Maximized
                    break
                case ViewportUtilities.STATE_OPTIONS.CALIFORNIA:
                    newViewport = ViewportUtilities.CALIFORNIA.Maximized
                    break
                default:
                    newViewport = ViewportUtilities.UNSELECTED.Maximized
            }
            return {
                ...state,
                CurrentState : action.State,
                MapViewport : newViewport
            }
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
        case ActionTypes.UPDATE_POPULATION_CONSTRAINT:
            /* Set all the keys to false and then set the one you want to true. */
            var updatedInfo = {}
            Object.keys(state.PopulationConstraintInfo).forEach(key => updatedInfo[key] = false)
            updatedInfo[action.Key] = true
            return {
                ...state,
                PopulationConstraintInfo : updatedInfo
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
        case ActionTypes.SET_CURRENT_TAB:
            return {
                ...state,
                CurrentTab : action.Tab
            }
        case ActionTypes.SET_VIEWPORT:
            return {
                ...state,
                MapViewport : action.Viewport
            }
        case ActionTypes.MAXIMIZE_MAP:
            var newViewport = null;
            switch (state.CurrentState) {
                case ViewportUtilities.STATE_OPTIONS.NORTH_CAROLINA:
                    newViewport = ViewportUtilities.NORTH_CAROLINA.Maximized
                    break
                case ViewportUtilities.STATE_OPTIONS.TEXAS:
                    newViewport = ViewportUtilities.TEXAS.Maximized
                    break
                case ViewportUtilities.STATE_OPTIONS.CALIFORNIA:
                    newViewport = ViewportUtilities.CALIFORNIA.Maximized
                    break
                default:
                    newViewport = ViewportUtilities.UNSELECTED.Maximized
            }
            return {
                ...state,
                MapViewport : newViewport,
                MinimizedMap : false
            }
        case ActionTypes.MINIMIZE_MAP:
            var newViewport = null;
            switch (state.CurrentState) {
                case ViewportUtilities.STATE_OPTIONS.NORTH_CAROLINA:
                    newViewport = ViewportUtilities.NORTH_CAROLINA.Minimized
                    break
                case ViewportUtilities.STATE_OPTIONS.TEXAS:
                    newViewport = ViewportUtilities.TEXAS.Minimized
                    break
                case ViewportUtilities.STATE_OPTIONS.CALIFORNIA:
                    newViewport = ViewportUtilities.CALIFORNIA.Minimized
                    break
                default:
                    newViewport = ViewportUtilities.UNSELECTED.Minimized
            }
            return {
                ...state,
                MapViewport : newViewport,
                MinimizedMap : true
            }
        case ActionTypes.SET_STAT_SHOWCASED_DISTRICT_ID:
            return {
                ...state,
                StatShowcasedDistrictID : action.DistrictID,
                CurrentTab : ToolbarUtilities.MODES.STATS
            }
        /* When a new districting is selected, much of the state should return to default
        to avoid any overlap*/
        case ActionTypes.RESTORE_DEFAULT_STATE_FOR_NEW_DISTRICTING:
            return {
                ...state,
                CurrentTab : ToolbarUtilities.MODES.SETTINGS,
                MinimizedMap : false,
                StatShowcasedDistrictID : null,
                FeaturesToHighlight : [],
                FeaturesToUnhighlight: [],
                DisplayPrecincts : false,
                DisplayCounties : false,
                DisplayDistricts : true,
                FeaturedDistrict : null,
                FeaturedPrecinct : null,
            }
        case ActionTypes.SET_NEW_DISTRICTING_SELECTED:
            return {
                ...state,
                NewDistrictingSelected : action.Bool
            }
        case ActionTypes.SET_TENTATIVE_STATE:
            return {
                ...state,
                TentativeState : action.State
            }
        default:
            return state;
    }
}

export default rootReducer