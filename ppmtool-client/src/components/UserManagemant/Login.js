import React, { Component } from "react";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { login } from "../../actions/securityActions";
import classnames from "classnames";

class Login extends Component {
    constructor() {
        super();
        this.state = {
            username: "",
            password: "",
            errors: {}
        };
    }

    componentDidMount(){
        if(this.props.security.validToken){
            this.props.history.push("/dashboard")
        }
    }

    componentWillReceiveProps(newProps) {
        if(newProps.security.validToken){
            this.props.history.push("/dashboard")
        }

        if (newProps.errors) {
            this.setState({ errors: newProps.errors });
        }
    }

    onChange = e => {
        this.setState({ [e.target.name]: e.target.value });
    };

    onSubmit = e => {
        e.preventDefault();
        const loginRequest = {
            username : this.state.username,
            password : this.state.password
        }
        this.props.login(loginRequest)
    };

    render() {
        const { errors } = this.state;
        return (
            <div className="login">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <h1 className="display-4 text-center">Log In</h1>

                            <form action="dashboard.html" onSubmit = {this.onSubmit}>
                                <div className="form-group">
                                    <input
                                        type="email"
                                        className={classnames("form-control form-control-lg ", {
                                            "is-invalid": errors.username
                                        })}
                                        placeholder="Email Address"
                                        name="username"
                                        value={this.state.username}
                                        onChange={this.onChange}
                                    />
                                    {errors.username ? <div className="invalid-feedback">{errors.username}</div> : null}
                                </div>
                                <div className="form-group">
                                    <input
                                        type="password"
                                        className={classnames("form-control form-control-lg ", {
                                            "is-invalid": errors.password
                                        })}
                                        placeholder="Password"
                                        name="password"
                                        value={this.state.password}
                                        onChange={this.onChange}
                                    />
                                    {errors.password ? <div className="invalid-feedback">{errors.password}</div> : null}
                                </div>
                                <input type="submit" className="btn btn-info btn-block mt-4" />
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

Login.propTypes = {
    login: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired,
    security : PropTypes.object.isRequired
};

const MapStateToProps = state => {
    return {
        errors: state.errors,
        security : state.security
    };
};

export default connect(MapStateToProps, { login })(Login);
