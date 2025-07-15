package com.mednova.usuarios_service.dto;

import com.mednova.usuarios_service.model.Usuario;

import java.util.Date;

public class UsuarioResponseDTO {

    private Integer id;
    private String nombre;
    private String apellido;
    private String correo;
    private String estado;
    private String nombreFarmacia;
    private String nombreRol; // Nombre del rol, no el objeto completo
    private Date fechaCreacion;
    private Date fechaModificacion;

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.correo = usuario.getCorreo();
        this.estado = usuario.getEstado();
        this.nombreFarmacia = usuario.getNombre_farmacia();
        this.fechaCreacion = usuario.getFechaCreacion();
        this.fechaModificacion = usuario.getFechaModificacion();

        // Si el rol no es null, obtenemos su nombre
        if (usuario.getRol() != null) {
            this.nombreRol = usuario.getRol().getNombre_rol(); // Asumimos que Rol tiene un campo "nombre"
        }
    }

    // Getters y setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreFarmacia() {
        return nombreFarmacia;
    }

    public void setNombreFarmacia(String nombreFarmacia) {
        this.nombreFarmacia = nombreFarmacia;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
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
}
