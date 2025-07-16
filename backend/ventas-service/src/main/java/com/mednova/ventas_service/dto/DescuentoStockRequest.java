package com.mednova.ventas_service.dto;

public class DescuentoStockRequest {
    private int productoId;
    private int cantidad;
    private Integer farmaciaId; // si es opcional
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

    public Integer getFarmaciaId() {
        return farmaciaId;
    }

    public void setFarmaciaId(Integer farmaciaId) {
        this.farmaciaId = farmaciaId;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }
}

