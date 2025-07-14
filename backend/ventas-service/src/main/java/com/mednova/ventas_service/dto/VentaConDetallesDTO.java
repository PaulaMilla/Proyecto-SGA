package com.mednova.ventas_service.dto;

import java.util.List;

public class VentaConDetallesDTO {
    private int pacienteId;
    private int usuarioId;
    private List<DetalleVentaDTO> detalles;

    // Getters y Setters
    public int getPacienteId() { return pacienteId; }
    public void setPacienteId(int pacienteId) { this.pacienteId = pacienteId; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public List<DetalleVentaDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleVentaDTO> detalles) { this.detalles = detalles; }
}

