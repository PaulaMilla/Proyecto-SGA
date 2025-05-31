package com.mednova.reportes_service.model;

/**
 * Clase DTO que representa un detalle individual de una venta.
 * Usado dentro del objeto VentaDTO para reflejar los productos vendidos.
 */
public class DetalleVentaDTO {

    private Integer medicamentoId;
    private String nombreMedicamento;
    private int cantidad;
    private double subtotal;               // subtotal en dinero por este producto

    // Getters y setters
    public Integer getMedicamentoId() {
        return medicamentoId;
    }

    public void setMedicamentoId(Integer medicamentoId) {
        this.medicamentoId = medicamentoId;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
