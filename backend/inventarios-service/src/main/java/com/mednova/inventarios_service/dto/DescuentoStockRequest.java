package com.mednova.inventarios_service.dto;

public class DescuentoStockRequest {
    private int productoId;
    private int farmaciaId;   // Nuevo campo
    private int cantidad;
    private String lote;

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public int getFarmaciaId() {
        return farmaciaId;
    }

    public void setFarmaciaId(int farmaciaId) {
        this.farmaciaId = farmaciaId;
    }
}

