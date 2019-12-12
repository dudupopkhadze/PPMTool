import React, { Component } from "react";
import { Link } from "react-router-dom";
import PropTypes from 'prop-types';
import { connect } from 'react-redux'


class Landing extends Component {

    componentDidMount(){
        if(this.props.security.validToken){
            this.props.history.push("/dashboard")
        }
    }

    render() {
        return (
            <div className="landing">
                <div className="light-overlay landing-inner text-dark">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-12 text-center">
                                <h1 className="display-3 mb-4">Personal Project Managment Tool</h1>
                                <p className="lead">Create Your Account Yo Join Active Projects Or Start Your Own</p>
                                <hr />
                                <Link to={"/register"} className="btn btn-lg btn-primary mr-2">
                                    Sign Up
                                </Link>
                                <Link to={"/login"} className="btn btn-lg btn-secondary mr-2">
                                    Log In
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

Landing.propTypes = {
    security : PropTypes.object.isRequired
}


const MapStateToProps = state => {
    return {
        security : state.security
    }
}

export default connect(MapStateToProps)(Landing);
