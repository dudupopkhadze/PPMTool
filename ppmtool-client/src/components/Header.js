import React, { Component } from "react";
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { logOut } from "../actions/securityActions";

class Header extends Component {

    logOut = () =>{
        this.props.logOut();
        window.location.href = "/"
    }

    render() {
        const { validToken, user } = this.props.security;

        const userIsAuthenticated = (
            <div className="collapse navbar-collapse" id="mobile-nav">
                <ul className="navbar-nav mr-auto">
                    <li className="nav-item">
                        <Link className="nav-link" to={"/dashboard"}>
                            Dashboard
                        </Link>
                    </li>
                </ul>

                <ul className="navbar-nav ml-auto">
                    <li className="nav-item">
                        <Link className="nav-link " to={"/dashboard"}>
                            <i className = "fas fa-user-circle mr-1"> {user.fullName} </i>
                        </Link>
                    </li>
                    <li className="nav-item">
                        <Link  onClick = {this.logOut}className="nav-link" to={"/"}>
                            Log Out
                        </Link>
                    </li>
                </ul>
            </div>
        );

        const userIsNotAuthenticated = (
            <div className="collapse navbar-collapse" id="mobile-nav">
                <ul className="navbar-nav ml-auto">
                    <li className="nav-item">
                        <Link className="nav-link " to={"/register"}>
                           Sign Up
                        </Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link" to={"/login"}>
                            Log In
                        </Link>
                    </li>
                </ul>
            </div>
        );

        let headerLinks;

        if(validToken && user) {
            headerLinks = userIsAuthenticated;
        } else {
            headerLinks = userIsNotAuthenticated;
        }
        
        return (
            <nav className="navbar navbar-expand-sm navbar-dark bg-primary mb-4">
                <div className="container">
                    <Link className="navbar-brand" to={"/"}>
                        Personal Project Management Tool
                    </Link>
                    <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#mobile-nav">
                        <span className="navbar-toggler-icon" />
                    </button>
                    {headerLinks}
                </div>
            </nav>
        );
    }
}

Header.propTypes = {
    security: PropTypes.object.isRequired,
    logOut: PropTypes.func.isRequired
};

const MapStateToProps = state => {
    return {
        security: state.security
    };
};

export default connect(MapStateToProps, { logOut })(Header);
