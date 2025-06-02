package com.mednova.alertas_service.repository;

import com.mednova.alertas_service.model.Retiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RetiroRepository extends JpaRepository<Retiro, Integer> {
    @Query("SELECT r FROM Retiro r WHERE r.id_paciente = :id_paciente AND r.id_producto = :id_producto ORDER BY r.fecha_retiro DESC")
    Optional<Retiro> findUltimoRetiro(@Param("id_paciente") int pacienteId, @Param("id_producto") int id_producto);
}
