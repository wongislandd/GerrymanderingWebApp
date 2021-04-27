export const LABELS = {
  RETURN_TO_MAP: "Return to Map",
  BACK_TO_STATE_SELECTION : "Back to State Selection",
  APPLY_JOB: "Apply Job",
  FILTERS_HEADER: "Filters",
  OBJECTIVE_FUNCTION_WEIGHTS_HEADER: "Objective Function Weights",
  CONSTRAINTS_HEADER: "Constraints",
  LOAD_THIS_DISTRICTING: "Load This Districting",
  INCUMBENT_PROTECTION_OPTIONS: "Incumbent Protection Options",
  POPULATION_CONSTRAINT_OPTIONS: "Population Constraint Options",
  VOTING_POPULATION_TO_CONSTRAIN: "Voting Population to Constrain",
  MINORITY_POPULATION_TO_CONSTRAIN: "Minority Population to Constrain",
  COMPACTNESS_TYPE : "Type of Compactness",
  CHOOSE_A_MINORITY_POPULATION: "Choose a Minority Population",
  CHOOSE_A_VOTING_POPULATION: "Choose a Voting Population",
  CHOOSE_A_TYPE_OF_COMPACTNESS : "Choose a Method of Compactness",
  CONSTRAINED_DISTRICTING_RESULTS: "Constrained Districting Results",
  DISTRICTING_BREAKDOWN: "Districting Breakdown",
  SELECT_A_JOB : "Select a Job",
  CONTINUE_WITH_THIS_JOB : "Continue with this Job"

};

export const DESCRIPTIONS = {
  MINORITY_POPULATION_CONSTRAINT:
    "A box-and-whisker plot showcasing the distribution of the selected minority in each districting will be generated.",
  VOTING_POPULATION_CONSTRAINT:
    "The selected voting population will be the one considered when applying a limit to the maximum population difference.",
  COMPACTNESS_TYPE_CONSTRAINT: 
    "Add info about each individual compactness here. Ex. Why you'd pick one over the other"
};


export const MINORITIES = {
  B: "Black",
  HL: "Hispanic or Latino",
  A: "Asian",
  I: "Native American or Alaskan Native",
  P: "Native Hawaiian or Pacific Islander",
};

export const POPULATIONS = {
  TOTAL: "Total Population",
  VAP: "Voting Age Population",
  CVAP: "Citizen Voting Age Population",
};

export const COMPACTNESS_TYPES = {
  PolsbyPopper : "Polsby Popper",
  PopulationFatness : "Population Fatness",
  GraphCompactness : "Graph Compactness",
}

export const ANALYSIS_CATEGORIES = {
  TOP_SCORING: "TopScoring",
  HIGH_SCORING_SIMILAR_ENACTED: "HighScoringSimilarEnacted",
  HIGH_SCORING_MAJORITY_MINORITY: "HighScoringMajorityMinority",
  TOP_DIFFERENT_AREA_PAIR_DEVIATIONS: "TopDifferentAreaPairDeviations",
};


export const CONSTRAINT_KEYS = {
  MajorityMinorityDistricts : "MajorityMinorityDistricts",
  PopulationDifference : "PopulationDifference",
  Compactness : "Compactness"
}


// Maps this ^ to something more user friendly, any change to the values above have to be changed
// to the key below. Unfortunatly
export const ANALYSIS_CATEGORIES_USER_FRIENDLY = {
  TopScoring: "Top Districtings According to Objective Function",
  HighScoringSimilarEnacted: "Highest Scoring Districtings Similar to Enacted",
  HighScoringMajorityMinority:
    "Highest Scoring Districtings with Desired Majority Minority",
  TopDifferentAreaPairDeviations:
    "Districtings with the Greatest Area Pair-Deviations",
};
