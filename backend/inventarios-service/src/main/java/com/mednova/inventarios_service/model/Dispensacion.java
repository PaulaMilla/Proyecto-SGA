package com.mednova.inventarios_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Dispensacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;

    private String rutPaciente;

    private String nombrePaciente;

    private String emailUsuario;

    private LocalDate fechaDispensacion;

    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "farmacia_id", referencedColumnName = "id")
    private Farmacia farmacia;
}
