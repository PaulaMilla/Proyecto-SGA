package com.mednova.pacientes_service.controller;

import com.mednova.pacientes_service.model.Paciente;
import com.mednova.pacientes_service.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Paciente>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pacienteService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Paciente> crearPaciente(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.guardar(paciente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Integer id) {
        pacienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/beneficiarios")
    public ResponseEntity<List<Paciente>> obtenerBeneficiarios() {
        return ResponseEntity.ok(pacienteService.obtenerPorBeneficiario(true));
    }

    @GetMapping("/no-beneficiarios")
    public ResponseEntity<List<Paciente>> obtenerNoBeneficiarios() {
        return ResponseEntity.ok(pacienteService.obtenerPorBeneficiario(false));
    }
}
