export const LABELS = {
  RETURN_TO_MAP: "Return to Map",
  BACK_TO_STATE_SELECTION: "Back to State Selection",
  APPLY_JOB: "Apply Job",
  FILTERS_HEADER: "Filters",
  OBJECTIVE_FUNCTION_WEIGHTS_HEADER: "Objective Function Weights",
  CONSTRAINTS_HEADER: "Constraints",
  LOAD_THIS_DISTRICTING: "Load This Districting",
  INCUMBENT_PROTECTION_OPTIONS: "Incumbent Protection Options",
  POPULATION_CONSTRAINT_OPTIONS: "Population Constraint Options",
  VOTING_POPULATION_TO_CONSTRAIN: "Voting Population to Constrain",
  MINORITY_POPULATION_TO_CONSTRAIN: "Minority Population to Constrain",
  COMPACTNESS_TYPE: "Type of Compactness",
  CHOOSE_A_MINORITY_POPULATION: "Choose a Minority Population",
  CHOOSE_A_VOTING_POPULATION: "Choose a Voting Population",
  CHOOSE_A_TYPE_OF_COMPACTNESS: "Choose a Method of Compactness",
  ANALYSIS_RESULTS : "Top Scoring Objective Function Districtings",
  SELECT_A_JOB: "Select a Job",
  CONTINUE_WITH_THIS_JOB: "Continue with this Job",
};

export const DESCRIPTIONS = {
  MINORITY_POPULATION_CONSTRAINT:
    "A box-and-whisker plot showcasing the distribution of the selected minority in each districting will be generated.",
  VOTING_POPULATION_CONSTRAINT:
    "The selected voting population will be the one considered when applying a limit to the maximum population difference. Maximum population difference constrains the average deviation from ideal from a districting's districts.",
  COMPACTNESS_TYPE_CONSTRAINT:
    "Add info about each individual compactness here. Ex. Why you'd pick one over the other",
};

export const MINORITIES = {
  BLACK: "Black",
  HISPANIC: "Hispanic or Latino",
  ASIAN: "Asian",
  NATIVE_AMERICAN: "Native American or Alaskan Native",
};

export const POPULATIONS = {
  TOTAL_POPULATION: "Total Population",
  VOTING_AGE_POPULATION: "Voting Age Population",
  CITIZEN_VOTING_AGE_POPULATION: "Citizen Voting Age Population",
};

export const COMPACTNESS_TYPES = {
  POLSBY_POPPER: "Polsby Popper",
  POPULATION_FATNESS: "Population Fatness",
  GRAPH_COMPACTNESS: "Graph Compactness",
};

export const ANALYSIS_CATEGORIES = {
  TOP_SCORING: "TopScoring",
  HIGH_SCORING_SIMILAR_ENACTED: "HighScoringSimilarEnacted",
  HIGH_SCORING_MAJORITY_MINORITY: "HighScoringMajorityMinority",
  TOP_DIFFERENT_AREA_PAIR_DEVIATIONS: "TopDifferentAreaPairDeviations",
};

export const CONSTRAINT_KEYS = {
  MajorityMinorityDistricts: "MinimumMajorityMinorityDistricts",
  MinorityThreshold: "MinorityThreshold",
  PopulationDifference: "PopulationDifference",
  Compactness: "Compactness",
};

export const TAGS = {
  CLOSE_TO_ENACTED : "Closest to Enacted",
  CLOSE_TO_AVERAGE : "Closest to Average",
  MAJORITY_MINORITY : "Top Majority Minority",
  AREA_PAIR_DEVIATION : "Top Area Pair Deviation",
}

export const IDEAL_POPULATIONS = {
  NORTH_CAROLINA : 733499,
  LOUISIANA : 0,
  ALABAMA : 0,
}