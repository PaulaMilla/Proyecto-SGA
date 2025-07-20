package com.mednova.ventas_service.repository;

import com.mednova.ventas_service.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Integer> {
    List<Venta> findByPacienteId(int pacienteId);
    List<Venta> findByUsuarioId(int usuarioId);
    List<Venta> findByFechaVentaBetween(LocalDate fechaInicial, LocalDate fechaFinal);
    List<Venta> findByPagadaFalse();
}
