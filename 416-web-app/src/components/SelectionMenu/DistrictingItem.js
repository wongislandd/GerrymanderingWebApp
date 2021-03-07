import React, { Component } from 'react'
import { CollapsibleItem } from 'react-materialize'
import DistrictingInfoSection from './DistrictingInfoSection'

export default class DistrictingItem extends Component {
    /* Pass this component a districting and it'll do the rest. */
    constructor(props) {
        super(props)
    }

    handleClick(event) {

    }

    render() {
        return (
            <CollapsibleItem
                header={"#" + (this.props.index+1) + " " + this.props.districting.name}
                onSelect={(e)=>this.handleClick(e)}>
                    <DistrictingInfoSection districting={this.props.districting}/>
            </CollapsibleItem>
        )
    }
}
