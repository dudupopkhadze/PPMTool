import React, { Component } from "react";
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { getBacklog } from "../../actions/backlogActions";
import Backlog from "./Backlog";

class ProjectBoard extends Component {
  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.getBacklog(id);
  }

  render() {
    const { id } = this.props.match.params;
    const { backlog, errors } = this.props;

    const boardContent = (errors, backlog) => {
      if (backlog && backlog.project_tasks.length < 1) {
        if (errors.projectNotFound) {
          return (
            <div className="alert alert-danger text-center" role="alert">
              {errors.projectNotFound}
            </div>
          );
        } else {
          return (
            <div className="alert alert-info text-center" role="alert">
              No Project Tasks On This Board
            </div>
          );
        }
      } else {
        return(
          backlog && <Backlog backlog={backlog} />          
        )
      }
    };

    let boardCont = boardContent(errors,backlog)

    return (
      <div className="container">
        <Link to={`/addProjectTask/${id}`} className="btn btn-primary mb-3">
          <i className="fas fa-plus-circle"> Create Project Task</i>
        </Link>
        <br />
        <hr />
        {boardCont}
      </div>
    );
  }
}

ProjectBoard.propTypes = {
  getBacklog: PropTypes.func.isRequired,
  backlog: PropTypes.object.isRequired,
  errors: PropTypes.object.isRequired
};

const MapStateToProprs = state => {
  return {
    backlog: state.backlog,
    errors: state.errors
  };
};

export default connect(MapStateToProprs, { getBacklog })(ProjectBoard);
