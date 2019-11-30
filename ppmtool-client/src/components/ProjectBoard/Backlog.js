import React, { Component } from "react";
import ProjectTask from "./ProjectTask";

class Backlog extends Component {
  render() {
    const { backlog } = this.props;

    const tasks = backlog.project_tasks.map(cur => (
      <ProjectTask projectTask={cur} key={cur.projectSequence} />
    ));

    let todo = [];
    let inpr = [];
    let done = [];

    for (let i = 0; i < tasks.length; i++) {
      const elementStatus = tasks[i].props.projectTask.status;
      if (elementStatus === "TO_DO") {
        todo.push(tasks[i]);
      } else if (elementStatus === "DONE") {
        done.push(tasks[i]);
      } else inpr.push(tasks[i]);
    }

    return (
      <div className="container">
        <div className="row">
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-secondary text-white">
                <h3>TO DO</h3>
              </div>
            </div>
            {todo}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-primary text-white">
                <h3>In Progress</h3>
              </div>
            </div>
            {inpr}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-success text-white">
                <h3>Done</h3>
              </div>
            </div>
            {done}
          </div>
        </div>
      </div>
    );
  }
}

export default Backlog;
