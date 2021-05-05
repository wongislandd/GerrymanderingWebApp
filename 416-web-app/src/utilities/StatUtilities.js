import commaNumber from "comma-number";

export const DESCRIPTIONS = {
  POPULATION_EQUALITY:
    "How close in population the districts are to each other.",

  SPLIT_COUNTY_SCORE:
    "Counties that are part of two or more districts are considered split counties.",

  DEVIATION_FROM_AVERAGE:
    "How different a given districting is from the average of the districts.",

  DEVIATION_FROM_ENACTED:
    "How different a given districting is from the enacted districting.",

  COMPACTNESS: "The ratio of district's circumference to their total areas.",

  POLITICAL_FAIRNESS: "How fair the politics be.",

  MAJORITY_MINORITY_DISTRICT:
    "A district is considered a majority minority district if the majority of the population is a minority.",
};

export const COMPARISON_DIRECTIONS = {
  NONE: "NONE",
  UP: "UP",
  DOWN: "DOWN",
};

export function getPercentageChange(x, y) {
  if (x == 0 && y == 0) {
    return 0;
  }
  let decreaseValue = x - y;
  return Math.round((decreaseValue / x) * 100);
}

export function addCommas(value) {
  return commaNumber(value);
}

function percentage(partialValue, totalValue) {
  return (100 * partialValue) / totalValue;
}

export function formatAsPercentage(decimal, places) {
  let pctValue = decimal * 100;
  return Number.parseFloat(Number.parseFloat(pctValue).toFixed(places)) + "%";
}

export function formatResult(partialValue, totalValue) {
  return (
    commaNumber(partialValue) +
    " (" +
    Math.round(percentage(partialValue, totalValue) * 10) / 10 +
    "%)"
  );
}

export function getRandomInt(max) {
  return Math.floor(Math.random() * Math.floor(max));
}

export function rollARandomNumberOfDistrictings() {
  return getRandomInt(100000);
}
