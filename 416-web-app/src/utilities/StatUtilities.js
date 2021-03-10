import commaNumber from 'comma-number'

export const DESCRIPTIONS = {
    POPULATION_EQUALITY : "How close in population the districts are to each other.",



    SPLIT_COUNTIES : "Counties that are part of two or more districts are considered split counties.",



    DEVIATION_FROM_AVERAGE : "How different a given districting is from the average of the districts.",



    DEVIATION_FROM_ENACTED : "How different a given districting is from the enacted districting.",



    COMPACTNESS : "The ratio of district's circumference to their total areas.",
}

export const COMPARISON_DIRECTIONS = {
    NONE : "NONE",
    UP : "UP",
    DOWN : "DOWN",
}

export function getPercentageChange(x,y){
    var decreaseValue = x - y;
    return Math.round((decreaseValue / x) * 100);
}

export function addCommas(value) {
    return (commaNumber(value))
}

function percentage(partialValue, totalValue) {
    return (100 * partialValue) / totalValue
}    

export function formatResult(partialValue, totalValue) {
    return commaNumber(partialValue) + " ("+ (Math.round(percentage(partialValue, totalValue) * 10)/10) +"%)"
}

function getRandomInt(max) {
    return Math.floor(Math.random() * Math.floor(max));
  }

export function rollARandomNumberOfDistrictings() {
    return (getRandomInt(100000))
}