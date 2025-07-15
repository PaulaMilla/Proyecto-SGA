package com.mednova.usuarios_service.dto;

import java.util.List;

public class UsuarioLoginDTO {
    private String email;
    private String rol;
    private List<String> permisos;

    public UsuarioLoginDTO(String email, String rol, List<String> permisos) {
        this.email = email;
        this.rol = rol;
        this.permisos = permisos;
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
}