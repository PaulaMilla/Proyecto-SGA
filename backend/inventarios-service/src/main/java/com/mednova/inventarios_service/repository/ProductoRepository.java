package com.mednova.inventarios_service.repository;

import com.mednova.inventarios_service.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
