package com.mednova.compras_service.repository;


import com.mednova.compras_service.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Integer> {
    List<Compra> findByProveedor_NombreContainingIgnoreCase(String nombre);
}
