package com.mednova.fraccionamiento_service.repository;

import com.mednova.fraccionamiento_service.model.Fraccionamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FraccionamientoRepository extends JpaRepository<Fraccionamiento, Long> {
}
