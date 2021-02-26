import React, { Component } from 'react'
import { connect } from 'react-redux'
import * as MapUtilities from '../../utilities/MapUtilities'

class TooltipComponent extends Component {
    constructor(props) {
        super(props)
    }
    render() {
        const locationToFeature = this.props.DisplayDistricts ? this.props.FeaturedDistrict : this.props.DisplayPrecincts ? this.props.FeaturedPrecinct : null
        if (locationToFeature == null) {
            return (
                <div className="tooltip" style={{left: this.props.MouseX, top: this.props.MouseY}}>
                    <div>{MapUtilities.MESSAGES.NoInfoToDisplayMsg}</div>
                </div>
            )
        } else {
            /* There is a feature to display */
            return (
                /* Placeholder for better design */
                <div className="tooltip" style={{left: this.props.MouseX, top: this.props.MouseY}}>
                    <div>{JSON.stringify(locationToFeature.properties)}</div>
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
  
  export default connect(mapStateToProps)(TooltipComponent);