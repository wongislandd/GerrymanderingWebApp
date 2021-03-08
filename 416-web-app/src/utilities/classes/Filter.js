/* A filter is used for both constraints and objective function weights.
It's in this format so that it can be readily turned into a slider.*/
export default class Filter {
    constructor(name, value, minVal, maxVal, step) {
        this.name = name
        this.value = value
        this.minVal = minVal
        this.maxVal = maxVal
        this.step = step
    }
}