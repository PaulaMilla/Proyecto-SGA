package com.mednova.pacientes_service.controller;

import com.mednova.pacientes_service.model.Paciente;
import com.mednova.pacientes_service.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    // Endpoint de prueba para diagnosticar problemas
    @PostMapping("/test-upload")
    public ResponseEntity<Map<String, String>> testUpload(@RequestPart("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("message", "El archivo está vacío");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("message", "Archivo recibido correctamente: " + file.getOriginalFilename());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadPacientes(@RequestPart("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        System.out.println("=== CARGA DE PACIENTES ===");
        System.out.println("Recibido archivo: " + file.getOriginalFilename());
        System.out.println("Tamaño del archivo: " + file.getSize() + " bytes");
        System.out.println("Tipo de contenido: " + file.getContentType());

        try {
            // Validaciones básicas del archivo
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(response);
            }

            String fileName = file.getOriginalFilename();
            if (fileName == null || fileName.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(response);
            }

            if (!fileName.toLowerCase().endsWith(".csv") && !fileName.toLowerCase().endsWith(".xlsx")) {
                return ResponseEntity.badRequest().body(response);
            }

            System.out.println("Iniciando procesamiento del archivo...");
            pacienteService.processFile(file);
            System.out.println("Archivo procesado exitosamente");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            System.err.println("Error al procesar archivo: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
