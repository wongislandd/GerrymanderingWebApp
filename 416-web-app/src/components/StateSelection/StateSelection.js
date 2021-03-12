import React, { Component } from "react";
import StateSelectionMap from "./StateSelectionMap";
import StateSelectionToolbar from "./StateSelectionToolbar";

export default class StateSelection extends Component {
  render() {
    return (
      <div className="full-screen-flex-container">
        <StateSelectionMap />
        <StateSelectionToolbar />
      </div>
    );
  }
}
