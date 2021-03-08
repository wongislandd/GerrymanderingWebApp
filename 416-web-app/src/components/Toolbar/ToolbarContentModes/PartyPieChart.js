import React, { Component } from 'react'
import * as MapUtilities from '../../../utilities/MapUtilities'
import {Chart} from 'react-google-charts'

export default class PartyPieChart extends Component {
    constructor(props){
        super(props)
    }

    render() {
        return (
            <Chart
                width={'400px'}
                height={'200px'}
                chartType="PieChart"
                data={[
                    ['Party', 'Voters'],
                    ['Democratic', this.props.feature.properties[MapUtilities.PROPERTY_LABELS.DEMOCRAT_COUNT]],
                    ['Republican', this.props.feature.properties[MapUtilities.PROPERTY_LABELS.REPUBLICAN_COUNT]],
                    ['Other', this.props.feature.properties[MapUtilities.PROPERTY_LABELS.PARTY_OTHER_COUNT]]
                ]}
                options={{
                    title : 'Party Demographics',
                    pieSliceText : 'none',
                    slices: {
                        0 : {color : 'blue'},
                        1 : {color : 'red'},
                        2 : {color : 'purple'},
                    },
                    pieHole : 0.2
                }}
            />
        )
    }
}
