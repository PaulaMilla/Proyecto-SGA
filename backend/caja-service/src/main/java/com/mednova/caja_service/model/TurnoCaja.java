package com.mednova.caja_service.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="turno_caja")
public class TurnoCaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="caja_id")
    private Caja caja;

    @Column(name="id_usuario")
    private int idUsuario;

    @Column(name="hora_apertura")
    private LocalDateTime horaApertura;

    @Column(name="hora_cierre")
    private LocalDateTime horaCierre;

    private BigDecimal montoApertura;

    private BigDecimal montoCierre;

    private Boolean cerrado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDateTime getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(LocalDateTime horaApertura) {
        this.horaApertura = horaApertura;
    }

    public LocalDateTime getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(LocalDateTime horaCierre) {
        this.horaCierre = horaCierre;
    }

    public BigDecimal getMontoApertura() {
        return montoApertura;
    }

    public void setMontoApertura(BigDecimal montoApertura) {
        this.montoApertura = montoApertura;
    }

    public BigDecimal getMontoCierre() {
        return montoCierre;
    }

    public void setMontoCierre(BigDecimal montoCierre) {
        this.montoCierre = montoCierre;
    }

    public Boolean getCerrado() {
        return cerrado;
    }

    public void setCerrado(Boolean cerrado) {
        this.cerrado = cerrado;
    }
}
