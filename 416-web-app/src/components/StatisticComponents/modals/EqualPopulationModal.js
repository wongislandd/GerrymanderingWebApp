import React, { Component } from 'react'
import {TableCell, TableRow, TableHead, TableContainer, Table, Paper, TableBody} from '@material-ui/core'
import { connect } from 'react-redux'
import * as SelectionMenuUtilities from '../../../utilities/SelectionMenuUtilities'
import * as StatUtilities from '../../../utilities/StatUtilities'

class EqualPopulationModal extends Component {
    render() {
        return (
            <TableContainer component={Paper}>
              <Table className="" aria-label="simple table">
                <TableHead>
                  <TableRow>
                    <TableCell>District Number</TableCell>
                    <TableCell align="left">Total Population</TableCell>
                    <TableCell align="left">Deviation from Ideal (Ideal: {SelectionMenuUtilities.IDEAL_POPULATIONS[this.props.CurrentState]})</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                {this.props.districting.districtSummaries.map((summary, key) => {
                  return(
                    <TableRow key={key}>
                    <TableCell>{summary.districtNumber}</TableCell>
                    <TableCell align="left">{StatUtilities.addCommas(summary.demographics.tp)}</TableCell>
                    <TableCell align="left">{StatUtilities.addCommas(summary.demographics.tp-SelectionMenuUtilities.IDEAL_POPULATIONS[this.props.CurrentState]) + " (" + StatUtilities.formatAsPercentage(summary.measures.populationDiffFromIdeal,2)})</TableCell>
                    </TableRow>
                  )})}
                </TableBody>
              </Table>
            </TableContainer>
          );
    }
}


const mapDispatchToProps = (dispatch) => {
  return {

  };
};

const mapStateToProps = (state, ownProps) => {
  return {
    MinoritySelection : state.MinoritySelection,
    CurrentState : state.CurrentState
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(EqualPopulationModal);
