package com.mednova.compras_service.controller;

import com.mednova.compras_service.model.Proveedor;
import com.mednova.compras_service.repository.ProveedorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {
    private final ProveedorRepository proveedorRepository;

    public ProveedorController(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @GetMapping
    public ResponseEntity<List<Proveedor>> listarProveedores() {
        List<Proveedor> proveedores = proveedorRepository.findAll();
        return ResponseEntity.ok(proveedores);
    }

    @PostMapping
    public ResponseEntity<Proveedor> crearProveedor(@RequestBody Proveedor proveedor) {
        proveedorRepository.save(proveedor);
        return ResponseEntity.ok(proveedor);
    }
}
