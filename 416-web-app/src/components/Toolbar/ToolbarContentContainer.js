import React, { Component } from "react";
import DynamicToolbarContent from "./DynamicToolbarContent";

/**
 * ToolbarContentContainer
 * Wrapper around DynamicToolbarContent
 */
export default class ToolbarContentContainer extends Component {
  constructor(props) {
    super(props);
  }
  /* Conditionally rendering the bottom allows you to have the full size to one mode if you want */
  render() {
    if (this.props.bottomMode != undefined) {
      return (
        <div className="toolbar-content-container">
          <div className="toolbar-content-top">
            <DynamicToolbarContent mode={this.props.topMode} />
          </div>
          <div className="toolbar-content-bottom">
            <DynamicToolbarContent mode={this.props.bottomMode} />
          </div>
        </div>
      );
    } else {
      return (
        <div className="toolbar-content-container">
          <div className="toolbar-content-full">
            <DynamicToolbarContent mode={this.props.topMode} />
          </div>
        </div>
      );
    }
  }
}
