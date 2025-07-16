package com.mednova.caja_service.repository;

import com.mednova.caja_service.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findByTurnoCajaId(int turnoId);
}
