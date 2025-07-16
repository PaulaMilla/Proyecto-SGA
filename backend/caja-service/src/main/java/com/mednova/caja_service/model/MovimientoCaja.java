package com.mednova.caja_service.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="movimiento_caja")
public class MovimientoCaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_movimientocaja;

    @ManyToOne
    @JoinColumn(name = "turno_caja_id")
    private TurnoCaja turnoCaja;

    private LocalDateTime fechaHora;

    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    private TipoMovimiento tipoMovimiento;

    private String descripcion;

    private int id_venta;

    public int getId_movimientocaja() {
        return id_movimientocaja;
    }

    public void setId_movimientocaja(int id_movimientocaja) {
        this.id_movimientocaja = id_movimientocaja;
    }

    public TurnoCaja getTurnoCaja() {
        return turnoCaja;
    }

    public void setTurnoCaja(TurnoCaja turnoCaja) {
        this.turnoCaja = turnoCaja;
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

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }
}
