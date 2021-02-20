import React, { Component } from 'react'
import DynamicToolbarContent from './DynamicToolbarContent'


/**
 * ToolbarContentContainer
 * Wrapper around DynamicToolbarContent 
 */
export default class ToolbarContentContainer extends Component {
    constructor(props) {
        super(props)
    }


    render() {
        return (
            <div className = "toolbar-content-container">
                <div className="toolbar-content-top">
                    <DynamicToolbarContent mode={this.props.topMode}/>
                    </div>
                <div className="toolbar-content-bottom">
                    <DynamicToolbarContent mode={this.props.bottomMode}/>
                    </div>
            </div>
        )
    }
}
