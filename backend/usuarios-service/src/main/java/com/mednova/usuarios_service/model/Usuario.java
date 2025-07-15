package com.mednova.usuarios_service.model;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")  
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    @Column(name = "id_usuario")  
    private Integer id;

    @Column(name = "apellido_usuario") 
    private String apellido;

    @Column(name = "correo")
    private String correo;

    @Column(name = "estado_usuario")
    private String estado;

    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)  
    private Date fechaCreacion;

    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    @Column(name = "fecha_ultimo_acceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltimoAcceso;

    @Column(name = "nombre_usuario")
    private String nombre;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_rol") // este campo FK debe existir en la tabla
    private Rol rol;

    @Column (name = "nombre_farmacia")
    private String nombre_farmacia;

    // Getters y setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaUltimoAcceso() {
        return fechaUltimoAcceso;
    }

    public void setFechaUltimoAcceso(Date fechaUltimoAcceso) {
        this.fechaUltimoAcceso = fechaUltimoAcceso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getNombre_farmacia() {
        return nombre_farmacia;
    }

    public void setNombre_farmacia(String nombre_farmacia) {
        this.nombre_farmacia = nombre_farmacia;
    }
}