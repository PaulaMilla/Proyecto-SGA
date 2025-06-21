package com.mednova.auth_service.dto;

public class JwtResponse {
    private String token;
    private String email;
    private String rol;

    public JwtResponse(String token, String email, String rol) {
        this.token = token;
        this.email = email;
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public String getRol() {
        return rol;
    }

    public String getEmail() {
        return email;
    }
}
