import {AUTH_JWT_LOCAL_STORAGE_KEY} from "../constants/authConstants";
import { jwtDecode } from "jwt-decode";

export const getAuthToken = () => localStorage.getItem(AUTH_JWT_LOCAL_STORAGE_KEY);
export const cleatAuthToken = () => localStorage.removeItem(AUTH_JWT_LOCAL_STORAGE_KEY);
export const setAuthToken = (token: string) => localStorage.setItem(AUTH_JWT_LOCAL_STORAGE_KEY, token);


export const getTokenExpirationTime = (token: string): number => {
    try {
        const decodedToken: { exp: number } = jwtDecode(token);
        return decodedToken.exp * 1000;
    } catch (error) {
        console.error("Invalid token", error);
        return 0;
    }
};

export const isTokenExpired = (token: string): boolean => {
    const expirationTime = getTokenExpirationTime(token);
    return Date.now() > expirationTime;
};