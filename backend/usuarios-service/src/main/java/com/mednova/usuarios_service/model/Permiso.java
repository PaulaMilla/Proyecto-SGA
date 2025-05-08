package com.mednova.usuarios_service.model;

import jakarta.persistence.*;

@Entity
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_permiso;
    @Column(name = "nombre_permiso")
    private String nombre_permiso;
    @Column(name = "descripcion_permiso")
    private String descripcion_permiso;


    // Getters and Setters
    public Integer getId_permiso() {
        return id_permiso;
    }
    public void setId_permiso(Integer id_permiso) {
        this.id_permiso = id_permiso;
    }
    public String getNombre_permiso() {
        return nombre_permiso;
    }
    public void setNombre_permiso(String nombre_permiso) {
        this.nombre_permiso = nombre_permiso;
    }
    public String getDescripcion_permiso() {
        return descripcion_permiso;
    }
    public void setDescripcion_permiso(String descripcion_permiso) {
        this.descripcion_permiso = descripcion_permiso;
    }
}
