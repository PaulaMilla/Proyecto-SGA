package com.mednova.alertas_service.client.dto;

import com.mednova.alertas_service.model.enums.TipoProducto;

public class ProductoDTO {
    private int id_producto;
    private String nombre;
    private TipoProducto tipo;

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_producto() {
        return id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoProducto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }
}
