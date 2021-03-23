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

export default class DistrictingObjectiveFunctionDetails extends Component {
    render() {
        return (
        <CollapsibleItem
          expanded={false}
          key={-1}
          header={"Objective Function Details"}
          onSelect={()=>{}}
        >
          <TableContainer component={Paper}>
            <Table aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell>
                    <LabelAndInfoIcon
                      label="Average Population Equality"
                      description={
                        StatUtilities.DESCRIPTIONS.POPULATION_EQUALITY
                      }
                    />
                  </TableCell>
                  <TableCell>
                    <LabelAndInfoIcon
                      label="Split County Score"
                      description={StatUtilities.DESCRIPTIONS.SPLIT_COUNTY_SCORE}
                    />
                  </TableCell>
                  <TableCell>
                    <LabelAndInfoIcon
                      label="Average Deviaton from Average"
                      description={
                        StatUtilities.DESCRIPTIONS.DEVIATION_FROM_AVERAGE
                      }
                    />
                  </TableCell>
                  <TableCell>
                    <LabelAndInfoIcon
                      label="Average Deviation from Enacted"
                      description={
                        StatUtilities.DESCRIPTIONS.DEVIATION_FROM_ENACTED
                      }
                    />
                  </TableCell>
                  <TableCell>
                    <LabelAndInfoIcon
                      label="Average Compactness"
                      description={StatUtilities.DESCRIPTIONS.COMPACTNESS}
                    />
                  </TableCell>
                  <TableCell>
                  <LabelAndInfoIcon
                      label="Average Political Fairness"
                      description={StatUtilities.DESCRIPTIONS.POLITICAL_FAIRNESS}
                    />
                  </TableCell>
                  <TableCell>
                  <LabelAndInfoIcon
                      label="Majority Minority Districts"
                      description={StatUtilities.DESCRIPTIONS.MAJORITY_MINORITY_DISTRICT}
                    />
                  </TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                <TableRow>
                  <TableCell scope="row">
                    {
                      this.props.DistrictingToDisplay.geoJson.objectivefunc[
                        MapUtilities.PROPERTY_LABELS.AVG_POPULATION_EQUALITY
                      ]
                    }
                  </TableCell>
                  <TableCell>
                    {
                      this.props.DistrictingToDisplay.geoJson.objectivefunc[
                        MapUtilities.PROPERTY_LABELS.SPLIT_COUNTIES_SCORE
                      ]
                    }
                  </TableCell>
                  <TableCell>
                    {
                      this.props.DistrictingToDisplay.geoJson.objectivefunc[
                        MapUtilities.PROPERTY_LABELS.AVG_DEVIATION_FROM_AVG_DISTRICTING
                      ]
                    }
                  </TableCell>
                  <TableCell>
                    {
                      this.props.DistrictingToDisplay.geoJson.objectivefunc[
                        MapUtilities.PROPERTY_LABELS.AVG_DEVIATION_FROM_ENACTED_DISTRICTING
                      ]
                    }
                  </TableCell>
                  <TableCell>
                    {
                      this.props.DistrictingToDisplay.geoJson.objectivefunc[
                        MapUtilities.PROPERTY_LABELS.AVG_COMPACTNESS
                      ]
                    }
                  </TableCell>
                  <TableCell>
                    {
                      this.props.DistrictingToDisplay.geoJson.objectivefunc[
                        MapUtilities.PROPERTY_LABELS.AVG_POLITICAL_FAIRNESS
                      ]
                    }
                  </TableCell>
                  <TableCell>
                    {
                      this.props.DistrictingToDisplay.geoJson.objectivefunc[
                        MapUtilities.PROPERTY_LABELS.TOTAL_MAJORITY_MINORITY_DISTRICTS
                      ]
                    }
                  </TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </TableContainer>
          </CollapsibleItem>
        )
    }
}
