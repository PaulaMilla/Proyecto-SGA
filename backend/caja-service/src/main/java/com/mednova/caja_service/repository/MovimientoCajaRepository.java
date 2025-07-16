package com.mednova.caja_service.repository;

import com.mednova.caja_service.model.MovimientoCaja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoCajaRepository extends JpaRepository<MovimientoCaja, Integer> {
    List<MovimientoCaja> findByTurnoCajaId(int turnoId);
}
