import React, { Component } from 'react'
import { Collapsible, CollapsibleItem} from 'react-materialize'
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper} from '@material-ui/core'
import { connect } from 'react-redux'
import {addFeatureToHighlight, removeFeatureHighlighting, setFeaturedDistrict, setFeaturedPrecinct, setStatShowcasedDistrictID} from '../../redux/actions/settingActions'
import * as MapUtilities from '../../utilities/MapUtilities'
import * as StatUtilities from '../../utilities/StatUtilities'
import PartyPieChart from './PartyPieChart'
import RacialPieChart from './RacialPieChart'
import LabelAndInfoIcon from './LabelAndInfoIcon'


class DistrictingSummary extends Component{
    constructor(props){
        super(props)
    }

    /* This guarentees that only the StatShowcasedDistrictID is open. This is a fix to some weird issues
    where a collapsible item would be technically closed (not active) but still had styling as if it
    were open */
    closeAllCollapsibles() {
        var collapsible = document.getElementById("stat-collapsible")
        if (collapsible != null) {
            var items = collapsible.getElementsByTagName("li");
            for (var i=0; i< items.length; ++i) {
                var collapsiblebodies = items[i].getElementsByClassName("collapsible-body")
                for (var j=0; j< collapsiblebodies.length; ++j) {
                    collapsiblebodies[j].removeAttribute("style")
                }
            }
        }  
    }

    render() {
        this.closeAllCollapsibles()
        return (
            <Collapsible className="stat-window" id="stat-collapsible" accordion={false}>
                <CollapsibleItem
                    expanded={false}
                    key={-1}
                    header={"Objective Function Details"}
                >
                    <TableContainer component={Paper}>
                        <Table aria-label="simple table">
                            <TableHead>
                            <TableRow>
                                <TableCell>
                                <LabelAndInfoIcon
                                 label="Population Equality"
                                 description={StatUtilities.DESCRIPTIONS.POPULATION_EQUALITY}
                                 />
                                </TableCell>
                                <TableCell>
                                <LabelAndInfoIcon
                                 label="Split Counties"
                                 description={StatUtilities.DESCRIPTIONS.SPLIT_COUNTIES}
                                 />
                                </TableCell>
                                <TableCell>
                                <LabelAndInfoIcon
                                 label="Deviaton from Average"
                                 description={StatUtilities.DESCRIPTIONS.DEVIATION_FROM_AVERAGE}
                                 />
                                </TableCell>
                                <TableCell>
                                <LabelAndInfoIcon
                                 label="Deviation from Enacted"
                                 description={StatUtilities.DESCRIPTIONS.DEVIATION_FROM_ENACTED}
                                 />
                                </TableCell>
                                <TableCell>
                                <LabelAndInfoIcon
                                 label="Compactness"
                                 description={StatUtilities.DESCRIPTIONS.COMPACTNESS}
                                 />
                                </TableCell>
                            </TableRow>
                            </TableHead>
                            <TableBody>
                                <TableRow>
                                    <TableCell scope="row">
                                        {this.props.DistrictingToDisplay.geoJson.objectivefunc[MapUtilities.PROPERTY_LABELS.POPULATION_EQUALITY]}
                                    </TableCell>
                                    <TableCell>    
                                        {this.props.DistrictingToDisplay.geoJson.objectivefunc[MapUtilities.PROPERTY_LABELS.SPLIT_COUNTIES]}               
                                    </TableCell>
                                    <TableCell>          
                                        {this.props.DistrictingToDisplay.geoJson.objectivefunc[MapUtilities.PROPERTY_LABELS.DEVIATION_FROM_AVG]}         
                                    </TableCell>
                                    <TableCell>
                                        {this.props.DistrictingToDisplay.geoJson.objectivefunc[MapUtilities.PROPERTY_LABELS.DEVIATION_FROM_ENACTED]}
                                    </TableCell>
                                    <TableCell>
                                        {this.props.DistrictingToDisplay.geoJson.objectivefunc[MapUtilities.PROPERTY_LABELS.COMPACTNESS]}
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
                        onClick={(e)=> {
                            /* If they close the already open one. */
                            if (key == this.props.StatShowcasedDistrictID) {
                                this.props.setStatShowcasedDistrictID(null)
                            } else {
                                this.props.setStatShowcasedDistrictID(key)
                            }
                        }}
                        expanded={this.props.StatShowcasedDistrictID == null ? false : this.props.StatShowcasedDistrictID == key}
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
                                <TableCell>Democratic Affiliated</TableCell>
                                <TableCell>Republican Affiliated</TableCell>
                                <TableCell>Other Affiliations</TableCell>
                            </TableRow>
                            </TableHead>
                            <TableBody>
                                <TableRow>
                                <TableCell scope="row">
                                {StatUtilities.addCommas(feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_VOTER_COUNT])}
                                </TableCell>
                                <TableCell>
                                    {StatUtilities.formatResult(feature.properties[MapUtilities.PROPERTY_LABELS.DEMOCRAT_COUNT],feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_VOTER_COUNT])}                                    
                                </TableCell>
                                <TableCell>
                                    {StatUtilities.formatResult(feature.properties[MapUtilities.PROPERTY_LABELS.REPUBLICAN_COUNT],feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_VOTER_COUNT])}                                    
                                 </TableCell>
                                <TableCell>
                                {StatUtilities.formatResult(feature.properties[MapUtilities.PROPERTY_LABELS.PARTY_OTHER_COUNT],feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_VOTER_COUNT])}                                    
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
                                {StatUtilities.addCommas(feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION])}
                                </TableCell>
                                <TableCell align="right">
                                    {StatUtilities.formatResult(feature.properties[MapUtilities.PROPERTY_LABELS.WHITE_COUNT], feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION])}
                                    </TableCell>
                                <TableCell align="right">
                                    {StatUtilities.formatResult(feature.properties[MapUtilities.PROPERTY_LABELS.BLACK_COUNT], feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION])}
                                </TableCell>
                                <TableCell align="right">
                                    {StatUtilities.formatResult(feature.properties[MapUtilities.PROPERTY_LABELS.ASIAN_COUNT], feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION])}
                                     </TableCell>
                                <TableCell align="right">
                                 {StatUtilities.formatResult(feature.properties[MapUtilities.PROPERTY_LABELS.NATIVE_COUNT], feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION])}
                                   </TableCell>
                                <TableCell align="right">
                                    {StatUtilities.formatResult(feature.properties[MapUtilities.PROPERTY_LABELS.PACIFIC_ISLANDER_COUNT], feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION])}
                                     </TableCell>
                                <TableCell align="right">
                                    {StatUtilities.formatResult(feature.properties[MapUtilities.PROPERTY_LABELS.UNDESIGNATED_COUNT], feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION])}
                                   </TableCell>
                                <TableCell align="right">
                                    {StatUtilities.formatResult(feature.properties[MapUtilities.PROPERTY_LABELS.RACE_OTHER_COUNT], feature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION])}
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
        removeFeatureHighlighting : (feature) => {dispatch(removeFeatureHighlighting(feature))},
        setStatShowcasedDistrictID : (districtID) => {dispatch(setStatShowcasedDistrictID(districtID))},
    }
  }
  
  const mapStateToProps = (state, ownProps) => {
    return {
        DisplayPrecincts : state.DisplayPrecincts,
        DisplayDistricts : state.DisplayDistricts,
        StatShowcasedDistrictID : state.StatShowcasedDistrictID,
    }
  }
  
  export default connect(mapStateToProps, mapDispatchToProps)(DistrictingSummary);