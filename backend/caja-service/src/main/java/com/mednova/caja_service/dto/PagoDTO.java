package com.mednova.caja_service.dto;

import com.mednova.caja_service.model.MetodoPago;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PagoDTO {
    private int id;
    private int turnoCajaId;
    private int idVenta;
    private LocalDateTime fechaHora;
    private BigDecimal monto;
    private MetodoPago metodoPago;
    private String observacion;

    //getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTurnoCajaId() {
        return turnoCajaId;
    }

    public void setTurnoCajaId(int turnoCajaId) {
        this.turnoCajaId = turnoCajaId;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
