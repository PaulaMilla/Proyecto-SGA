package com.mednova.usuarios_service.dto;

public class UsuarioRequestDTO {
    private String nombre;
    private String apellido;
    private String correo;
    private String password;
    private String estado;
    private String nombre_farmacia;
    private Integer idRol;

    //Getters y Setters


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre_farmacia() {
        return nombre_farmacia;
    }

    public void setNombre_farmacia(String nombre_farmacia) {
        this.nombre_farmacia = nombre_farmacia;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }
}