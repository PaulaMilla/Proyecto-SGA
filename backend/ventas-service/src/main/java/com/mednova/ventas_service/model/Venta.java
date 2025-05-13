package com.mednova.ventas_service.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "pacienteId")
    private int pacienteId;

    @Column(name = "fechaVenta")
    private LocalDate fechaVenta;

    @Column(name = "total")
    private double total;

    @Column(name = "usuarioId")
    private int usuarioId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdUsuarioVendedor() {
        return usuarioId;
    }

    public void setIdUsuarioVendedor(int usuarioId) {
        this.usuarioId = usuarioId;
    }
}
