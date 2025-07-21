package com.mednova.usuarios_service.dto;

import com.mednova.usuarios_service.model.Rol;

public class RolDTO {
    private int id;
    private String nombre;

    public RolDTO(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public RolDTO(Rol rol) {
        this.id = rol.getId_rol();
        this.nombre = rol.getNombre_rol();
    }

    // Getters y setters


    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

