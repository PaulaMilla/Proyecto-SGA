package com.mednova.pacientes_service.service;

import com.mednova.pacientes_service.model.Paciente;
import com.mednova.pacientes_service.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> obtenerPorId(Integer id) {
        return pacienteRepository.findById(id);
    }

    public Paciente guardar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public void eliminar(Integer id) {
        pacienteRepository.deleteById(id);
    }

    public List<Paciente> obtenerPorBeneficiario(boolean esBeneficiario) {
        return pacienteRepository.findByBeneficiario(esBeneficiario);
    }
}
