import React, { Component } from 'react'
import { Col, Row } from 'react-materialize'
import FilterSection from './FilterSection'
import ListingSection from './ListingSection'
import CompareSection from './CompareSection'


export default class SelectionMenuContainer extends Component {
    render() {
        return (
            <div className="SelectionMenuContainer">
                <FilterSection/>
                <ListingSection/>
                <CompareSection/>
            </div>
        )
    }
}
