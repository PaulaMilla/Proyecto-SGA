package com.mednova.compras_service.dto;

import com.mednova.compras_service.model.TipoDocumento;

import java.util.List;

public class CompraDTO {
    private String numeroDocumento;
    private TipoDocumento tipo;
    private int proveedorId;
    private String observacion;
    private List<DetalleCompraDTO> detalles;

    private String bodega;
    private int farmaciaId;

    //getters y setters

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public TipoDocumento getTipo() {
        return tipo;
    }

    public void setTipo(TipoDocumento tipo) {
        this.tipo = tipo;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<DetalleCompraDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCompraDTO> detalles) {
        this.detalles = detalles;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public int getFarmaciaId() {
        return farmaciaId;
    }

    public void setFarmaciaId(int farmaciaId) {
        this.farmaciaId = farmaciaId;
    }
}
