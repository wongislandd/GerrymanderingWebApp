import React, { Component } from 'react'
import { Collapsible, CollapsibleItem, Table } from 'react-materialize'

function CollapsibleStats(props){
    return (
        <Collapsible className="stat-window" accordion={false}>
            <CollapsibleItem
                expanded={true}
                key={-1}
                header={"Districting Summary"}
            >
                Summary info goes in here.
            </CollapsibleItem>
            {props.DistrictingToDisplay.geoJson.features.map((feature,key) => {
                    return(
                    <CollapsibleItem 
                    expanded={false}
                    key={key}
                    header={"District " + (key+1)}
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

export default CollapsibleStats