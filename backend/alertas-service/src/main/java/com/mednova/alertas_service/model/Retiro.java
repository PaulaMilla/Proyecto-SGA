package com.mednova.alertas_service.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Retiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_retiro;

    @Column(name = "id_paciente")
    private int id_paciente;

    @Column(name = "id_producto")
    private int id_producto;

    @Column(name = "fecha_retiro")
    private LocalDate fecha_retiro;

    public int getId_retiro() {
        return id_retiro;
    }

    public void setId_retiro(int id_retiro) {
        this.id_retiro = id_retiro;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setid_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public LocalDate getFecha_retiro() {
        return fecha_retiro;
    }

    public void setFecha_retiro(LocalDate fecha_retiro) {
        this.fecha_retiro = fecha_retiro;
    }
}
