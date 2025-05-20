package com.mednova.inventarios_service.controller;

import com.mednova.inventarios_service.model.Inventario;
import com.mednova.inventarios_service.service.InventarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public void deleteInventario(@PathVariable int id) {
        inventarioService.deleteInventario(id);
    }
}