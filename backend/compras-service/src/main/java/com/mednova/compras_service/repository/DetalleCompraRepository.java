package com.mednova.compras_service.repository;

import com.mednova.compras_service.model.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Integer> {
    List<DetalleCompra> findByCompraId(int compraId);
}
