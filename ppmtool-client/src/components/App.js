import React from 'react';
import '../css/App.css';
import DashBoard from './DashBoard';
import 'bootstrap/dist/css/bootstrap.min.css'
import Header from './Header';
import {BrowserRouter as Router, Route} from 'react-router-dom'
import AddProject from './AddProject';
import {Provider} from 'react-redux'
import store from '../store'
import UpdateProject from './UpdateProject';


function App() {
  return (
    <Provider store = {store}>
      <Router>
        <div className = "App">
          <Header />
          <Route exact path = "/dashboard" component = {DashBoard}/>
          <Route exact path = "/addProject" component = {AddProject}/>
          <Route exact path = "/updateProject/:id" component = {UpdateProject}/>
        </div>
      </Router>
    </Provider>
  );
}

export default App;
