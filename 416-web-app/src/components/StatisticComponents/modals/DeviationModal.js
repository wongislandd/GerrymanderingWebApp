import React, { Component } from 'react'
import {TableCell, TableRow, TableHead, TableContainer, Table, Paper, TableBody} from '@material-ui/core'
import { connect } from 'react-redux'
import * as SelectionMenuUtilities from '../../../utilities/SelectionMenuUtilities'
import * as StatUtilities from '../../../utilities/StatUtilities'

class DeviationFromEnactedModal extends Component {
    render() {
        console.log(this.props.districting.districtSummaries)
        return (
            <TableContainer component={Paper}>
              <Table className="" aria-label="simple table">
                <TableHead>
                  <TableRow>
                    <TableCell>District Number</TableCell>
                    <TableCell align="left">Area Difference</TableCell>
                    <TableCell align="left">Area Difference %</TableCell>
                    <TableCell align="left">Population Difference</TableCell>
                    <TableCell align="left">Population Difference %</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                {this.props.districting.districtSummaries.map((summary, key) => {
                  return(
                    <TableRow key={key}>
                    <TableCell>{summary.districtNumber}</TableCell>
                      <TableCell align="left">
                        {/* {MATH MATH MATH MATH, HOW TO GET ORIGINAL VALUE FROM NEW VALUE AND PCT CHANGE?} */}
                        {(summary.area * (this.props.type == "enacted" ? summary.measures.deviationFromEnacted.areaDev : summary.measures.deviationFromAverage.areaDev)).toFixed(2)}
                      </TableCell>
                      <TableCell align="left">
                        {StatUtilities.formatAsPercentage(this.props.type == "enacted" ? summary.measures.deviationFromEnacted.areaDev : summary.measures.deviationFromAverage.areaDev, 2)}
                      </TableCell>
                      <TableCell align="left">
                        {/* {FIX HERE TOO} */}
                        {(summary.demographics.tp * (this.props.type == "enacted" ? summary.measures.deviationFromEnacted.populationDev : summary.measures.deviationFromAverage.populationDev)).toFixed(2)}
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
