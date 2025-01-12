import axios from "axios";
import {cleatAuthToken, getAuthToken} from "../utils/auth-utils";
import {LOGIN_PAGE} from "../constants/path";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || '';

axios.interceptors.response.use(function (response) {
    return response;
}, function (error) {
    const status = error.status ? error.status : error.response && error.response.status;
    if (status === 403) {
        cleatAuthToken();
        window.location.href = LOGIN_PAGE;
    }
    return Promise.reject(error);
});


export const buildUrl = (url: string) => {
    return `${API_BASE_URL}${url}`;
};

const getConfig = () => {
    const token = getAuthToken();
    const headers = token ? {Authorization: `Bearer ${token}`} : {};
    return {
        headers
    };
};

export const post = (url: string, data: object) => {
    return axios.post(buildUrl(url), data, {
        ...getConfig()
    });
};

export const get = (url: string, data: object) => {
    return axios.get(buildUrl(url), {
        ...getConfig(),
        params: data,
    });
};
