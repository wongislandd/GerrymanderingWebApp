import React, { Component } from 'react'
import { Button, Select, Row, Col } from 'react-materialize'
import { connect } from 'react-redux'
import { setTentativeDistricting, setCurrentDistricting } from '../../../redux/actions/settingActions'

class HistoryMode extends Component {
    render() {
        console.log(this.props)
        return (
            <div className="ToolbarContent">
                <Row>
                <Select
                    id="DistrictSelector"
                    multiple={false}
                    onChange={(e) => {
                        this.props.setTentativeDistricting(JSON.parse(e.target.value))}}
                    options={{
                        classes: '',
                        dropdownOptions: {
                        alignment: 'left',
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
                        <Button>{this.props.TentativeDistricting != null ? "Load " + this.props.TentativeDistricting.name : "Select a Districting"}</Button>
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
        TentativeDistricting : state.TentativeDistricting
    }
  }

export default connect(mapStateToProps, mapDispatchToProps)(HistoryMode);
