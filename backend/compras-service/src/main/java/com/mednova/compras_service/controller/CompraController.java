package com.mednova.compras_service.controller;

import com.mednova.compras_service.dto.CompraDTO;
import com.mednova.compras_service.model.Compra;
import com.mednova.compras_service.model.Proveedor;
import com.mednova.compras_service.service.CompraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class CompraController {
    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Compra> crearCompraConArchivo(
            @RequestPart("compra") CompraDTO dto,
            @RequestPart(name = "file", required = false) MultipartFile file
    ) {
        Compra compra = compraService.mapearCompra(dto);
        Compra creada = compraService.crearCompra(compra, dto.getBodega(), dto.getFarmaciaId());

        if (file != null && !file.isEmpty()) {
            try {
                compraService.guardarArchivoFactura(creada.getId(), file);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(null);
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PostMapping("/{id}/documento")
    public ResponseEntity<String> subirDocumentoCompra(
            @PathVariable int id,
            @RequestPart("file") MultipartFile file
    ) {
        try {
            compraService.guardarArchivoFactura(id, file);
            return ResponseEntity.ok("Archivo guardado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el archivo: " + e.getMessage());
        }
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

    @GetMapping("/proveedores")
    public ResponseEntity<List<Proveedor>> obtenerProveedores() {
        return ResponseEntity.ok(compraService.listarProveedores());
    }

}
