import React, { Component } from 'react'
import {CollapsibleItem, Row, Col} from 'react-materialize'
import SwapVertIcon from "@material-ui/icons/SwapVert"
import { connect } from 'react-redux'
import { setDistrictingSortMethod } from '../../../redux/actions/settingActions'
import * as SelectionMenuUtilities from '../../../utilities/SelectionMenuUtilities'

/* This should be able to change the sort direction and line up with the numbers displayed in the districting item component*/
class SortingCollapsible extends Component {
    // Should we add a time out so you can't spam click?
    handleSortClick(method) {
        if (this.props.DistrictingSortMethod.method == method) {
            if (this.props.DistrictingSortMethod.direction == SelectionMenuUtilities.SORT_DIRECTIONS.DESCENDING) {
                this.props.setDistrictingSortMethod(method, SelectionMenuUtilities.SORT_DIRECTIONS.ASCENDING)
                return;
            }
        }
        // Otherwise, set to the descending version of that method
        this.props.setDistrictingSortMethod(method, SelectionMenuUtilities.SORT_DIRECTIONS.DESCENDING)
    }

    render() {
        return (
          <CollapsibleItem
            className={"districtSummaryCollapsible sortingCollapsible"}
            header={
                    <Row className="ListingColumnsContainer align-items-start">
                    <Col s={2}>
                        <div>ID</div>
                    </Col>
                    <Col s={2}>
                    <Row>OF Score</Row>
                    <Row>
                    <SwapVertIcon 
                    style={{ fontSize: 40 }} className="swapSortOrderIcon" 
                    onClick={() => this.handleSortClick(SelectionMenuUtilities.SORT_METHODS.OBJECTIVE_FUNCTION)}
                    />
                        </Row>
                    </Col>
                    <Col s={3}>
                        <Row>
                    Population Equality
                    </Row>
                    <Row><SwapVertIcon style={{ fontSize: 40 }} className="swapSortOrderIcon" onClick={() => this.handleSortClick(SelectionMenuUtilities.SORT_METHODS.POPULATION_EQUALITY)}/></Row>
                    </Col>
                    <Col s={3}>
                    <Row>Majority Minority Districts</Row>
                    <Row><SwapVertIcon style={{ fontSize: 40 }} className="swapSortOrderIcon" onClick={() => this.handleSortClick(SelectionMenuUtilities.SORT_METHODS.MAJORITY_MINORITY_DISTRICTS)}/></Row>
                    </Col>
                    <Col s={2}>
                        <Row>
                    Split Counties
                    </Row>
                    <Row>
                    <SwapVertIcon style={{ fontSize: 40 }} className="swapSortOrderIcon" onClick={() => this.handleSortClick(SelectionMenuUtilities.SORT_METHODS.SPLIT_COUNTIES)}/>
                        </Row>
                    </Col>
                    </Row>
            }
            onSelect = {(e) => {}}
            >
          </CollapsibleItem>
        )
    }
}


const mapDispatchToProps = (dispatch) => {
    return {
        setDistrictingSortMethod : (method, direction) => {
            dispatch(setDistrictingSortMethod(method, direction));
        },
    };
  };
  
  const mapStateToProps = (state, ownProps) => {
    return {
        DistrictingSortMethod : state.DistrictingSortMethod
    };
  };
  
  export default connect(mapStateToProps, mapDispatchToProps)(SortingCollapsible);
  