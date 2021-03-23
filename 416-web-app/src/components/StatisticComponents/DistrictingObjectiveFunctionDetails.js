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
                      label="Population Equality"
                      description={
                        StatUtilities.DESCRIPTIONS.POPULATION_EQUALITY
                      }
                    />
                  </TableCell>
                  <TableCell>
                    <LabelAndInfoIcon
                      label="Split Counties"
                      description={StatUtilities.DESCRIPTIONS.SPLIT_COUNTIES}
                    />
                  </TableCell>
                  <TableCell>
                    <LabelAndInfoIcon
                      label="Deviaton from Average"
                      description={
                        StatUtilities.DESCRIPTIONS.DEVIATION_FROM_AVERAGE
                      }
                    />
                  </TableCell>
                  <TableCell>
                    <LabelAndInfoIcon
                      label="Deviation from Enacted"
                      description={
                        StatUtilities.DESCRIPTIONS.DEVIATION_FROM_ENACTED
                      }
                    />
                  </TableCell>
                  <TableCell>
                    <LabelAndInfoIcon
                      label="Compactness"
                      description={StatUtilities.DESCRIPTIONS.COMPACTNESS}
                    />
                  </TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                <TableRow>
                  <TableCell scope="row">
                    {
                      this.props.DistrictingToDisplay.geoJson.objectivefunc[
                        MapUtilities.PROPERTY_LABELS.POPULATION_EQUALITY
                      ]
                    }
                  </TableCell>
                  <TableCell>
                    {
                      this.props.DistrictingToDisplay.geoJson.objectivefunc[
                        MapUtilities.PROPERTY_LABELS.SPLIT_COUNTIES
                      ]
                    }
                  </TableCell>
                  <TableCell>
                    {
                      this.props.DistrictingToDisplay.geoJson.objectivefunc[
                        MapUtilities.PROPERTY_LABELS.DEVIATION_FROM_AVG
                      ]
                    }
                  </TableCell>
                  <TableCell>
                    {
                      this.props.DistrictingToDisplay.geoJson.objectivefunc[
                        MapUtilities.PROPERTY_LABELS.DEVIATION_FROM_ENACTED
                      ]
                    }
                  </TableCell>
                  <TableCell>
                    {
                      this.props.DistrictingToDisplay.geoJson.objectivefunc[
                        MapUtilities.PROPERTY_LABELS.COMPACTNESS
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
