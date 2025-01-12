import {get} from "./api";


const BASE_URL = '/api/v1/users';

export const getAllUsers = (page: number, size: number) => get(`${BASE_URL}?page=${page}&size=${size}`, {});