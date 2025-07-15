package com.mednova.inventarios_service.controller;

import com.mednova.inventarios_service.dto.InventarioProductoDTO;
import com.mednova.inventarios_service.model.Farmacia;
import com.mednova.inventarios_service.model.Inventario;
import com.mednova.inventarios_service.model.Producto;
import com.mednova.inventarios_service.repository.ProductoRepository;
import com.mednova.inventarios_service.service.InventarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventarios")
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

    // Endpoint para mostrar productos con formato legible
    @GetMapping("/productos-info")
    public ResponseEntity<String> getProductosInfo() {
        try {
            List<Producto> productos = productoRepository.findAll();
            if (productos.isEmpty()) {
                return ResponseEntity.ok("No hay productos en la base de datos. Ejecuta /create-test-products primero.");
            }
            
            StringBuilder info = new StringBuilder();
            info.append("Productos disponibles (").append(productos.size()).append("):\n");
            for (Producto p : productos) {
                info.append("ID: ").append(p.getId_producto())
                    .append(" | Nombre: ").append(p.getNombre())
                    .append(" | Tipo: ").append(p.getTipo())
                    .append(" | Precio: $").append(p.getPrecio_unitario())
                    .append("\n");
            }
            
            return ResponseEntity.ok(info.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener información de productos: " + e.getMessage());
        }
    }

    @GetMapping("/con-producto")
    public ResponseEntity<List<InventarioProductoDTO>> getInventarioConProducto(@RequestParam("emailUsuario") String emailUsuario) {
        List<InventarioProductoDTO> lista = inventarioService.obtenerInventarioConProducto(emailUsuario);
        return ResponseEntity.ok(lista);
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
                Producto saved1 = productoRepository.save(producto1);

                Producto producto2 = new Producto();
                producto2.setNombre("Ibuprofeno 400mg");
                producto2.setDescripcion("Antiinflamatorio no esteroideo");
                producto2.setLaboratorio("Genérico");
                producto2.setTipo("Pastilla");
                producto2.setPrecio_unitario(4.80);
                Producto saved2 = productoRepository.save(producto2);

                Producto producto3 = new Producto();
                producto3.setNombre("Jarabe para la tos");
                producto3.setDescripcion("Antitusivo");
                producto3.setLaboratorio("Farmacéutica ABC");
                producto3.setTipo("Jarabe");
                producto3.setPrecio_unitario(12.50);
                Producto saved3 = productoRepository.save(producto3);

                return ResponseEntity.ok("Productos de prueba creados exitosamente. Total: " + productoRepository.count() + 
                    "\nIDs creados: " + saved1.getId_producto() + ", " + saved2.getId_producto() + ", " + saved3.getId_producto());
            } else {
                List<Producto> productos = productoRepository.findAll();
                StringBuilder ids = new StringBuilder();
                for (Producto p : productos) {
                    ids.append(p.getId_producto()).append(" (").append(p.getNombre()).append("), ");
                }
                return ResponseEntity.ok("Ya existen productos en la base de datos. Total: " + productoRepository.count() + 
                    "\nProductos existentes: " + ids.toString());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al crear productos de prueba: " + e.getMessage());
        }
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
    public ResponseEntity<Map<String, String>> uploadInventario(@RequestPart("file") MultipartFile file,
                                                                @RequestPart("tipoInventario") String tipoInventario, @RequestPart("emailUsuario") String emailUsuario) {
        Map<String, String> response = new HashMap<>();

        System.out.println("=== CARGA DE INVENTARIO ===");
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
            inventarioService.processFile(file, tipoInventario, emailUsuario);
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

    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable int id) {
        return productoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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

    @PostMapping("/crear-farmacia")
    public Farmacia crearFarmacia(@RequestBody Farmacia farmacia) {
        return inventarioService.saveFarmacia(farmacia);
    }

}