package com.mednova.inventarios_service.repository;

import com.mednova.inventarios_service.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
}
