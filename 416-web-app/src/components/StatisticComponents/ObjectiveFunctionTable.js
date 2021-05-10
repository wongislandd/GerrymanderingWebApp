import React, { Component } from "react";
import { CollapsibleItem } from "react-materialize";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
} from "@material-ui/core";
import * as MapUtilities from "../../utilities/MapUtilities";
import * as StatUtilities from "../../utilities/StatUtilities";
import * as SelectionMenuUtilities from "../../utilities/SelectionMenuUtilities";
import LabelAndInfoIcon from "./LabelAndInfoIcon";
import { connect } from 'react-redux'

/* If forDistricting is true, then this is for a districting, otherwise it's for an individual district and will display different values*/
class ObjectiveFunctionTable extends Component {
  mapCompactnessTypeToJsonKey(compactnessType) {
    switch(compactnessType) {
      case SelectionMenuUtilities.COMPACTNESS_TYPES.GRAPH_COMPACTNESS:
        return "graphCompactness"
      case SelectionMenuUtilities.COMPACTNESS_TYPES.POPULATION_FATNESS:
        return "populationFatness"
      default:
        return "polsbyPopper"
    }
  }


  displayDistrictingInfo() {
    return this.props.DistrictingToDisplay != undefined;
  }

  render() {
    return (
      <TableContainer component={Paper}>
        <Table aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>
                <LabelAndInfoIcon
                  label={
                    this.displayDistrictingInfo()
                      ? "Average Population Equality"
                      : "Population Equality"
                  }
                  type="POPULATION"
                />
              </TableCell>
              <TableCell>
                <LabelAndInfoIcon
                  label={
                    this.displayDistrictingInfo()
                      ? "Split County Score"
                      : "Split Counties"
                  }
                  type="SPLIT_COUNTIES"
                />
              </TableCell>
              <TableCell>
                <LabelAndInfoIcon
                  label={
                    this.displayDistrictingInfo()
                      ? "Average Deviation from Average (Area/Pop)"
                      : "Deviation from Average (Area/Pop)"
                  }
                  type="AVG_DEVIATION"
                />
              </TableCell>
              <TableCell>
                <LabelAndInfoIcon
                  label={
                    this.displayDistrictingInfo()
                      ? "Average Deviation from Enacted (Area/Pop)"
                      : "Deviation from Enacted (Area/Pop)"
                  }
                  type="ENACTED_DEVIATION"
                />
              </TableCell>
              <TableCell>
                <LabelAndInfoIcon
                  label={
                    this.displayDistrictingInfo()
                      ? "Average Compactness"
                      : "Compactness"
                  }
                  type="COMPACTNESS"
                />
              </TableCell>
              <TableCell>
                <LabelAndInfoIcon
                  label={
                    this.displayDistrictingInfo()
                      ? "Majority Minority Districts"
                      : "Is Majority Minority District?"
                  }
                  type="MAJORITY_MINORITY"
                />
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            <TableRow>
              <TableCell scope="row">
                {StatUtilities.formatAsPercentage((this.displayDistrictingInfo()
                  ? this.props.DistrictingToDisplay.measures
                      .populationEqualityAvg
                  : this.props.DistrictToDisplay.measures.populationEquality),2)}
              </TableCell>
              <TableCell>
                {this.displayDistrictingInfo()
                  ? this.props.DistrictingToDisplay.measures.splitCountiesScore
                  : this.props.DistrictToDisplay.measures.splitCounties}
              </TableCell>
              <TableCell>
                {StatUtilities.formatAsPercentage((this.displayDistrictingInfo()
                  ? this.props.DistrictingToDisplay.measures
                      .deviationFromAverageAvg.areaDev
                  : this.props.DistrictToDisplay.measures.deviationFromAverage
                      .areaDev),2)} / 
                {StatUtilities.formatAsPercentage((this.displayDistrictingInfo()
                  ? this.props.DistrictingToDisplay.measures
                      .deviationFromAverageAvg.populationDev
                  : this.props.DistrictToDisplay.measures.deviationFromAverage
                      .populationDev), 2)}
              </TableCell>
              <TableCell>
                {StatUtilities.formatAsPercentage((this.displayDistrictingInfo()
                  ? this.props.DistrictingToDisplay.measures
                      .deviationFromEnactedAvg.areaDev
                  : this.props.DistrictToDisplay.measures.deviationFromEnacted
                      .areaDev), 0)} / 
                {StatUtilities.formatAsPercentage((this.displayDistrictingInfo()
                  ? this.props.DistrictingToDisplay.measures
                      .deviationFromEnactedAvg.populationDev
                  : this.props.DistrictToDisplay.measures.deviationFromEnacted
                      .populationDev), 0)}
              </TableCell>
              <TableCell>
                {StatUtilities.formatAsPercentage((this.displayDistrictingInfo()
                  ? this.props.DistrictingToDisplay.measures.compactnessAvg[this.mapCompactnessTypeToJsonKey(this.props.CompactnessSelection)]
                  : this.props.DistrictToDisplay.measures.compactness[this.mapCompactnessTypeToJsonKey(this.props.CompactnessSelection)]), 2)}
              </TableCell>
              <TableCell>
                {this.displayDistrictingInfo()
                  ? this.props.DistrictingToDisplay.measures
                      .majorityMinorityDistricts
                  : this.props.DistrictToDisplay.majorityMinorityDistrict
                  ? "Yes"
                  : "No"}
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </TableContainer>
    );
  }
}


const mapStateToProps = (state, ownProps) => {
  return {
    CompactnessSelection : state.CompactnessSelection
  };
};

export default connect(mapStateToProps)(ObjectiveFunctionTable);
