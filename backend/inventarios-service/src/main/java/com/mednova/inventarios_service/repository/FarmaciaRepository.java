package com.mednova.inventarios_service.repository;

import com.mednova.inventarios_service.model.Farmacia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FarmaciaRepository extends JpaRepository<Farmacia, Integer> {
    Optional<Farmacia> findByNombre(String nombre);
}
