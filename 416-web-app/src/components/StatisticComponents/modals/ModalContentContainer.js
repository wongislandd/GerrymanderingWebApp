import React, { Component } from 'react'
import MajorityMinorityModal from './MajorityMinorityModal'

export default class ModalContentContainer extends Component {
    render() {
        switch(this.props.type) {
            case "MAJORITY_MINORITY":
                return <MajorityMinorityModal districting={this.props.districting}/>
            default:
                return <MajorityMinorityModal districting={this.props.districting}/>
        }
    }
}
