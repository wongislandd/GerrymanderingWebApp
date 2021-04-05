import * as ActionTypes from "./ActionTypes";

/* Takes in a constant from ViewportUtilities.STATE_OPTIONS */
export const setCurrentState = (state) => {
  return {
    type: ActionTypes.SET_CURRENT_STATE,
    State: state,
  };
};

export const togglePrecinctSwitch = (bool) => {
  return {
    type: ActionTypes.TOGGLE_PRECINCT_SWITCH,
    DisplayPrecincts: bool,
  };
};

export const toggleDistrictSwitch = (bool) => {
  return {
    type: ActionTypes.TOGGLE_DISTRICT_SWITCH,
    DisplayDistricts: bool,
  };
};

export const toggleCountySwitch = (bool) => {
  return {
    type: ActionTypes.TOGGLE_COUNTY_SWITCH,
    DisplayCounties: bool,
  };
};

export const setTentativeDistricting = (districting) => {
  return {
    type: ActionTypes.SET_TENTATIVE_DISTRICTING,
    TentativeDistricting: {
      name: districting.name,
      geoJson: districting.geoJson,
    },
  };
};

export const setCurrentDistricting = (districting) => {
  return {
    type: ActionTypes.SET_CURRENT_DISTRICTING,
    CurrentDistricting: districting,
  };
};

export const moveMouse = (event) => {
  if (event.nativeEvent) {
    return {
      type: ActionTypes.MOVE_MOUSE,
      MouseX: event.nativeEvent.offsetX,
      MouseY: event.nativeEvent.offsetY,
    };
  }
};

export const setMouseEntered = (bool) => {
  return {
    type: ActionTypes.SET_MOUSE_ENTERED,
    MouseEntered: bool,
  };
};

export const setFeaturedDistrict = (district) => {
  return {
    type: ActionTypes.SET_FEATURED_DISTRICT,
    FeaturedDistrict: district,
  };
};

export const setFeaturedPrecinct = (precinct) => {
  return {
    type: ActionTypes.SET_FEATURED_PRECINCT,
    FeaturedPrecinct: precinct,
  };
};

export const setLoadedStatus = (bool) => {
  return {
    type: ActionTypes.SET_LOADED_STATUS,
    Loaded: bool,
  };
};

export const updateObjectiveFunctionSettings = (key, newValue) => {
  return {
    type: ActionTypes.UPDATE_OBJECTIVE_FUNCTION_SETTINGS,
    Key: key,
    NewValue: newValue,
  };
};

export const updateConstraintSliderSettings = (key, newValue) => {
  return {
    type: ActionTypes.UPDATE_CONSTRAINT_SLIDER_SETTINGS,
    Key: key,
    NewValue: newValue,
  };
};

export const updateIncumbentProtection = (key, newValue) => {
  return {
    type: ActionTypes.UPDATE_INCUMBENT_PROTECTION,
    Key: key,
    NewValue: newValue,
  };
};

export const updatePopulationConstraint = (key) => {
  return {
    type: ActionTypes.UPDATE_POPULATION_CONSTRAINT,
    Key: key,
  };
};

export const updateMinorityConstraint = (key) => {
  return {
    type: ActionTypes.UPDATE_MINORITY_CONSTRAINT,
    Key: key,
  };
};

export const setInSelectionMenu = (bool) => {
  return {
    type: ActionTypes.SET_IN_SELECTION_MENU,
    In: bool,
  };
};

export const changeValueSortedBy = (value, descending) => {
  return {
    type: ActionTypes.CHANGE_VALUE_SORTED_BY,
    Value: value,
    Descending: descending,
  };
};

export const setComparisonDistrictingA = (districting) => {
  return {
    type: ActionTypes.SET_COMPARISON_DISTRICTING_A,
    Districting: districting,
  };
};

export const setComparisonDistrictingB = (districting) => {
  return {
    type: ActionTypes.SET_COMPARISON_DISTRICTING_B,
    Districting: districting,
  };
};

export const addFeatureToHighlight = (feature) => {
  return {
    type: ActionTypes.ADD_FEATURE_TO_HIGHLIGHT,
    Feature: feature,
  };
};

export const removeFeatureHighlighting = (feature) => {
  return {
    type: ActionTypes.REMOVE_FEATURE_HIGHLIGHTING,
    Feature: feature,
  };
};

export const resetAllHighlighting = () => {
  return {
    type: ActionTypes.RESET_ALL_HIGHLIGHTING,
  };
};

export const clearFeaturesToUnhighlight = () => {
  return {
    type: ActionTypes.CLEAR_FEATURES_TO_UNHIGHLIGHT,
  };
};

export const loadInDistricting = (districting) => {
  return {
    type: ActionTypes.LOAD_IN_DISTRICTINGS,
    Districting: districting,
  };
};

export const setCurrentTab = (tab) => {
  return {
    type: ActionTypes.SET_CURRENT_TAB,
    Tab: tab,
  };
};

export const setMinimizedMap = (bool) => {
  return {
    type: ActionTypes.SET_MINIMIZED_MAP,
    Minimized: bool,
  };
};

export const setViewport = (viewport) => {
  return {
    type: ActionTypes.SET_VIEWPORT,
    Viewport: viewport,
  };
};

export const maximizeMap = () => {
  return {
    type: ActionTypes.MAXIMIZE_MAP,
  };
};

export const minimizeMap = () => {
  return {
    type: ActionTypes.MINIMIZE_MAP,
  };
};

export const setStatShowcasedDistrictID = (districtID) => {
  return {
    type: ActionTypes.SET_STAT_SHOWCASED_DISTRICT_ID,
    DistrictID: districtID,
  };
};

export const restoreDefaultStateForNewDistricting = () => {
  return {
    type: ActionTypes.RESTORE_DEFAULT_STATE_FOR_NEW_DISTRICTING,
  };
};

export const setNewDistrictingSelected = (bool) => {
  return {
    type: ActionTypes.SET_NEW_DISTRICTING_SELECTED,
    Bool: bool,
  };
};

export const setTentativeState = (state) => {
  return {
    type: ActionTypes.SET_TENTATIVE_STATE,
    State: state,
  };
};

export const setEnabledStateOfConstraint = (key, bool) => {
  return {
    type: ActionTypes.SET_ENABLED_STATE_OF_CONSTRAINT,
    Key: key,
    Bool: bool,
  };
};

export const setNumberOfDistrictingsAvailable = (number) => {
  return {
    type: ActionTypes.SET_NUMBER_OF_DISTRICTINGS_AVAILABLE,
    Number: number,
  };
};

export const setDistrictingSortMethod = (method, direction) => {
  return {
    type: ActionTypes.SET_DISTRICTING_SORT_METHOD,
    Method: method,
    Direction: direction,
  };
};

export const updateAnalysisDistrictings = (dict) => {
  return {
    type: ActionTypes.UPDATE_ANALYSIS_DISTRICTINGS,
    Dictionary: dict,
  };
};

export const setShowFullListing = (bool) => {
  return {
    type: ActionTypes.SET_SHOW_FULL_LISTING,
    Bool: bool,
  };
};
