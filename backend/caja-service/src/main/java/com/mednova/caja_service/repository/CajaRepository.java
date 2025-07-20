package com.mednova.caja_service.repository;

import com.mednova.caja_service.model.Caja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CajaRepository extends JpaRepository<Caja, Integer> {
    List<Caja> findByIdFarmacia(Integer idFarmacia);
}
