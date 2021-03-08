import React, { Component } from 'react'
import { Collapsible, CollapsibleItem} from 'react-materialize'
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper} from '@material-ui/core'
import { connect } from 'react-redux'
import {addFeatureToHighlight, removeFeatureHighlighting, setFeaturedDistrict, setFeaturedPrecinct} from '../../../redux/actions/settingActions'
import * as MapUtilities from '../../../utilities/MapUtilities'
import PartyPieChart from './PartyDemographics'
import RacePieChart from './RacialDemographics'
import PartyDemographics from './PartyDemographics'
import RacialDemographics from './RacialDemographics'


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
                        <h5>Voter Demographics</h5>
                        <TableContainer component={Paper}>
                        <Table aria-label="simple table">
                            <TableHead>
                            <TableRow>
                                <TableCell>Total Voters</TableCell>
                                <TableCell align="right">Democratic Affiliated</TableCell>
                                <TableCell align="right">Republican Affiliated</TableCell>
                                <TableCell align="right">Other Affiliations</TableCell>
                            </TableRow>
                            </TableHead>
                            <TableBody>
                                <TableRow>
                                <TableCell scope="row">
                                {feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_VOTER_COUNT]}
                                </TableCell>
                                <TableCell align="right">{feature.properties[MapUtilities.PROPERTY_LABELS.DEMOCRAT_COUNT]}</TableCell>
                                <TableCell align="right">{feature.properties[MapUtilities.PROPERTY_LABELS.REPUBLICAN_COUNT]}</TableCell>
                                <TableCell align="right">{feature.properties[MapUtilities.PROPERTY_LABELS.PARTY_OTHER_COUNT]}</TableCell>
                                </TableRow>
                            </TableBody>
                        </Table>
                        </TableContainer>
                        <h5>Racial Demographics</h5>
                        <TableContainer component={Paper}>
                        <Table aria-label="simple table">
                            <TableHead>
                            <TableRow>
                                <TableCell>Total Population</TableCell>
                                <TableCell align="right">White</TableCell>
                                <TableCell align="right">Black</TableCell>
                                <TableCell align="right">Asian</TableCell>
                                <TableCell align="right">Native American or Alaskan</TableCell>
                                <TableCell align="right">Pacific Islander or Hawaiian</TableCell>
                                <TableCell align="right">Undesignated</TableCell>
                                <TableCell align="right">Other</TableCell>
                            </TableRow>
                            </TableHead>
                            <TableBody>
                                <TableRow>
                                <TableCell scope="row">
                                {feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION]}
                                </TableCell>
                                <TableCell align="right">{feature.properties[MapUtilities.PROPERTY_LABELS.WHITE_COUNT]}</TableCell>
                                <TableCell align="right">{feature.properties[MapUtilities.PROPERTY_LABELS.BLACK_COUNT]}</TableCell>
                                <TableCell align="right">{feature.properties[MapUtilities.PROPERTY_LABELS.ASIAN_COUNT]}</TableCell>
                                <TableCell align="right">{feature.properties[MapUtilities.PROPERTY_LABELS.NATIVE_COUNT]}</TableCell>
                                <TableCell align="right">{feature.properties[MapUtilities.PROPERTY_LABELS.PACIFIC_ISLANDER_COUNT]}</TableCell>
                                <TableCell align="right">{feature.properties[MapUtilities.PROPERTY_LABELS.UNDESIGNATED_COUNT]}</TableCell>
                                <TableCell align="right">{feature.properties[MapUtilities.PROPERTY_LABELS.RACE_OTHER_COUNT]}</TableCell>
                                </TableRow>
                            </TableBody>
                        </Table>
                        </TableContainer>
                        <div className="demographicsContainer">
                                <PartyDemographics feature={feature}/>
                                <RacialDemographics feature={feature}/>
                        </div>
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