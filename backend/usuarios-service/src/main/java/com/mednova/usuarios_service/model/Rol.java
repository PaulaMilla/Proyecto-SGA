package com.mednova.usuarios_service.model;

import jakarta.persistence.*;

@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_rol;
    private String nombre_rol;
    private String descripcion_rol;


    // Getters and Setters
    public Integer getId_rol() {
        return id_rol;
    }
    public void setId_rol(Integer id_rol) {
        this.id_rol = id_rol;
    }
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

}
