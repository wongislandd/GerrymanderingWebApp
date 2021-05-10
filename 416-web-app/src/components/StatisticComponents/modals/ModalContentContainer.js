import React, { Component } from 'react'
import DeviationModal from './DeviationModal'
import EqualPopulationModal from './EqualPopulationModal'
import MajorityMinorityModal from './MajorityMinorityModal'

export default class ModalContentContainer extends Component {
    render() {
        switch(this.props.type) {
            case "POPULATION":
                return <EqualPopulationModal districting={this.props.districting}/>
            case "MAJORITY_MINORITY":
                return <MajorityMinorityModal districting={this.props.districting}/>
            case "DEVIATION_AVG":
                return <DeviationModal type="avg" districting={this.props.districting}/>
            case "DEVIATION_ENACTED":
                return <DeviationModal type="enacted" districting={this.props.districting}/>
            default:
                return <MajorityMinorityModal districting={this.props.districting}/>
        }
    }
}
