package com.mednova.caja_service.repository;

import com.mednova.caja_service.model.TurnoCaja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TurnoCajaRepository extends JpaRepository<TurnoCaja, Integer> {
    Optional<TurnoCaja> findByCajaIdAndCerradoFalse(int cajaId);
}
