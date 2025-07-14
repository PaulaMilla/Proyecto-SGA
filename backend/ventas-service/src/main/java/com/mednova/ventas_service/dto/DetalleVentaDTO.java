package com.mednova.ventas_service.dto;

public class DetalleVentaDTO {
    private int productoId;
    private int cantidad;

    // Getters y Setters
    public int getProductoId() { return productoId; }
    public void setProductoId(int productoId) { this.productoId = productoId; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}

