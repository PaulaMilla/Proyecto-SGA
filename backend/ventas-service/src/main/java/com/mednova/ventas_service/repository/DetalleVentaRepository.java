package com.mednova.ventas_service.repository;

import com.mednova.ventas_service.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Integer> {

    DetalleVenta findByVentaId(Integer ventaId);
}
