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
    TentativeDistricting: districting,
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


export const updatePopulationConstraint = (key) => {
  return {
    type: ActionTypes.UPDATE_POPULATION_CONSTRAINT,
    Key: key,
  };
};

export const updateCompactnessConstraint = (key) => {
  return {
    type: ActionTypes.UPDATE_COMPACTNESS_CONSTRAINT,
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

export const updateAnalysisDistrictings = (analysis) => {
  return {
    type: ActionTypes.UPDATE_ANALYSIS_DISTRICTINGS,
    Analysis : analysis,
  };
};

export const loadStateOutlines = (dict) => {
  return {
    type: ActionTypes.LOAD_STATE_OUTLINES,
    Outlines: dict,
  };
};

export const toggleExpandedSummary = (name) => {
  return {
    type: ActionTypes.TOGGLE_EXPANDED_SUMMARY,
    Name: name,
  };
};

export const resetExpandedSummaries = () => {
  return {
    type: ActionTypes.RESET_EXPANDED_SUMMARIES,
  };
};

export const setDistrictingsAreConstrained = (bool) => {
  return {
    type: ActionTypes.SET_DISTRICTINGS_ARE_CONSTRAINED,
    Bool: bool,
  };
};

export const loadInJobs = (jobs) => {
  return {
    type: ActionTypes.LOAD_IN_JOBS,
    Jobs: jobs,
  };
};


export const populatePrecincts = (precinctsGeoJson) => {
  return {
    type: ActionTypes.POPULATE_PRECINCTS,
    PrecinctsGeoJson: precinctsGeoJson,
  };
};

export const populateCounties = (countiesGeoJson) => {
  return {
    type: ActionTypes.POPULATE_COUNTIES,
    CountiesGeoJson: countiesGeoJson,
  };
};

export const populateCurrentDistrictingGeoJson = (districtingGeoJson) => {
  return {
    type: ActionTypes.POPULATE_CURRENT_DISTRICTING_GEOJSON,
    DistrictingGeoJson: districtingGeoJson,
  };
};

export const populateCurrentDistrictingSummary = (summary) => {
  return {
    type: ActionTypes.POPULATE_CURRENT_DISTRICTING_SUMMARY,
    DistrictingSummary: summary,
  };
};

export const returnToStateSelection = () => {
  return {
    type : ActionTypes.RETURN_TO_STATE_SELECTION
  }
}

export const setCurrentJob = (job) => {
  return {
    type: ActionTypes.SET_CURRENT_JOB,
    Job: job,
  };
};

export const updateBWBoxes = (boxData) => {
  return {
    type : ActionTypes.UPDATE_BW_BOXES,
    BoxData : boxData
  }
}

export const updateBWPoints = (pointsData) => {
  return {
    type : ActionTypes.UPDATE_BW_POINTS,
    PointsData : pointsData,
  }
}

export const updateBWEnacted = (enactedData) => {
  return {
    type : ActionTypes.UPDATE_BW_ENACTED,
    EnactedData : enactedData
  }
}

export const updateSelectedTags = (tag) => {
  return {
    type : ActionTypes.UPDATE_SELECTED_TAGS,
    Tag : tag,
  }
}

export const updateJobLoaded = (bool) => {
  return {
    type : ActionTypes.UPDATE_JOB_LOADED,
    Loaded : bool,
  }
}

export const updateEnactedGeoJson = (geoJson) => {
  return {
    type : ActionTypes.UPDATE_ENACTED_GEOJSON,
    GeoJson : geoJson
  }
}

export const toggleEnactedSwitch = (bool) => {
  return {
    type : ActionTypes.TOGGLE_ENACTED_SWITCH,
    DisplayEnacted : bool
  }
}