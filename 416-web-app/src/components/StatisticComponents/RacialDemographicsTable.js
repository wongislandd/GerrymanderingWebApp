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
        const feature = this.props.DistrictToDisplay
        return (
            <TableContainer component={Paper}>
                  <Table aria-label="simple table">
                    <TableHead>
                      <TableRow>
                        <TableCell>Total Population</TableCell>
                        <TableCell align="right">White</TableCell>
                        <TableCell align="right">Black</TableCell>
                        <TableCell align="right">Asian</TableCell>
                        <TableCell align="right">
                          Native American or Alaskan
                        </TableCell>
                        <TableCell align="right">
                          Pacific Islander or Hawaiian
                        </TableCell>
                        <TableCell align="right">Undesignated</TableCell>
                        <TableCell align="right">Other</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      <TableRow>
                        <TableCell scope="row">
                          {StatUtilities.addCommas(
                            feature.properties[
                              MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION
                            ]
                          )}
                        </TableCell>
                        <TableCell align="right">
                          {StatUtilities.formatResult(
                            feature.properties[
                              MapUtilities.PROPERTY_LABELS.WHITE_COUNT
                            ],
                            feature.properties[
                              MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION
                            ]
                          )}
                        </TableCell>
                        <TableCell align="right">
                          {StatUtilities.formatResult(
                            feature.properties[
                              MapUtilities.PROPERTY_LABELS.BLACK_COUNT
                            ],
                            feature.properties[
                              MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION
                            ]
                          )}
                        </TableCell>
                        <TableCell align="right">
                          {StatUtilities.formatResult(
                            feature.properties[
                              MapUtilities.PROPERTY_LABELS.ASIAN_COUNT
                            ],
                            feature.properties[
                              MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION
                            ]
                          )}
                        </TableCell>
                        <TableCell align="right">
                          {StatUtilities.formatResult(
                            feature.properties[
                              MapUtilities.PROPERTY_LABELS.NATIVE_COUNT
                            ],
                            feature.properties[
                              MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION
                            ]
                          )}
                        </TableCell>
                        <TableCell align="right">
                          {StatUtilities.formatResult(
                            feature.properties[
                              MapUtilities.PROPERTY_LABELS
                                .PACIFIC_ISLANDER_COUNT
                            ],
                            feature.properties[
                              MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION
                            ]
                          )}
                        </TableCell>
                        <TableCell align="right">
                          {StatUtilities.formatResult(
                            feature.properties[
                              MapUtilities.PROPERTY_LABELS.UNDESIGNATED_COUNT
                            ],
                            feature.properties[
                              MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION
                            ]
                          )}
                        </TableCell>
                        <TableCell align="right">
                          {StatUtilities.formatResult(
                            feature.properties[
                              MapUtilities.PROPERTY_LABELS.RACE_OTHER_COUNT
                            ],
                            feature.properties[
                              MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION
                            ]
                          )}
                        </TableCell>
                      </TableRow>
                    </TableBody>
                  </Table>
                </TableContainer>
        )
    }
}
