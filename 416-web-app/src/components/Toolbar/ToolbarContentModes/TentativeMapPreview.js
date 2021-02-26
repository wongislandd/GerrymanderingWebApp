import React, { Component } from 'react'
import ReactMapGL, { Layer, Source } from "react-map-gl"
import { connect } from 'react-redux'
import * as MapUtilities from '../../../utilities/MapUtilities'

function TentativeMapPreview(props) {
    const viewport = {
        latitude : MapUtilities.NC.LATTITUDE, 
        longitude: MapUtilities.NC.LONGITUDE,
        width: "400px",
        height: "200px",
        zoom: 4.8
    }
    if (props.TentativeDistricting == null) {
        return (
            <div>
            <h6 className="title-text">Preview</h6>
            <ReactMapGL 
                    className = "map-display"
                    {...viewport} 
                    mapboxApiAccessToken = {process.env.REACT_APP_MAPBOX_TOKEN}
                >
                </ReactMapGL>
            </div>
        )
    }
    else {
        return (
            <div>
            <h6 className="title-text">Preview</h6>
            <ReactMapGL 
                    className = "map-display"
                    {...viewport} 
                    mapboxApiAccessToken = {process.env.REACT_APP_MAPBOX_TOKEN}
                >
            <Source
                id = {MapUtilities.IDs.DISTRICT_SOURCE_ID}
                type = "geojson"
                data =  {props.TentativeDistricting.geoJson}/>
            <Layer
                id = {MapUtilities.IDs.DISTRICT_FILL_LAYER_ID}
                type="fill"
                source={MapUtilities.IDs.DISTRICT_SOURCE_ID}
                paint={{
                "fill-color" : ["rgb",["get","rgb-R"], ["get","rgb-G"], ["get","rgb-B"]],
                "fill-opacity": .5
                }}/>
            <Layer
                id = {MapUtilities.IDs.DISTRICT_LINE_LAYER_ID}
                type = "line"
                source={MapUtilities.IDs.DISTRICT_SOURCE_ID}
                paint={{
                    "line-opacity": 1
                }}/>
                </ReactMapGL>
            </div>
        )
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        TentativeDistricting : state.TentativeDistricting
    }
  }

export default connect(mapStateToProps)(TentativeMapPreview)