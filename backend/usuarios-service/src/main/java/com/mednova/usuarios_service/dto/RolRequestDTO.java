package com.mednova.usuarios_service.dto;

import java.util.List;

public class RolRequestDTO {
    private String nombre_rol;
    private String descripcion_rol;
    private List<Integer> permisos; // IDs de los permisos

    // Getters y setters

    public String getNombre_rol() {
        return nombre_rol;
    }

    public void setNombre_rol(String nombre_rol) {
        this.nombre_rol = nombre_rol;
    }

    public String getDescripcion_rol() {
        return descripcion_rol;
    }

    public void setDescripcion_rol(String descripcion_rol) {
        this.descripcion_rol = descripcion_rol;
    }

    public List<Integer> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Integer> permisos) {
        this.permisos = permisos;
    }
}
