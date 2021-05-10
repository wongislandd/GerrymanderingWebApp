import React, { Component } from "react";
import InfoIcon from "@material-ui/icons/Info";
import { Button, Modal } from "react-materialize";
import ModalContentContainer from "./modals/ModalContentContainer";

export default class LabelAndInfoIcon extends Component {
  render() {
    return (
      <div className="labelAndIcon">
        <span>{this.props.label}</span>
        <Modal
          actions={[
            <Button flat modal="close" node="button" waves="green">
              Close
            </Button>,
          ]}
          bottomSheet={false}
          fixedFooter={false}
          header={this.props.label}
          id="Modal-0"
          open={false}
          options={{
            dismissible: true,
            endingTop: "10%",
            inDuration: 250,
            onCloseEnd: null,
            onCloseStart: null,
            onOpenEnd: null,
            onOpenStart: null,
            opacity: 0.5,
            outDuration: 250,
            preventScrolling: true,
            startingTop: "4%",
          }}
          trigger={<InfoIcon style={{ fontSize: 22 }} className="infoIcon" />}
        >
          {this.props.description ? 
          <p>{this.props.description}</p> : 
          <ModalContentContainer type={this.props.type} districting={this.props.districting}/>}
        </Modal>
      </div>
    );
  }
}
