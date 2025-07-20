package com.mednova.fraccionamiento_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fraccionamientos")
public class Fraccionamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int idInventario;

    private int cantidadFraccionada;

    private String nuevoLote;

    private LocalDateTime fecha;

    public Fraccionamiento() {
        this.fecha = LocalDateTime.now();
    }

    public Fraccionamiento(int idInventario, int cantidadFraccionada, String nuevoLote) {
        this.idInventario = idInventario;
        this.cantidadFraccionada = cantidadFraccionada;
        this.nuevoLote = nuevoLote;
        this.fecha = LocalDateTime.now();
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public int getCantidadFraccionada() {
        return cantidadFraccionada;
    }

    public void setCantidadFraccionada(int cantidadFraccionada) {
        this.cantidadFraccionada = cantidadFraccionada;
    }

    public String getNuevoLote() {
        return nuevoLote;
    }

    public void setNuevoLote(String nuevoLote) {
        this.nuevoLote = nuevoLote;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
