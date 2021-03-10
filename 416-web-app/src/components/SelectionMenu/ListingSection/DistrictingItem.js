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
                className={"districtSummaryCollapsible centerWithinMe"}
                header={<div className="districtingItem" onClick={(e)=>{e.stopPropagation()}}>
                            #{this.props.index+1} {this.props.districting.name}
                        </div>}
                onSelect={(e)=>this.handleClick(e)}>
                    <h5 className="padBelowMe">Districting Breakdown</h5>
                    <DistrictingInfoSection districting={this.props.districting}/>
            </CollapsibleItem>
        )
    }
}
