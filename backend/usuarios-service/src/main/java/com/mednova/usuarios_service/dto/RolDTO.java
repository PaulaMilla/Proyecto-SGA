package com.mednova.usuarios_service.dto;

import com.mednova.usuarios_service.model.Rol;

public class RolDTO {
    private Integer id;
    private String nombre;

    public RolDTO(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public RolDTO(Rol rol) {
        this.id = rol.getId_rol();
        this.nombre = rol.getNombre_rol();
    }

    // Getters y setters
}

