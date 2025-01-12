import {post} from "./api";

const BASE_URL = '/auth/login';

interface LoginRequest {
    username: string;
    password: string;
}

export const authWithPassword = (request: LoginRequest) => post(`${BASE_URL}`, request);

