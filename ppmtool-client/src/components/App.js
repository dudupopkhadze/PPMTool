import React from "react";
import "../css/App.css";
import DashBoard from "./DashBoard";
import "bootstrap/dist/css/bootstrap.min.css";
import Header from "./Header";
import { BrowserRouter as Router, Route, Switch} from "react-router-dom";
import AddProject from "./AddProject";
import { Provider } from "react-redux";
import store from "../store";
import UpdateProject from "./UpdateProject";
import ProjectBoard from "./ProjectBoard/ProjectBoard";
import AddProjectTask from "./ProjectBoard/AddProjectTask";
import UpdateProjectTask from "./ProjectBoard/UpdateProjectTask";
import Landing from "./Landing";
import Register from "./UserManagemant/Register";
import Login from "./UserManagemant/Login";
import { SET_CURRENT_USER } from "../actions/types";
import jwt_decode from "jwt-decode";
import setJWTToken from "../securityUtils/setJWTToken";
import { logOut } from "../actions/securityActions";
import SecureRoute from "../securityUtils/SecureRoute";


const jwtToken = localStorage.jwtToken;

if (jwtToken) {
    setJWTToken(jwtToken);
    const decoded = jwt_decode(jwtToken);
    store.dispatch({
        type: SET_CURRENT_USER,
        payload: decoded
    });

    const curTime = Date.now() / 1000;
    if (decoded.exp < curTime) {
        store.dispatch(logOut());
        window.location.href = "/";
    }
}

function App() {
    return (
        <Provider store={store}>
            <Router>
                <div className="App">
                    <Header />

                    <Route exact path="/" component={Landing} />
                    <Route exact path="/register" component={Register} />
                    <Route exact path="/login" component={Login} />

                    <Switch>
                    <SecureRoute exact path="/dashboard" component={DashBoard} />
                    <SecureRoute exact path="/addProject" component={AddProject} />
                    <SecureRoute exact path="/updateProject/:id" component={UpdateProject} />
                    <SecureRoute exact path="/projectBoard/:id" component={ProjectBoard} />
                    <SecureRoute exact path="/addProjectTask/:id" component={AddProjectTask} />
                    <SecureRoute exact path="/updateProjectTask/:backlog_id/:pt_id" component={UpdateProjectTask} />
                    </Switch>
                </div>
            </Router>
        </Provider>
    );
}

export default App;
