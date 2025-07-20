package com.mednova.inventarios_service.dto;

public class FraccionamientoRequest {
    private int idInventario;
    private int cantidadFraccionada;
    private String nuevoLote;
    private String emailUsuario; //  importante

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

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
}
