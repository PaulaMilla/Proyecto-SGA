package com.mednova.compras_service.controller;

import com.mednova.compras_service.dto.CompraDTO;
import com.mednova.compras_service.model.Compra;
import com.mednova.compras_service.service.CompraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class CompraController {
    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public ResponseEntity<Compra> crearCompra(@RequestBody CompraDTO dto) {
        Compra compra = compraService.mapearCompra(dto);
        Compra creada = compraService.crearCompra(compra, dto.getBodega(), dto.getFarmaciaId());
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping
    public ResponseEntity<List<Compra>> listarCompras() {
        return ResponseEntity.ok(compraService.listarCompras());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> obtenerCompraPorId(@PathVariable int id) {
        return compraService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/proveedor")
    public ResponseEntity<List<Compra>> buscarPorProveedor(@RequestParam String nombre) {
        return ResponseEntity.ok(compraService.buscarPorProveedor(nombre));
    }

    @PostMapping("/{id}/anular")
    public ResponseEntity<Compra> anularCompra(@PathVariable int id) {
        return ResponseEntity.ok(compraService.anularCompra(id));
    }
}
