import { setCurrentDistricting } from "../redux/actions/settingActions"

/* To be used as a shared enum-like variable for setting/checking what mode goes in the DynamicToolbarContent */
export const MODES = {
    SETTINGS : "settings",
    STATS : "stats",
    HISTORY : "history",
    TENTATIVE_STATS : "tentative_stats",
    FILTER_SETTINGS : "filter_settings",
    FILTER_SUMMARY : "filter_summary"
}


export const CONSTANTS = {
    PRECINCT_SWITCH_ID : "precinct-switch",
    DISTRICT_SWITCH_ID : "district-switch",
    COUNTY_SWITCH_ID : "county-switch"
}

export const LABELS = {
    TOOLBAR_OPTIONS_HEADER_LABEL : "Options",
    TOOLBAR_STATISTICS_HEADER_LABEL : "Stats",
    TOOLBAR_HISTORY_HEADER_LABEL : "History",
    TOOLBAR_FILTER_HEADER_LABEL : "Filter",
    TOGGLE_PRECINCT_DISPLAY_LABEL : "Display Precincts",
    TOGGLE_DISTRICT_DISPLAY_LABEL : "Display Districts",
    TOGGLE_COUNTY_DISPLAY_LABEL : "Display Counties",
    INCUMBENT_PROTECTION_OPTIONS_LABEL : "Incumbent Protection Options",
    PROTECTED_POLITICANS_LABEL : "Protected Politicans",
    FILTER_SUMMARY_LABEL : "Filter Summary",
    FILTER_DISTRICTING_CONFIRMATION_LABEL : "Filter Districtings With These Settings",
    DISTRICTING_HISTORY_LABEL : "Districting History",
    CHOOSE_A_DISTRICTING_LABEL : "Choose a Districting",
    NO_TENTATIVE_STATS_TO_DISPLAY_LABEL : ""
}