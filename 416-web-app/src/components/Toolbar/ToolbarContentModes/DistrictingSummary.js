import React, { Component } from 'react'
import { Collapsible, CollapsibleItem} from 'react-materialize'
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper} from '@material-ui/core'
import { connect } from 'react-redux'
import {addFeatureToHighlight, removeFeatureHighlighting, setFeaturedDistrict, setFeaturedPrecinct} from '../../../redux/actions/settingActions'
import * as MapUtilities from '../../../utilities/MapUtilities'
import PartyPieChart from './PartyPieChart'
import RacialPieChart from './RacialPieChart'


class DistrictingSummary extends Component{
    constructor(props){
        super(props)
    }

    percentage(partialValue, totalValue) {
        return (100 * partialValue) / totalValue
    }    

    formatResult(partialValue, totalValue) {
        return partialValue + " ("+ (Math.round(this.percentage(partialValue, totalValue) * 10)/10) +"%)"
    }

    render() {
        return (
            <Collapsible className="stat-window" accordion>
                <CollapsibleItem
                    expanded={false}
                    key={-1}
                    header={"Objective Function Details"}
                >
                    <TableContainer component={Paper}>
                        <Table aria-label="simple table">
                            <TableHead>
                            <TableRow>
                                <TableCell align="left">Population Equality</TableCell>
                                <TableCell align="right">Split Counties</TableCell>
                                <TableCell align="right">Compactness</TableCell>
                                <TableCell align="right">Deviation from Average</TableCell>
                                <TableCell align="right">Deviation from Enacted</TableCell>
                            </TableRow>
                            </TableHead>
                            <TableBody>
                                <TableRow>
                                    <TableCell scope="row">
                                        {this.props.DistrictingToDisplay.geoJson.objectivefunc['POPULATION_EQUALITY']}
                                    </TableCell>
                                    <TableCell align="right">    
                                        {this.props.DistrictingToDisplay.geoJson.objectivefunc['SPLIT_COUNTIES']}               
                                    </TableCell>
                                    <TableCell align="right">          
                                        {this.props.DistrictingToDisplay.geoJson.objectivefunc['DEVIATION_FROM_AVG_DISTRICTING']}         
                                    </TableCell>
                                    <TableCell align="right">
                                        {this.props.DistrictingToDisplay.geoJson.objectivefunc['DEVIATION_FROM_ENACTED_PLAN']}
                                    </TableCell>
                                    <TableCell align="right">
                                        {this.props.DistrictingToDisplay.geoJson.objectivefunc['COMPACTNESS']}
                                    </TableCell>
                                </TableRow>
                            </TableBody>
                        </Table>
                        </TableContainer>
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
                                <TableCell align="right">
                                    {this.formatResult(feature.properties[MapUtilities.PROPERTY_LABELS.DEMOCRAT_COUNT],feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_VOTER_COUNT])}                                    
                                </TableCell>
                                <TableCell align="right">
                                    {this.formatResult(feature.properties[MapUtilities.PROPERTY_LABELS.REPUBLICAN_COUNT],feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_VOTER_COUNT])}                                    
                                 </TableCell>
                                <TableCell align="right">
                                {this.formatResult(feature.properties[MapUtilities.PROPERTY_LABELS.PARTY_OTHER_COUNT],feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_VOTER_COUNT])}                                    
                                    </TableCell>
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
                                <TableCell align="right">
                                    {this.formatResult(feature.properties[MapUtilities.PROPERTY_LABELS.WHITE_COUNT], feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION])}
                                    </TableCell>
                                <TableCell align="right">
                                    {this.formatResult(feature.properties[MapUtilities.PROPERTY_LABELS.BLACK_COUNT], feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION])}
                                </TableCell>
                                <TableCell align="right">
                                    {this.formatResult(feature.properties[MapUtilities.PROPERTY_LABELS.ASIAN_COUNT], feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION])}
                                     </TableCell>
                                <TableCell align="right">
                                 {this.formatResult(feature.properties[MapUtilities.PROPERTY_LABELS.NATIVE_COUNT], feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION])}
                                   </TableCell>
                                <TableCell align="right">
                                    {this.formatResult(feature.properties[MapUtilities.PROPERTY_LABELS.PACIFIC_ISLANDER_COUNT], feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION])}
                                     </TableCell>
                                <TableCell align="right">
                                    {this.formatResult(feature.properties[MapUtilities.PROPERTY_LABELS.UNDESIGNATED_COUNT], feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION])}
                                   </TableCell>
                                <TableCell align="right">
                                    {this.formatResult(feature.properties[MapUtilities.PROPERTY_LABELS.RACE_OTHER_COUNT], feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION])}
                                     </TableCell>
                                </TableRow>
                            </TableBody>
                        </Table>
                        </TableContainer>
                        <div className="demographicsContainer">
                                <PartyPieChart feature={feature}/>
                                <RacialPieChart feature={feature}/>
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
  
  export default connect(mapStateToProps, mapDispatchToProps)(DistrictingSummary);