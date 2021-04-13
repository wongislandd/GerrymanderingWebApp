export default class SortingMethod {
  // Direction should be part of SelectionMenuUtilities.SORTING_DIRECTIONS
  // Method should be a part of SelectionMenuUtilities.SORTING_METHODS
  constructor(method, direction) {
    this.method = method;
    this.direction = direction;
  }
}
