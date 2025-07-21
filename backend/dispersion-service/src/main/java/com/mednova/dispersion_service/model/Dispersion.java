package com.mednova.dispersion_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Dispersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int idInventario;
    private int cantidad;
    private String emailUsuario;
    private LocalDateTime fecha;

    public Dispersion() {}

    public Dispersion(int idInventario, int cantidad, String emailUsuario) {
        this.idInventario = idInventario;
        this.cantidad = cantidad;
        this.emailUsuario = emailUsuario;
        this.fecha = LocalDateTime.now();
    }

    // Getters y Setters
}
