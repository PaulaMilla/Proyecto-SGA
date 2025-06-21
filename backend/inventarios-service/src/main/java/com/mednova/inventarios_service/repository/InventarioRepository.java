package com.mednova.inventarios_service.repository;

import com.mednova.inventarios_service.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
    Optional<Inventario> findById_productoAndLoteAndNombre_farmacia(int idProducto, String lote, String nombreFarmacia);
}
