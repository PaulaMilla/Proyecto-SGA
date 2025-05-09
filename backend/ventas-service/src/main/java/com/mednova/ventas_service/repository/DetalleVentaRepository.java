package com.mednova.ventas_service.repository;

import com.mednova.ventas_service.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Integer> {
    DetalleVenta findByIdVenta(int idVenta);

    DetalleVenta findByVentaId(Integer ventaId);
}
