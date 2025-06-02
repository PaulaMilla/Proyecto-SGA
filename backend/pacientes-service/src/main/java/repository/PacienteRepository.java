package com.mednova.pacientes_service.repository;

import com.mednova.pacientes_service.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    List<Paciente> findByBeneficiario(boolean beneficiario); // Consulta por tipo de beneficiario
}
