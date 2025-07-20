package com.mednova.dispersion_service.dto;

public class DescuentoRequest {
    private int idInventario;
    private int cantidad;

    public DescuentoRequest(int idInventario, int cantidad) {
        this.idInventario = idInventario;
        this.cantidad = cantidad;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public int getCantidad() {
        return cantidad;
    }
}
