package com.mednova.auth_service.dto;

import java.util.List;

public class JwtResponse {
    private String token;
    private String email;
    private String rol;
    private List<String> permisos;

    public JwtResponse(String token, String email, String rol, List<String> permisos) {
        this.token = token;
        this.email = email;
        this.rol = rol;
        this.permisos = permisos;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<String> permisos) {
        this.permisos = permisos;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
