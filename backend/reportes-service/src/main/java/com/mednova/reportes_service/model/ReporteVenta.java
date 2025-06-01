package com.mednova.reportes_service.model;

import java.time.LocalDate;

public class ReporteVenta {
    private LocalDate fecha;
    private String nombreMedicamento;
    private int cantidadVendida;
    private double total;

    public ReporteVenta(LocalDate fecha, String nombreMedicamento, int cantidadVendida, double total) {
        this.fecha = fecha;
        this.nombreMedicamento = nombreMedicamento;
        this.cantidadVendida = cantidadVendida;
        this.total = total;
    }
}