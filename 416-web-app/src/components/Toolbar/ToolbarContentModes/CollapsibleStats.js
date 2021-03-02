import React, { Component } from 'react'
import { Collapsible, CollapsibleItem, Table } from 'react-materialize'
import { connect } from 'react-redux'
import {setFeaturedDistrict, setFeaturedPrecinct} from '../../../redux/actions/settingActions'
import * as MapUtilities from '../../../utilities/MapUtilities'

class CollapsibleStats extends Component{
    constructor(props){
        super(props)
    }

    render() {
        return (
            <Collapsible className="stat-window" accordion={false}>
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
                        expanded={false}
                        key={key}
                        header={"District " + (key+1)}
                        style={{
                            backgroundColor: "rgba(" + feature.properties["rgb-R"] + "," + feature.properties["rgb-G"] + "," + feature.properties["rgb-B"] + "," + MapUtilities.VALUES.UNHIGHLIGHTED_DISTRICT_OPACITY + ")",
                        }}
                        >
                        <Table>
                        <thead>
                            <tr>
                                {Object.keys(feature.properties).map((jsonProperty, key) => {
                                    return <th key = {key} data-field={jsonProperty}>{jsonProperty}</th>
                                })}
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                {Object.keys(feature.properties).map((jsonProperty, key) => {
                                    return <td key = {key}>{feature.properties[jsonProperty]}</td>
                                })}
                            </tr>
                        </tbody>
                        </Table>
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