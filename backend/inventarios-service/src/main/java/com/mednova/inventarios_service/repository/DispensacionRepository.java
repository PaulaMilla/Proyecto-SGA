package com.mednova.inventarios_service.repository;

import com.mednova.inventarios_service.model.Dispensacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DispensacionRepository extends JpaRepository<Dispensacion, Integer> {

    @Query("SELECT COUNT(d) FROM Dispensacion d WHERE d.fechaDispensacion = :fecha")
    int contarDispensacionesHoy(LocalDate fecha);

    List<Dispensacion> findByFechaDispensacion(LocalDate fecha);
}
