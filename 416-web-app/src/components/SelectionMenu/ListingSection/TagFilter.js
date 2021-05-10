import React, { Component } from 'react'
import { connect } from 'react-redux'
import { Row, Col} from 'react-materialize'
import { updateSelectedTags } from '../../../redux/actions/settingActions';
import { Chip } from '@material-ui/core'
import * as SelectionMenuUtilities from '../../../utilities/SelectionMenuUtilities'

class TagFilter extends Component {
    keyIsSelected(key) {
        if (this.props.SelectedTags.includes(key)) {
            return true;
        } else {
            return false;
        }
    } 

    render() {
        return (
            <div>
            <Row>
                {Object.keys(SelectionMenuUtilities.TAGS).map((tag) => {
                    return(
                    <Col
                    key={tag}>
                    <Chip 
                    className="tag"
                    clickable
                    value={tag}
                    onClick = {()=>this.props.updateSelectedTags(tag)}
                    variant={this.keyIsSelected(tag) ? "default" : "outlined"} 
                    label={SelectionMenuUtilities.TAGS[tag]}
                    color= "primary"/>
                    </Col>
                    )
                })
                }
            </Row>
            </div>
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
      updateSelectedTags : (tag) => {
          dispatch(updateSelectedTags(tag))
      }
    };
  };
  
  const mapStateToProps = (state, ownProps) => {
    return {
      SelectedTags : state.SelectedTags
    };
  };
  
  export default connect(mapStateToProps, mapDispatchToProps)(TagFilter);
  