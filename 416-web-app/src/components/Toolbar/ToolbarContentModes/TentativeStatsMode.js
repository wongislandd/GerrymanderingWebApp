import React, { Component } from 'react'
import { connect } from 'react-redux'
import { Row, Collapsible, CollapsibleItem, Table } from 'react-materialize'

class TentativeStatsMode extends Component {
    render() {
        if (this.props.TentativeDistricting == null) {
            return(
                <h5>Your selected districting's stats will be displayed here.</h5>
            )
        } else {
            return (
                <div className="ToolbarContent">
                    <h5>Stat Breakdown</h5>
                    <Row>
                        <Collapsible accordion={false}>
                            {this.props.TentativeDistricting.geoJson.features.map((feature,key) => {
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
                    </Row>
                </div>
            )
        }
    }
}



const mapStateToProps = (state, ownProps) => {
    return {
        TentativeDistricting : state.TentativeDistricting,
    }
  }

export default connect(mapStateToProps)(TentativeStatsMode);
