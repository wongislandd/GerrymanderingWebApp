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

export default class RacialDemographicsTable extends Component {
  render() {
    const district = this.props.DistrictToDisplay;
    return (
      <TableContainer component={Paper}>
        <Table aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>Total Population</TableCell>
              <TableCell align="right">White</TableCell>
              <TableCell align="right">Black</TableCell>
              <TableCell align="right">Hispanic</TableCell>
              <TableCell align="right">Asian</TableCell>
              <TableCell align="right">Native American or Alaskan</TableCell>
              <TableCell align="right">Pacific Islander or Hawaiian</TableCell>
              <TableCell align="right">Other</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            <TableRow>
              <TableCell scope="row">
                {StatUtilities.addCommas(district.demographics.tp)}
              </TableCell>
              <TableCell align="right">
                {StatUtilities.formatResult(
                  district.demographics.white,
                  district.demographics.tp
                )}
              </TableCell>
              <TableCell align="right">
                {StatUtilities.formatResult(
                  district.demographics.black,
                  district.demographics.tp
                )}
              </TableCell>
              <TableCell align="right">
                {StatUtilities.formatResult(
                  district.demographics.hispanic,
                  district.demographics.tp
                )}
              </TableCell>
              <TableCell align="right">
                {StatUtilities.formatResult(
                  district.demographics.asian,
                  district.demographics.tp
                )}
              </TableCell>
              <TableCell align="right">
                {StatUtilities.formatResult(
                  district.demographics.natives,
                  district.demographics.tp
                )}
              </TableCell>
              <TableCell align="right">
                {StatUtilities.formatResult(
                  district.demographics.pacific,
                  district.demographics.tp
                )}
              </TableCell>
              <TableCell align="right">
                {StatUtilities.formatResult(
                  district.demographics.otherRace,
                  district.demographics.tp
                )}
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </TableContainer>
    );
  }
}
