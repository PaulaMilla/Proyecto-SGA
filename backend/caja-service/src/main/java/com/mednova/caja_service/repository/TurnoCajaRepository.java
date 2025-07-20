package com.mednova.caja_service.repository;

import com.mednova.caja_service.model.TurnoCaja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TurnoCajaRepository extends JpaRepository<TurnoCaja, Integer> {
    @Query("SELECT t FROM TurnoCaja t WHERE t.caja.id_caja = :cajaId AND t.cerrado = false")
    Optional<TurnoCaja> findTurnoAbiertoPorCaja(@Param("cajaId") int cajaId);

    @Query("SELECT t FROM TurnoCaja t WHERE t.caja.id_caja = :cajaId AND t.cerrado = false")
    Optional<TurnoCaja> findByCajaIdAndActivoTrue(@Param("cajaId") int cajaId);

}
