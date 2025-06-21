package com.mednova.auth_service.dto;

public class JwtResponse {
    private String token;

    public JwtResponse(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
