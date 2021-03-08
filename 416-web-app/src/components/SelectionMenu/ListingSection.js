import React, { Component } from 'react'
import {connect} from 'react-redux'
import {Collapsible, Row, Col, Select} from 'react-materialize'
import DistrictingItem from './DistrictingItem'

class ListingSection extends Component {
    render() {
        return (
            <div className="SelectionMenuSection ListingSection">
                <div className="DistrictingResultsHeader">
                <h5>Districting Results</h5>
                    <Select
                            id="DistrictingSorter"
                            multiple={false}
                            label="Sort Districtings By"
                            options={{
                                dropdownOptions: {
                                autoTrigger: true,
                                closeOnClick: true,
                                constrainWidth: true,
                                coverTrigger: true,
                                hover: false,
                                inDuration: 150,
                                onCloseEnd: null,
                                onCloseStart: null,
                                onOpenEnd: null,
                                onOpenStart: null,
                                outDuration: 250
                                }
                            }}
                            value=""
                            >
                            <option
                                disabled
                                value=""
                            >
                                Stat 1 (descending)
                            </option>
                        </Select>
                </div>
                <Collapsible accordion>
                {this.props.FilteredDistrictings.map((districting, key) => {
                    return(
                        <DistrictingItem key={key} index={key} districting={districting}/>
                    )
                })}
                </Collapsible>
            </div>
        )
    }
}


const mapStateToProps = (state, ownProps) => {
    return {
        FilteredDistrictings : state.FilteredDistrictings,
        SortedBy : state.SortedBy
    }
}
  
export default connect(mapStateToProps)(ListingSection);