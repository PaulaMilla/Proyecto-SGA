package com.mednova.alertas_service.repository;

import com.mednova.alertas_service.model.AlertaRetiro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertaRepository extends JpaRepository<AlertaRetiro, Integer> {
    List<AlertaRetiro> findByIdPaciente(int id_paciente);
}
