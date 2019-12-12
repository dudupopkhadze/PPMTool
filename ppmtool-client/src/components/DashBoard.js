import React, { Component } from "react";
import ProjectItem from "./ProjectItem";
import CreateProjectButton from "./CreateProjectButton";
import { connect } from "react-redux";
import { getProjects } from "../actions/projectActions";
import PropTypes from "prop-types";

class DashBoard extends Component {
    componentDidMount() {
        this.props.getProjects();
    }

    render() {
        const { project } = this.props;
        return (
            <div className="projects">
                <div className="container">
                    <div className="row">
                        <div className="col-md-12">
                            <h1 className="display-4 text-center">PROJECTS</h1>
                            <br />
                            <CreateProjectButton />
                            <br />
                            <hr />
                            {project.projects.map(cur => {
                                return <ProjectItem project={cur} key={cur.id} />;
                            })}
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

DashBoard.propTypes = {
    project: PropTypes.object.isRequired,
    getProjects: PropTypes.func.isRequired
};

const MapStateToProps = state => {
    return { project: state.project };
};

export default connect(MapStateToProps, { getProjects })(DashBoard);
