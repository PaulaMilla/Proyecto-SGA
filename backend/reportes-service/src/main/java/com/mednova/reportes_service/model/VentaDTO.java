package com.mednova.reportes_service.model;

import java.time.LocalDate;        // Para representar fechas
import java.util.List;             // Para manejar listas de detalles

/**
 * Clase DTO para representar una venta traída desde el microservicio de ventas.
 * Se usa para mapear el JSON que llega al microservicio de reportes desde el API Gateway.
 */
public class VentaDTO {

    private Integer id;                        // ID de la venta
    private Integer pacienteId;                // ID del paciente asociado a la venta
    private String nombrePaciente;             // Nombre del paciente (si lo incluye el JSON)
    private LocalDate fecha;                   // Fecha de la venta
    private List<DetalleVentaDTO> detalles;    // Lista de detalles de la venta (productos)

    // Métodos getter y setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Integer pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<DetalleVentaDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaDTO> detalles) {
        this.detalles = detalles;
    }
}
