import React, { Component } from 'react'
import { Button, Select, Row, Col } from 'react-materialize'
import { connect } from 'react-redux'
import { setTentativeDistricting, setCurrentDistricting } from '../../../redux/actions/settingActions'
import TentativeMapPreview from './TentativeMapPreview'

class HistoryMode extends Component {
    render() {
        return (
            <div className="ToolbarContent">
                <h5>Districting History</h5>
                <Row>
                    <Select
                        id="DistrictSelector"
                        multiple={false}
                        onChange={(e) => {
                            this.props.setTentativeDistricting(JSON.parse(e.target.value))}}
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
                            Choose a Districting
                        </option>
                        {this.props.DistrictingHistory.map((districting, key) => {

                            return <option key={key} value={JSON.stringify(districting)}>{districting.name}</option>
                        })}
                        </Select>
                    </Row>
                    <Row>
                        <TentativeMapPreview/>
                    </Row>
                    <Row>
                        <Button 
                        className="confirmButton"
                        disabled={this.props.TentativeDistricting == undefined} 
                        onClick={(e)=>this.props.setCurrentDistricting(this.props.TentativeDistricting)}
                        >
                        {this.props.TentativeDistricting != null ? "Load " + this.props.TentativeDistricting.name : "Select a Districting"}
                        </Button>
                    </Row>
            </div>
        )
    }   
}


const mapDispatchToProps = (dispatch) => {
    return {
        setTentativeDistricting : (districting) => {dispatch(setTentativeDistricting(districting))},
        setCurrentDistricting : (districting) => {dispatch(setCurrentDistricting(districting))}
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        DistrictingHistory : state.DistrictingHistory,
        TentativeDistricting : state.TentativeDistricting,
        CurrentDistricting : state.CurrentDistricting
    }
  }

export default connect(mapStateToProps, mapDispatchToProps)(HistoryMode);
