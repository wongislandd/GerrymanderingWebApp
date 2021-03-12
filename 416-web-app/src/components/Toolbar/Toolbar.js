import React, { Component } from "react";
import ToolbarTabsManager from "./ToolbarTabsManager";

export default class Toolbar extends Component {
  render() {
    return (
      <div className="toolbar">
        <ToolbarTabsManager />
      </div>
    );
  }
}
