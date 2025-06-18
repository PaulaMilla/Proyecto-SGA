package com.mednova.inventarios_service.controller;

import com.mednova.inventarios_service.model.Inventario;
import com.mednova.inventarios_service.model.Producto;
import com.mednova.inventarios_service.repository.ProductoRepository;
import com.mednova.inventarios_service.service.InventarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventarios")
@CrossOrigin(origins = "*")
public class InventarioController {

    private final InventarioService inventarioService;
    private final ProductoRepository productoRepository;

    public InventarioController(InventarioService inventarioService, ProductoRepository productoRepository) {
        this.inventarioService = inventarioService;
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Inventario>> getAllInventarios() {
        return ResponseEntity.ok(inventarioService.getAllInventarios());
    }

    // Endpoint para listar productos
    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> getAllProductos() {
        return ResponseEntity.ok(productoRepository.findAll());
    }

    // Endpoint para crear productos de prueba
    @PostMapping("/create-test-products")
    public ResponseEntity<String> createTestProducts() {
        try {
            // Crear productos de prueba si no existen
            if (productoRepository.count() == 0) {
                Producto producto1 = new Producto();
                producto1.setNombre("Paracetamol 500mg");
                producto1.setDescripcion("Analgésico y antipirético");
                producto1.setLaboratorio("Genérico");
                producto1.setTipo("Pastilla");
                producto1.setPrecio_unitario(5.50);
                productoRepository.save(producto1);

                Producto producto2 = new Producto();
                producto2.setNombre("Ibuprofeno 400mg");
                producto2.setDescripcion("Antiinflamatorio no esteroideo");
                producto2.setLaboratorio("Genérico");
                producto2.setTipo("Pastilla");
                producto2.setPrecio_unitario(4.80);
                productoRepository.save(producto2);

                Producto producto3 = new Producto();
                producto3.setNombre("Jarabe para la tos");
                producto3.setDescripcion("Antitusivo");
                producto3.setLaboratorio("Farmacéutica ABC");
                producto3.setTipo("Jarabe");
                producto3.setPrecio_unitario(12.50);
                productoRepository.save(producto3);

                return ResponseEntity.ok("Productos de prueba creados exitosamente. Total: " + productoRepository.count());
            } else {
                return ResponseEntity.ok("Ya existen productos en la base de datos. Total: " + productoRepository.count());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al crear productos de prueba: " + e.getMessage());
        }
    }

    // Endpoint de prueba para diagnosticar problemas
    @PostMapping("/test-upload")
    public ResponseEntity<String> testUpload(@RequestParam("file") MultipartFile file) {
        System.out.println("=== PRUEBA DE CARGA DE ARCHIVO ===");
        System.out.println("Nombre del archivo: " + file.getOriginalFilename());
        System.out.println("Tamaño: " + file.getSize() + " bytes");
        System.out.println("Tipo de contenido: " + file.getContentType());
        System.out.println("¿Está vacío?: " + file.isEmpty());
        
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("El archivo está vacío");
        }
        
        return ResponseEntity.ok("Archivo recibido correctamente: " + file.getOriginalFilename());
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadInventario(@RequestParam("file") MultipartFile file) {
        System.out.println("=== CARGA DE INVENTARIO ===");
        System.out.println("Recibido archivo: " + file.getOriginalFilename());
        System.out.println("Tamaño del archivo: " + file.getSize() + " bytes");
        System.out.println("Tipo de contenido: " + file.getContentType());
        
        try {
            // Validaciones básicas del archivo
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: El archivo está vacío");
            }
            
            String fileName = file.getOriginalFilename();
            if (fileName == null || fileName.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Nombre de archivo no válido");
            }
            
            if (!fileName.toLowerCase().endsWith(".csv") && !fileName.toLowerCase().endsWith(".xlsx")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Solo se aceptan archivos CSV y XLSX. Archivo recibido: " + fileName);
            }
            
            System.out.println("Iniciando procesamiento del archivo...");
            inventarioService.processFile(file);
            System.out.println("Archivo procesado exitosamente");
            return ResponseEntity.ok("Archivo procesado exitosamente.");
            
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al procesar archivo: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PostMapping
    public Inventario createInventario(@RequestBody Inventario inventario) {
        return inventarioService.saveInventario(inventario);
    }

    @GetMapping("/{id}")
    public Optional<Inventario> getInventarioById(@PathVariable int id) {
        return inventarioService.getInventarioById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteInventario(@PathVariable int id) {
        inventarioService.deleteInventario(id);
    }
}