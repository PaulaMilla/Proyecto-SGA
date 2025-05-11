package com.mednova.inventarios_service.model;

import jakarta.persistence.*;

@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cantidad;
    private String nombre_cantidad;



    // Getters and Setters
    public Integer getId_cantidad() {
        return id_cantidad;
    }
    public void setId_cantidad(Integer id_cantidad) {
        this.id_cantidad = id_cantidad;
    }
    public String getNombre_cantidad() {
        return nombre_cantidad;
    }
    public void setNombre_cantidad(String nombre_cantidad) {
        this.nombre_cantidad = nombre_cantidad;
    }

}
