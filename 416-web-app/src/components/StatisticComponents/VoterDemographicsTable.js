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

export default class VoterDemographicsTable extends Component {
  render() {
    const feature = this.props.DistrictToDisplay;
    return (
      <TableContainer component={Paper}>
        <Table aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>Total Voters</TableCell>
              <TableCell>Democratic Affiliated</TableCell>
              <TableCell>Republican Affiliated</TableCell>
              <TableCell>Other Affiliations</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            <TableRow>
              <TableCell scope="row">
                {StatUtilities.addCommas(
                  feature.properties[
                    MapUtilities.PROPERTY_LABELS.TOTAL_VOTER_COUNT
                  ]
                )}
              </TableCell>
              <TableCell>
                {StatUtilities.formatResult(
                  feature.properties[
                    MapUtilities.PROPERTY_LABELS.DEMOCRAT_COUNT
                  ],
                  feature.properties[
                    MapUtilities.PROPERTY_LABELS.TOTAL_VOTER_COUNT
                  ]
                )}
              </TableCell>
              <TableCell>
                {StatUtilities.formatResult(
                  feature.properties[
                    MapUtilities.PROPERTY_LABELS.REPUBLICAN_COUNT
                  ],
                  feature.properties[
                    MapUtilities.PROPERTY_LABELS.TOTAL_VOTER_COUNT
                  ]
                )}
              </TableCell>
              <TableCell>
                {StatUtilities.formatResult(
                  feature.properties[
                    MapUtilities.PROPERTY_LABELS.PARTY_OTHER_COUNT
                  ],
                  feature.properties[
                    MapUtilities.PROPERTY_LABELS.TOTAL_VOTER_COUNT
                  ]
                )}
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </TableContainer>
    );
  }
}
