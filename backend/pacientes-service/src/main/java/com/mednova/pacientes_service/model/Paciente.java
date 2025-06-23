package com.mednova.pacientes_service.model;

import jakarta.persistence.*;

@Entity
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_paciente;

    @Column(name="nombre")
    private String nombre;

    @Column(name="edad")
    private int edad;

    @Column(name="direccion")
    private String direccion;

    @Column(name="beneficiario")
    private boolean beneficiario; // si es beneficiario o no

    @Column (name = "nombre_farmacia")
    private String nombre_farmacia;


    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(boolean beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getNombre_farmacia() {
        return nombre_farmacia;
    }

    public void setNombre_farmacia(String nombre_farmacia) {
        this.nombre_farmacia = nombre_farmacia;
    }
}
