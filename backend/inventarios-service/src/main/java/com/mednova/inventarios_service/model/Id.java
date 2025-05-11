package com.mednova.inventarios_service.model;

import jakarta.persistence.*;

@Entity
public class Id {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_id;
    private String nombre_id;



    // Getters and Setters
    public Integer getId_id() {
        return id_id;
    }
    public void setId_Id(Integer id_id) {
        this.id_id = id_id;
    }
    public String getNombre_id() {
        return nombre_id;
    }
    public void setNombre_id(String nombre_id) {
        this.nombre_id = nombre_id;
    }

}
