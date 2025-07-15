package com.mednova.usuarios_service.dto;

import java.util.List;

public class UsuarioLoginDTO {
    private String email;
    private String rol;
    private List<String> permisos;
    private String password;

    public UsuarioLoginDTO(String email, String rol, List<String> permisos, String password) {
        this.email = email;
        this.rol = rol;
        this.permisos = permisos;
        this.password = password;
    }

    // getters y setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<String> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<String> permisos) {
        this.permisos = permisos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}