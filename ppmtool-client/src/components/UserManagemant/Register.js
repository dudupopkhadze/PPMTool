import React, { Component } from "react";
import { createNewUser } from "../../actions/securityActions";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import classnames from "classnames";

class Register extends Component {
    constructor() {
        super();
        this.state = {
            fullName: "",
            username: "",
            password: "",
            confirmPassword: "",
            errors: {}
        };
    }

    componentDidMount() {
        if (this.props.security.validToken) {
            this.props.history.push("/dashboard");
        }
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.errors) {
            this.setState({ errors: nextProps.errors });
        }
    }

    onChange = e => {
        this.setState({ [e.target.name]: e.target.value });
    };

    onSubmit = e => {
        e.preventDefault();
        const newUser = {
            fullName: this.state.fullName,
            username: this.state.username,
            password: this.state.password,
            confirmPassword: this.state.confirmPassword
        };
        this.props.createNewUser(newUser, this.props.history);
    };

    render() {
        const { errors } = this.state;
        return (
            <div className="register">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <h1 className="display-4 text-center">Sign Up</h1>
                            <p className="lead text-center">Create your Account</p>

                            <form action="create-profile.html" onSubmit={this.onSubmit}>
                                <div className="form-group">
                                    <input
                                        type="text"
                                        className={classnames("form-control form-control-lg ", {
                                            "is-invalid": errors.fullName
                                        })}
                                        placeholder="Name"
                                        name="fullName"
                                        value={this.state.fullName}
                                        onChange={this.onChange}
                                    />
                                    {errors.fullName ? <div className="invalid-feedback">{errors.fullName}</div> : null}
                                </div>
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
                                <div className="form-group">
                                    <input
                                        type="password"
                                        className={classnames("form-control form-control-lg ", {
                                            "is-invalid": errors.confirmPassword
                                        })}
                                        placeholder="Confirm Password"
                                        name="confirmPassword"
                                        value={this.state.confirmPassword}
                                        onChange={this.onChange}
                                    />
                                    {errors.confirmPassword ? <div className="invalid-feedback">{errors.confirmPassword}</div> : null}
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

Register.propTypes = {
    createNewUser: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired,
    security: PropTypes.object.isRequired
};

const MapStateToProps = state => {
    return {
        errors: state.errors,
        security: state.security
    };
};

export default connect(MapStateToProps, { createNewUser })(Register);
