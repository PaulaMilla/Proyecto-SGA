package com.mednova.compras_service.repository;

import com.mednova.compras_service.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
    Optional<Proveedor> findByNombreIgnoreCase(String nombre);
}
