import React, { Component } from 'react'
import { connect } from 'react-redux'
import * as MapUtilities from '../../utilities/MapUtilities'
import * as StatUtilities from '../../utilities/StatUtilities'

class PrecinctTooltip extends Component {
    constructor(props) {
        super(props)
    }
    render() {
        const locationToFeature = this.props.FeaturedPrecinct
        if (locationToFeature == null) {
            return (
                <div className="tooltip" style={{left: this.props.MouseX, top: this.props.MouseY}}>
                    <div>{MapUtilities.MESSAGES.NoInfoToDisplayMsg}</div>
                </div>
            )
        } else {
            console.log(locationToFeature.properties)
            return (    
                /* Placeholder for better design */
                <div className="tooltip" style={{left: this.props.MouseX, top: this.props.MouseY}}>
                     <ul>
                        <li><b>Precinct : {locationToFeature.properties[MapUtilities.PROPERTY_LABELS.PRECINCT_NAME]}</b></li>
                        <li>County : {locationToFeature.properties["county_nam"]}</li>
                        <li>Population : {StatUtilities.addCommas(locationToFeature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION])}</li>
                        <li>Democratic : {StatUtilities.formatResult(locationToFeature.properties[MapUtilities.PROPERTY_LABELS.DEMOCRAT_COUNT],locationToFeature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION])}</li>
                        <li>Republican : {StatUtilities.formatResult(locationToFeature.properties[MapUtilities.PROPERTY_LABELS.REPUBLICAN_COUNT],locationToFeature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION])}</li>
                        <li>White Population: {StatUtilities.formatResult(locationToFeature.properties[MapUtilities.PROPERTY_LABELS.WHITE_COUNT],locationToFeature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION])}</li>
                        <li>Black Population: {StatUtilities.formatResult(locationToFeature.properties[MapUtilities.PROPERTY_LABELS.BLACK_COUNT],locationToFeature.properties[MapUtilities.PROPERTY_LABELS.TOTAL_POPULATION])}</li>
                    </ul>
                </div>
            )
        }
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        DisplayDistricts : state.DisplayDistricts,
        DisplayPrecincts : state.DisplayPrecincts,
        FeaturedDistrict : state.FeaturedDistrict,
        FeaturedPrecinct : state.FeaturedPrecinct,
        MouseX : state.MouseX,
        MouseY : state.MouseY,
        MapRef : state.MapRef
    }
  }
  
  export default connect(mapStateToProps)(PrecinctTooltip);