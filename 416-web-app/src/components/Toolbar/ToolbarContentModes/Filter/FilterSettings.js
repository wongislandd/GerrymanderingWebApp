import React, { Component } from 'react'
import { connect } from 'react-redux'
import { Range, Row } from 'react-materialize'
import { Slider } from '@material-ui/core'
import { updateFilterSettings } from '../../../../redux/actions/settingActions'

class FilterSettings extends Component {
    render() {
        return (
            <div className="ToolbarContent settings-sliders">
                  {Object.keys(this.props.FilterSettings).map((key) => {
                      let filter = this.props.FilterSettings[key]
                      if(!Array.isArray(filter.value)) {
                        return(
                            <Row key={key}>
                                <h5>{filter.name}</h5>
                                <Slider
                                onChange={(e,newValue)=>this.props.updateFilterSettings(key, newValue)}
                                value={filter.value}
                                max={filter.maxVal}
                                min={filter.minVal}
                                name={filter.name}
                                step={filter.step}
                                marks
                                valueLabelDisplay="auto"/>
                            </Row>
                        )
                      } else { // Else it's for a range slider, ex. The objective function 
                      return(
                        <Row key={key}>
                            <h5>{filter.name}</h5>
                            <Slider
                            onChange={(e,newValue)=>this.props.updateFilterSettings(key, newValue)}
                            value={filter.value}
                            aria-labelledby="range-slider"
                            name={filter.name}
                            marks
                            max={filter.maxVal}
                            min={filter.minVal}
                            step={filter.step}
                            valueLabelDisplay="auto"/>
                        </Row>
                    )
                      }
                })}
            </div>
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        updateFilterSettings : (key, newVal) => {dispatch(updateFilterSettings(key, newVal))}
    }
  }

const mapStateToProps = (state, ownProps) => {
    return {
        FilterSettings : state.FilterSettings
    }
  }
  
export default connect(mapStateToProps, mapDispatchToProps)(FilterSettings);