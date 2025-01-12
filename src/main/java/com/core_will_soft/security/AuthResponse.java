package com.core_will_soft.security;

import lombok.Data;

@Data
public class AuthResponse {
    private final String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }
}
