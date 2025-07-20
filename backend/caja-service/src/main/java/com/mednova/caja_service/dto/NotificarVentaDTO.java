package com.mednova.caja_service.dto;

import java.math.BigDecimal;

public class NotificarVentaDTO {
    private int idVenta;
    private BigDecimal monto;

    //getter y setter

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}
