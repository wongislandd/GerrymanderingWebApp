import React, { Component } from 'react'
import {TableCell, TableRow, TableHead, TableContainer, Table, Paper, TableBody} from '@material-ui/core'
import { connect } from 'react-redux'
import * as SelectionMenuUtilities from '../../../utilities/SelectionMenuUtilities'
import * as StatUtilities from '../../../utilities/StatUtilities'

class DeviationFromEnactedModal extends Component {
    render() {
        return (
            <TableContainer component={Paper}>
              <Table className="" aria-label="simple table">
                <TableHead>
                  <TableRow>
                    <TableCell>District Number</TableCell>
                    <TableCell align="left">Area (km<sup>2</sup>)</TableCell>
                    <TableCell align="left">Area Difference (km<sup>2</sup>)</TableCell>
                    <TableCell align="left">Area Difference % (km<sup>2</sup>)</TableCell>
                    <TableCell align="left">Population Difference</TableCell>
                    <TableCell align="left">Population Difference %</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                {this.props.districting.districtSummaries.map((summary, key) => {
                  return(
                    <TableRow key={key}>
                    <TableCell>{summary.districtNumber}</TableCell>
                        <p>{StatUtilities.convertArea(summary.area)}</p>
                      <TableCell align="left">
                        {/* {MATH MATH MATH MATH, HOW TO GET ORIGINAL VALUE FROM NEW VALUE AND PCT CHANGE?} */}
                        {StatUtilities.convertArea(StatUtilities.getDifference(summary.area, (this.props.type == "enacted" ? summary.measures.deviationFromEnacted.areaDev : summary.measures.deviationFromAverage.areaDev)))}
                      </TableCell>
                      <TableCell align="left">
                        {StatUtilities.formatAsPercentage(this.props.type == "enacted" ? summary.measures.deviationFromEnacted.areaDev : summary.measures.deviationFromAverage.areaDev, 2)}
                      </TableCell>
                      <TableCell align="left">
                        {/* {FIX HERE TOO} */}
                        {StatUtilities.addCommas((summary.demographics.tp * (this.props.type == "enacted" ? summary.measures.deviationFromEnacted.populationDev : summary.measures.deviationFromAverage.populationDev)).toFixed(0))}
                      </TableCell>
                      <TableCell align="left">
                        {StatUtilities.formatAsPercentage(this.props.type == "enacted" ? summary.measures.deviationFromEnacted.populationDev : summary.measures.deviationFromAverage.populationDev,2)}
                      </TableCell>
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
    MinoritySelection : state.MinoritySelection
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(DeviationFromEnactedModal);
