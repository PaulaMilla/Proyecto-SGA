package com.mednova.alertas_service.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class AlertaRetiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_alerta;

    @Column(name = "id_paciente")
    private int id_paciente;

    @Column(name = "id_producto")
    private int id_producto;

    @Column(name = "fecha_programada")
    private LocalDate fecha_programada;

    @Column(name = "mensaje")
    private String mensaje;

    public int getId_alerta() {
        return id_alerta;
    }

    public void setId_alerta(int id_alerta) {
        this.id_alerta = id_alerta;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public LocalDate getFecha_programada() {
        return fecha_programada;
    }

    public void setFecha_programada(LocalDate fecha_programada) {
        this.fecha_programada = fecha_programada;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
