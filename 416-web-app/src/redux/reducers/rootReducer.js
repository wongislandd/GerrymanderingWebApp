import * as ActionTypes from "../actions/ActionTypes";
import * as ToolbarUtilities from "../../utilities/ToolbarUtilities";
import * as MapUtilities from "../../utilities/MapUtilities";
import TestGeneratedPlan from "../../data/NC/testGeneratedPlan.json";
import EnactedDistrictingPlan2016 from '../../data/NC/EnactedDistrictingPlan2016WithData.json'
import TestData from "../../data/NC/TestData3.json"
import React from "react";
import Filter from "../../utilities/classes/Filter";
import Job from "../../utilities/classes/models/Job";
import * as ViewportUtilities from "../../utilities/ViewportUtilities";
import * as SelectionMenuUtilities from "../../utilities/SelectionMenuUtilities";
import * as NetworkingUtilities from "../../network/NetworkingUtilities";


/* Initial State */
const initState = {

  DisplayPrecincts: false,
  DisplayCounties: false,
  DisplayDistricts: true,
  CurrentDistricting: null,
  FeaturedDistrict: null,
  FeaturedPrecinct: null,

  FeaturesToHighlight: [],
  FeaturesToUnhighlight: [],

  /* Default to UNSELECTED */
  CurrentState: ViewportUtilities.STATE_OPTIONS.UNSELECTED,

  /* Default to null*/
  CurrentJob : null,

  /* Map Reference */
  MapRef: React.createRef(),

  /* Start off the map as the U.S. map (unselected) */
  MapViewport: ViewportUtilities.UNSELECTED.Maximized,

  /* Determines where the user starts, if this is false we need a districting to display by default as well */
  /* Default to False*/
  InSelectionMenu: false,

  /* Objective Function Weights */
  ObjectiveFunctionSettings: [
    new Filter("Population Equality", 0.5, 0, 1, 0.05),
    new Filter("Split Counties", 0.5, 0, 1, 0.05),
    new Filter("Deviation from Average Districting", 0.5, 0, 1, 0.05),
    new Filter(
      "Deviation from Enacted Plan (Area and Population)",
      0.5,
      0,
      1,
      0.05,
      true
    ),
    new Filter("Compactness", 0.5, 0, 1, 0.05, true),
  ],

  /* Constraint Settings */
  ConstraintSliderSettings: {
    [SelectionMenuUtilities.CONSTRAINT_KEYS.PopulationDifference] : new Filter("Maximum Population Difference (%)", 20, 0, 100, 1, true),
    [SelectionMenuUtilities.CONSTRAINT_KEYS.MajorityMinorityDistricts] : new Filter("Minimum Majority-Minority Districts", 5, 0, 10, 1, true),
    [SelectionMenuUtilities.CONSTRAINT_KEYS.MinorityThreshold] : new Filter("Minority Threshold", 0.5, 0, 1, 0.05, true),
    [SelectionMenuUtilities.CONSTRAINT_KEYS.Compactness] : new Filter("Compactness", 0.15, 0, .5, 0.01, true),
  },




  PopulationSelection: null,

  MinoritySelection: null,

  CompactnessSelection : null,



  /* Gonna need like a function run early on to populate these names based on the
    provided information for the state
    All incumbents start off as false (not protected) */
  IncumbentProtectionInfo: [],


  Jobs : [],

  PrecinctsGeoJson : null,
  CountiesGeoJson : null,


  /* Usable Map */
  Map: null,
  Loaded: false,

  /* Mouse tracking for feature identification */
  MouseX: 0,
  MouseY: 0,
  MouseEntered: false,

  MinimizedMap: false,

  NewDistrictingSelected: false,

  TentativeState: ViewportUtilities.STATE_OPTIONS.UNSELECTED,

  ShowFullListing: true,

  CurrentTab: ToolbarUtilities.MODES.SETTINGS,
  StatShowcasedDistrictID: null,

  ComparisonDistrictingA: null,
  ComparisonDistrictingB: null,

  ExpandedSummaries: [],

  /* State variables to be filled in by network communication*/

  /* Starts at -1, should be populated when a job is loaded to be the full size of the job */
  NumDistrictingsAvailable: -1,

  DistrictingsAreConstrained: false,

  ConstrainedDistrictings : [],

  /* Keys must match ANALYSIS_CATEGORIES in SelectionMenuUtilities*/
  AnalysisDistrictings: {
    TopScoring: [],
    HighScoringSimilarEnacted: [],
    HighScoringMajorityMinority: [],
    TopAreaPairDeviation: [],
  },

  /* STATE VARIABLES TO BE LOADED IN THROUGH THE NETWORK? IS THIS THE BEST APPROACH? */

  StateCounties: null,
};

const ACTIONS_TO_IGNORE_FOR_LOGGING = [
  ActionTypes.MOVE_MOUSE,
  ActionTypes.SET_VIEWPORT,
  ActionTypes.RESET_ALL_HIGHLIGHTING,
  ActionTypes.ADD_FEATURE_TO_HIGHLIGHT,
  ActionTypes.SET_FEATURED_DISTRICT,
  ActionTypes.SET_FEATURED_PRECINCT,
  ActionTypes.UPDATE_CONSTRAINT_SLIDER_SETTINGS,
];

/* Action Dispatcher
Add action type to ./ActionTypes.js and then make use of it here as well as in the action.
*/
const rootReducer = (state = initState, action) => {
  if (!ACTIONS_TO_IGNORE_FOR_LOGGING.includes(action.type)) {
    console.log(action);
  }
  switch (action.type) {
    case ActionTypes.SET_CURRENT_STATE:
      var newViewport = null;
      switch (action.State) {
        case ViewportUtilities.STATE_OPTIONS.NORTH_CAROLINA:
          newViewport = ViewportUtilities.NORTH_CAROLINA.Maximized;
          break;
        case ViewportUtilities.STATE_OPTIONS.TEXAS:
          newViewport = ViewportUtilities.TEXAS.Maximized;
          break;
        case ViewportUtilities.STATE_OPTIONS.LOUISIANA:
          newViewport = ViewportUtilities.LOUISIANA.Maximized;
          break;
        default:
          newViewport = ViewportUtilities.UNSELECTED.Maximized;
      }
      return {
        ...state,
        CurrentState: action.State,
        MapViewport: newViewport,
      };
    case ActionTypes.TOGGLE_PRECINCT_SWITCH:
      return {
        ...state,
        DisplayPrecincts: action.DisplayPrecincts,
      };
    case ActionTypes.TOGGLE_DISTRICT_SWITCH:
      return {
        ...state,
        DisplayDistricts: action.DisplayDistricts,
      };
    case ActionTypes.TOGGLE_COUNTY_SWITCH:
      return {
        ...state,
        DisplayCounties: action.DisplayCounties,
      };
    case ActionTypes.SET_TENTATIVE_DISTRICTING:
      return {
        ...state,
        TentativeDistricting: action.TentativeDistricting,
      };
    case ActionTypes.MOVE_MOUSE:
      return {
        ...state,
        MouseX: action.MouseX,
        MouseY: action.MouseY,
      };
    case ActionTypes.SET_MOUSE_ENTERED:
      return {
        ...state,
        MouseEntered: action.MouseEntered,
      };
    case ActionTypes.SET_FEATURED_DISTRICT:
      return {
        ...state,
        FeaturedDistrict: action.FeaturedDistrict,
      };
    case ActionTypes.SET_FEATURED_PRECINCT:
      return {
        ...state,
        FeaturedPrecinct: action.FeaturedPrecinct,
      };
    case ActionTypes.SET_LOADED_STATUS:
      return {
        ...state,
        Loaded: action.Loaded,
      };
    case ActionTypes.UPDATE_OBJECTIVE_FUNCTION_SETTINGS:
      let newSettings = [...state.ObjectiveFunctionSettings];
      newSettings[action.Key].value = action.NewValue;
      return {
        ...state,
        ObjectiveFunctionSettings: newSettings,
      };
    case ActionTypes.UPDATE_CONSTRAINT_SLIDER_SETTINGS:
      var newFilter = state.ConstraintSliderSettings[action.Key]
      newFilter.value = action.NewValue
      return {
        ...state,
        ConstraintSliderSettings: {
          ...state.ConstraintSliderSettings,
           [action.Key] : newFilter
        }
      };
    case ActionTypes.SET_ENABLED_STATE_OF_CONSTRAINT:
      var newFilter = state.ConstraintSliderSettings[action.Key];
      newFilter.enabled = action.Bool;
      return {
        ...state,
        ConstraintSliderSettings: {
          ...state.ConstraintSliderSettings,
          [action.Key] : newFilter
        }
      };
    case ActionTypes.UPDATE_INCUMBENT_PROTECTION:
      let updatedIncumbents = [...state.IncumbentProtectionInfo];
      updatedIncumbents[action.Key].protected = action.NewValue;
      console.log(updatedIncumbents)
      return {
        ...state,
        IncumbentProtectionInfo : updatedIncumbents,
      };
    case ActionTypes.UPDATE_POPULATION_CONSTRAINT:
      return {
        ...state,
        PopulationSelection: action.Key,
      };
      case ActionTypes.UPDATE_COMPACTNESS_CONSTRAINT:
        return {
          ...state,
          CompactnessSelection: action.Key,
        };
    case ActionTypes.UPDATE_MINORITY_CONSTRAINT:
      return {
        ...state,
        MinoritySelection: action.Key,
      };
    case ActionTypes.SET_IN_SELECTION_MENU:
      return {
        ...state,
        InSelectionMenu: action.In,
      };
    case ActionTypes.CHANGE_VALUE_SORTED_BY:
      return {
        ...state,
        SortedBy: {
          value: action.Value,
          descending: action.Descending,
        },
      };
    case ActionTypes.SET_COMPARISON_DISTRICTING_A:
      console.log("UPDATING COMPARISON DISTRICT A");
      return {
        ...state,
        ComparisonDistrictingA: action.Districting,
      };
    case ActionTypes.SET_COMPARISON_DISTRICTING_B:
      console.log("UPDATING COMPARISON DISTRICT B");
      return {
        ...state,
        ComparisonDistrictingB: action.Districting,
      };
    case ActionTypes.ADD_FEATURE_TO_HIGHLIGHT:
      return {
        ...state,
        FeaturesToHighlight: [...state.FeaturesToHighlight, action.Feature],
      };
    case ActionTypes.REMOVE_FEATURE_HIGHLIGHTING:
      const NewFeaturesToHighlight = state.FeaturesToHighlight.filter(
        (feature) => feature.id != action.Feature.id
      );
      return {
        ...state,
        FeaturesToHighlight: NewFeaturesToHighlight,
        FeaturesToUnhighlight: [...state.FeaturesToUnhighlight, action.Feature],
      };
    case ActionTypes.RESET_ALL_HIGHLIGHTING:
      return {
        ...state,
        FeaturesToUnhighlight: state.FeaturesToHighlight,
        FeaturesToHighlight: [],
      };
    case ActionTypes.CLEAR_FEATURES_TO_UNHIGHLIGHT:
      return {
        ...state,
        FeaturesToUnhighlight: [],
      };
    case ActionTypes.LOAD_IN_DISTRICTINGS:
      return {
        ...state,
        ConstrainedDistrictings: [
          ...state.ConstrainedDistrictings,
          action.Districting,
        ],
      };
    case ActionTypes.SET_CURRENT_TAB:
      return {
        ...state,
        CurrentTab: action.Tab,
      };
    case ActionTypes.SET_VIEWPORT:
      return {
        ...state,
        MapViewport: action.Viewport,
      };
    case ActionTypes.MAXIMIZE_MAP:
      var newViewport = null;
      switch (state.CurrentState) {
        case ViewportUtilities.STATE_OPTIONS.NORTH_CAROLINA:
          newViewport = ViewportUtilities.NORTH_CAROLINA.Maximized;
          break;
        case ViewportUtilities.STATE_OPTIONS.TEXAS:
          newViewport = ViewportUtilities.TEXAS.Maximized;
          break;
        case ViewportUtilities.STATE_OPTIONS.LOUISIANA:
          newViewport = ViewportUtilities.LOUISIANA.Maximized;
          break;
        default:
          newViewport = ViewportUtilities.UNSELECTED.Maximized;
      }
      return {
        ...state,
        MapViewport: newViewport,
        MinimizedMap: false,
      };
    case ActionTypes.MINIMIZE_MAP:
      var newViewport = null;
      switch (state.CurrentState) {
        case ViewportUtilities.STATE_OPTIONS.NORTH_CAROLINA:
          newViewport = ViewportUtilities.NORTH_CAROLINA.Minimized;
          break;
        case ViewportUtilities.STATE_OPTIONS.TEXAS:
          newViewport = ViewportUtilities.TEXAS.Minimized;
          break;
        case ViewportUtilities.STATE_OPTIONS.LOUISIANA:
          newViewport = ViewportUtilities.LOUISIANA.Minimized;
          break;
        default:
          newViewport = ViewportUtilities.UNSELECTED.Minimized;
      }
      return {
        ...state,
        MapViewport: newViewport,
        MinimizedMap: true,
      };
    case ActionTypes.SET_STAT_SHOWCASED_DISTRICT_ID:
      return {
        ...state,
        StatShowcasedDistrictID: action.DistrictID,
        CurrentTab: ToolbarUtilities.MODES.STATS,
      };
    /* When a new districting is selected, much of the state should return to default
        to avoid any overlap*/
    case ActionTypes.RESTORE_DEFAULT_STATE_FOR_NEW_DISTRICTING:
      return {
        ...state,
        CurrentTab: ToolbarUtilities.MODES.SETTINGS,
        MinimizedMap: false,
        StatShowcasedDistrictID: null,
        FeaturesToHighlight: [],
        FeaturesToUnhighlight: [],
        DisplayPrecincts: false,
        DisplayCounties: false,
        DisplayDistricts: true,
        FeaturedDistrict: null,
        FeaturedPrecinct: null,
        CompareDistrict1: null,
        CompareDistrict2: null,
        ExpandedSummaries: [],
        DistrictingsAreConstrained : false,
      };
    case ActionTypes.SET_NEW_DISTRICTING_SELECTED:
      return {
        ...state,
        NewDistrictingSelected: action.Bool,
      };
    case ActionTypes.SET_TENTATIVE_STATE:
      return {
        ...state,
        TentativeState: action.State,
      };
    case ActionTypes.SET_NUMBER_OF_DISTRICTINGS_AVAILABLE:
      return {
        ...state,
        NumDistrictingsAvailable: action.Number,
      };
    case ActionTypes.UPDATE_ANALYSIS_DISTRICTINGS:
      return {
        ...state,
        AnalysisDistrictings: action.Dictionary,
      };
    case ActionTypes.LOAD_STATE_OUTLINES:
      return {
        ...state,
        StateCounties: action.Outlines,
      };
    case ActionTypes.POPULATE_INCUMBENTS:
      return {
        ...state,
        IncumbentProtectionInfo : action.Incumbents,
      }
    case ActionTypes.POPULATE_PRECINCTS:
      return {
        ...state,
        PrecinctsGeoJson : action.PrecinctsGeoJson,
      }
    case ActionTypes.POPULATE_COUNTIES:
      return {
        ...state,
        CountiesGeoJson : action.CountiesGeoJson,
      }
    case ActionTypes.POPULATE_CURRENT_DISTRICTING:
      return {
        ...state,
        CurrentDistricting : action.DistrictingGeoJson,
        TentativeDistricting: null,
      }
    case ActionTypes.TOGGLE_EXPANDED_SUMMARY:
      let newExpandedSet = [...state.ExpandedSummaries];
      if (!newExpandedSet.includes(action.Name)) {
        newExpandedSet.push(action.Name);
      } else {
        newExpandedSet = newExpandedSet.filter((name) => name != action.Name);
      }
      return {
        ...state,
        ExpandedSummaries: newExpandedSet,
      };
    case ActionTypes.RESET_EXPANDED_SUMMARIES:
      return {
        ...state,
        ExpandedSummaries: [],
      };
    case ActionTypes.SET_DISTRICTINGS_ARE_CONSTRAINED:
      return {
        ...state,
        DistrictingsAreConstrained: action.Bool,
      };
    case ActionTypes.LOAD_IN_JOBS:
      return {
        ...state,
        Jobs : action.Jobs
      }
    case ActionTypes.SET_CURRENT_JOB:
      return {
        ...state,
        CurrentJob : action.Job,
        NumDistrictingsAvailable : action.Job.size
      }
    default:
      return state;
  }
};

export default rootReducer;
