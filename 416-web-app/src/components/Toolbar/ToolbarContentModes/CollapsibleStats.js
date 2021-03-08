import React, { Component } from 'react'
import { Collapsible, CollapsibleItem} from 'react-materialize'
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper} from '@material-ui/core'
import { connect } from 'react-redux'
import {addFeatureToHighlight, removeFeatureHighlighting, setFeaturedDistrict, setFeaturedPrecinct} from '../../../redux/actions/settingActions'
import * as MapUtilities from '../../../utilities/MapUtilities'

class CollapsibleStats extends Component{
    constructor(props){
        super(props)
    }

    render() {
        return (
            <Collapsible className="stat-window" accordion>
                <CollapsibleItem
                    expanded={false}
                    key={-1}
                    header={"Districting Summary"}
                >
                    Averaged summary information about this districting will go here.
                </CollapsibleItem>
                {this.props.DistrictingToDisplay.geoJson.features.map((feature,key) => {
                        return(
                        <CollapsibleItem 
                        /* The key tells the highlighting engine how to identify the feature 
                         This will work so long as the key matches the feature's ID in the visual object
                         that the map renders, which I think it always will since it's in order. */
                        onMouseEnter={(e) => {
                            feature.id = key
                            this.props.addFeatureToHighlight(feature)
                        }}
                        onMouseLeave={(e) => {
                            feature.id = key
                            this.props.removeFeatureHighlighting(feature)
                        }}
                        expanded={false}
                        key={key}
                        header={"District " + (key+1)}
                        style={{
                            backgroundColor: "rgba(" + feature.properties["rgb-R"] + "," + feature.properties["rgb-G"] + "," + feature.properties["rgb-B"] + "," + MapUtilities.VALUES.UNHIGHLIGHTED_DISTRICT_OPACITY + ")",
                        }}
                        >
                         <TableContainer component={Paper}>
                        <Table aria-label="simple table">
                            <TableHead>
                            <TableRow>
                                <TableCell>Population</TableCell>
                                <TableCell align="right">Pop. Deviation from Ideal</TableCell>
                                <TableCell align="right">Minority Population</TableCell>
                                <TableCell align="right">Democratic Voters</TableCell>
                                <TableCell align="right">Republican Voters</TableCell>
                            </TableRow>
                            </TableHead>
                            <TableBody>
                                <TableCell scope="row">
                                    YES
                                </TableCell>
                                <TableCell align="right">a</TableCell>
                                <TableCell align="right">b</TableCell>
                                <TableCell align="right">c</TableCell>
                                <TableCell align="right">d</TableCell>
                            </TableBody>
                        </Table>
                        </TableContainer>
                        </CollapsibleItem>
                        )
                })}
        </Collapsible>
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        setFeaturedDistrict : (district) => {dispatch(setFeaturedDistrict(district))},
        setFeaturedPrecinct : (precinct) => {dispatch(setFeaturedPrecinct(precinct))},
        addFeatureToHighlight : (feature) => {dispatch(addFeatureToHighlight(feature))},
        removeFeatureHighlighting : (feature) => {dispatch(removeFeatureHighlighting(feature))}
    }
  }
  
  const mapStateToProps = (state, ownProps) => {
    return {
        DisplayPrecincts : state.DisplayPrecincts,
        DisplayDistricts : state.DisplayDistricts,
        FeaturedDistrict : state.FeaturedDistrict,
        FeaturedPrecinct : state.FeaturedPrecinct,
    }
  }
  
  export default connect(mapStateToProps, mapDispatchToProps)(CollapsibleStats);