package com.mednova.dispersion_service.repository;

import com.mednova.dispersion_service.model.Dispersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DispersionRepository extends JpaRepository<Dispersion, Long> {
}
