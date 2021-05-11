import React, { Component } from 'react'
import {TableCell, TableRow, TableHead, TableContainer, Table, Paper, TableBody} from '@material-ui/core'
import { connect } from 'react-redux'
import * as SelectionMenuUtilities from '../../../utilities/SelectionMenuUtilities'
import * as StatUtilities from '../../../utilities/StatUtilities'

class MajorityMinorityModal extends Component {
    getMinorityPercentage(summary, minority) {
      switch(minority) {
        case "BLACK":
          return summary.measures.majorityMinorityInfo.blackPercentage
        case "HISPANIC":
          return summary.measures.majorityMinorityInfo.hispanicPercentage
        case "ASIAN":
          return summary.measures.majorityMinorityInfo.asianPercentage
        default:
          return summary.measures.majorityMinorityInfo.nativePercentage
      }
    }



    sortDistrictSummaries(summaries = []) {
        summaries.sort((d1, d2) => {
            let d1Val = this.getMinorityPercentage(d1, this.props.MinoritySelection)
            let d2Val = this.getMinorityPercentage(d2, this.props.MinoritySelection)
            if (d1Val < d2Val) {
              return -1
            }
            if (d1Val > d2Val) {
              return 1
            }
            else {
              return 0
            }
        })
        return summaries;
    }

    render() {
        let sortedSummaries = this.sortDistrictSummaries([...this.props.districting.districtSummaries])
        return (
            <TableContainer component={Paper}>
              <Table className="" aria-label="simple table">
                <TableHead>
                  <TableRow>
                    <TableCell>Minority Ordered Number</TableCell>
                    <TableCell>District Number</TableCell>
                    <TableCell align="left">Total Population</TableCell>
                    <TableCell align="left">{SelectionMenuUtilities.MINORITIES[this.props.MinoritySelection]} Population</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                {sortedSummaries.map((summary, key) => {
                  return(
                    <TableRow key={key}>
                      <TableCell component="th" scope="row">
                    {key+1}
                    </TableCell>
                    <TableCell>{summary.districtNumber}</TableCell>
                    <TableCell align="left">{summary.demographics.tp}</TableCell>
                    <TableCell align="left">{StatUtilities.formatResult(summary.demographics[this.props.MinoritySelection.toLowerCase()], summary.demographics.tp)}</TableCell>
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

export default connect(mapStateToProps, mapDispatchToProps)(MajorityMinorityModal);
