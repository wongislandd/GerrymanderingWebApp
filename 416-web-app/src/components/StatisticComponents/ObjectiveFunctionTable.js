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
import LabelAndInfoIcon from "./LabelAndInfoIcon";

/* If forDistricting is true, then this is for a districting, otherwise it's for an individual district and will display different values*/
export default class ObjectiveFunctionTable extends Component {
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
                  description={StatUtilities.DESCRIPTIONS.POPULATION_EQUALITY}
                />
              </TableCell>
              <TableCell>
                <LabelAndInfoIcon
                  label={
                    this.displayDistrictingInfo()
                      ? "Split County Score"
                      : "Split Counties"
                  }
                  description={StatUtilities.DESCRIPTIONS.SPLIT_COUNTY_SCORE}
                />
              </TableCell>
              <TableCell>
                <LabelAndInfoIcon
                  label={
                    this.displayDistrictingInfo()
                      ? "Average Deviation from Average"
                      : "Deviation from Average"
                  }
                  description={
                    StatUtilities.DESCRIPTIONS.DEVIATION_FROM_AVERAGE
                  }
                />
              </TableCell>
              <TableCell>
                <LabelAndInfoIcon
                  label={
                    this.displayDistrictingInfo()
                      ? "Average Deviation from Enacted"
                      : "Deviation from Enacted"
                  }
                  description={
                    StatUtilities.DESCRIPTIONS.DEVIATION_FROM_ENACTED
                  }
                />
              </TableCell>
              <TableCell>
                <LabelAndInfoIcon
                  label={
                    this.displayDistrictingInfo()
                      ? "Average Compactness"
                      : "Compactness"
                  }
                  description={StatUtilities.DESCRIPTIONS.COMPACTNESS}
                />
              </TableCell>
              <TableCell>
                <LabelAndInfoIcon
                  label={
                    this.displayDistrictingInfo()
                      ? "Average Political Fairness"
                      : "Political Fairness"
                  }
                  description={StatUtilities.DESCRIPTIONS.POLITICAL_FAIRNESS}
                />
              </TableCell>
              <TableCell>
                <LabelAndInfoIcon
                  label={
                    this.displayDistrictingInfo()
                      ? "Majority Minority Districts"
                      : "Is Majority Minority District?"
                  }
                  description={
                    StatUtilities.DESCRIPTIONS.MAJORITY_MINORITY_DISTRICT
                  }
                />
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            <TableRow>
              <TableCell scope="row">
                {this.displayDistrictingInfo()
                  ? this.props.DistrictingToDisplay.measures.populationEqualityAvg
                  : this.props.DistrictToDisplay.measures.populationEquality}
              </TableCell>
              <TableCell>
                {this.displayDistrictingInfo()
                  ? this.props.DistrictingToDisplay.measures.splitCountiesScore
                  : this.props.DistrictToDisplay.measures.splitCounties}
              </TableCell>
              <TableCell>
                {this.displayDistrictingInfo()
                  ? this.props.DistrictingToDisplay.measures.deviationFromAverageAvg.areaDev
                  : this.props.DistrictToDisplay.measures.deviationFromAverage.areaDev}
              </TableCell>
              <TableCell>
                {this.displayDistrictingInfo()
                  ? this.props.DistrictingToDisplay.measures.deviationFromEnactedAvg.areaDev
                  : this.props.DistrictToDisplay.measures.deviationFromEnacted.areaDev}
              </TableCell>
              <TableCell>
                {this.displayDistrictingInfo()
                  ? this.props.DistrictingToDisplay.measures.compactnessAvg.polsbyPopper
                  : this.props.DistrictToDisplay.measures.compactness.polsbyPopper}
              </TableCell>
              <TableCell>
                {this.displayDistrictingInfo()
                  ? this.props.DistrictingToDisplay.measures.majorityMinorityDistricts
                  : this.props.DistrictToDisplay.isMajorityMinorityDistrict}
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </TableContainer>
    );
  }
}
