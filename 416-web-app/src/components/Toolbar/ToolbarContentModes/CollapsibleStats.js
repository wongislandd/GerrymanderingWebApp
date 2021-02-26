import React, { Component } from 'react'
import { Collapsible, CollapsibleItem, Table } from 'react-materialize'

function CollapsibleStats(props){
    return (
        <Collapsible accordion={false}>
            {props.DistrictingToDisplay.geoJson.features.map((feature,key) => {
                    console.log(Object.keys(feature.properties))
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
                                return <th data-field={jsonProperty}>{jsonProperty}</th>
                            })}
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            {Object.keys(feature.properties).map((jsonProperty, key) => {
                                return <td>{feature.properties[jsonProperty]}</td>
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