package com.mednova.usuarios_service.model;

import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_usuario;
    private String nombre_usuario;
    private String apellido_usuario;
    private String correo_usuario;
    private String password;
    private String estado_usuario;
    private String rol_usuario;
    private Date fecha_creacion;
    private Date fecha_modificacion;
    private Date fecha_ultimo_acceso;


    //Getters and Setters
    public Integer getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }
    public String getNombre_usuario() {
        return nombre_usuario;
    }
    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }
    public String getApellido_usuario() {
        return apellido_usuario;
    }
    public void setApellido_usuario(String apellido_usuario) {
        this.apellido_usuario = apellido_usuario;
    }
    public String getCorreo_usuario() {
        return correo_usuario;
    }
    public void setCorreo_usuario(String correo_usuario) {
        this.correo_usuario = correo_usuario;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEstado_usuario() {
        return estado_usuario;
    }
    public void setEstado_usuario(String estado_usuario) {
        this.estado_usuario = estado_usuario;
    }
    public String getRol_usuario() {
        return rol_usuario;
    }
    public void setRol_usuario(String rol_usuario) {
        this.rol_usuario = rol_usuario;
    }
    public Date getFecha_creacion() {
        return fecha_creacion;
    }
    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
    public Date getFecha_modificacion() {
        return fecha_modificacion;
    }
    public void setFecha_modificacion(Date fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }
    public Date getFecha_ultimo_acceso() {
        return fecha_ultimo_acceso;
    }
    public void setFecha_ultimo_acceso(Date fecha_ultimo_acceso) {
        this.fecha_ultimo_acceso = fecha_ultimo_acceso;
    }
}