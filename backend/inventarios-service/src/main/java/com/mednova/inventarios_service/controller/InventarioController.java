package com.mednova.inventarios_service.controller;

import com.mednova.inventarios_service.model.Inventario;
import com.mednova.inventarios_service.service.InventarioService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping
    public ResponseEntity<List<Inventario>> getAllInventarios() {
        return ResponseEntity.ok(inventarioService.getAllInventarios());
    }

    @GetMapping("/{id}")
    public Optional<Inventario> getInventarioById(@PathVariable int id) {
        return inventarioService.getInventarioById(id);
    }

    @PostMapping
    public Inventario createInventario(@RequestBody Inventario inventario) {
        return inventarioService.saveInventario(inventario);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadInventario(@RequestParam("file") MultipartFile file) {
        try {
            inventarioService.processFile(file);
            return ResponseEntity.ok("Archivo procesado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteInventario(@PathVariable int id) {
        inventarioService.deleteInventario(id);
    }
}