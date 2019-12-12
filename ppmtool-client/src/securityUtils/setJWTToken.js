import Axios from "axios";

const setJWTToken = token => {
    if (token) {
        Axios.defaults.headers.common["Authorization"] = token;
    } else {
        delete Axios.defaults.headers.common["Authorization"];
    }
};

export default setJWTToken;
