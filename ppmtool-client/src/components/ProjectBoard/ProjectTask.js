import React, { Component } from "react";
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { deleteProjectTask } from "../../actions/backlogActions";

class ProjectTask extends Component {
    deleteProjectTask = pt_id => () => {
        const { projectIndetrifer } = this.props.projectTask;
        this.props.deleteProjectTask(projectIndetrifer, pt_id);
    };

    render() {
        const { projectTask } = this.props;
        const { projectIndetrifer, projectSequence } = this.props.projectTask;
        let priorityString;
        let priorityClass;

        if (projectTask.priority === 1) {
            priorityString = "HIGH";
            priorityClass = "bg-danger text-light";
        } else if (projectTask.priority === 2) {
            priorityClass = "bg-warning text-light";
            priorityString = "MEDIUM";
        } else {
            priorityClass = "bg-info text-light";
            priorityString = "LOW";
        }

        return (
            <div className="card mb-1 bg-light">
                <div className={`card-header text-primary ${priorityClass}`}>
                    ID: {projectTask.projectSequence} -- Priority: {priorityString}
                </div>
                <div className="card-body bg-light">
                    <h5 className="card-title">{projectTask.summary}</h5>
                    <p className="card-text text-truncate ">{projectTask.acceptanceCriteria}</p>
                    <Link to={`/updateProjectTask/${projectIndetrifer}/${projectSequence}`} className="btn btn-primary">
                        View / Update
                    </Link>
                    <button className="btn btn-danger ml-4" onClick={this.deleteProjectTask(projectTask.projectSequence)}>
                        Delete
                    </button>
                </div>
            </div>
        );
    }
}

ProjectTask.propTypes = {
    deleteProjectTask: PropTypes.func.isRequired
};

export default connect(null, { deleteProjectTask })(ProjectTask);
