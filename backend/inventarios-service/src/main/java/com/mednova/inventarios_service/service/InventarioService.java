package com.mednova.inventarios_service.service;

import com.mednova.inventarios_service.dto.DescuentoStockRequest;
import com.mednova.inventarios_service.dto.FarmaciaResponseDTO;
import com.mednova.inventarios_service.dto.InventarioProductoDTO;
import com.mednova.inventarios_service.dto.DispersarRequest;
import com.mednova.inventarios_service.model.Farmacia;
import com.mednova.inventarios_service.model.Inventario;
import com.mednova.inventarios_service.model.Producto;
import com.mednova.inventarios_service.model.Dispensacion;
import com.mednova.inventarios_service.repository.FarmaciaRepository;
import com.mednova.inventarios_service.repository.InventarioRepository;
import com.mednova.inventarios_service.repository.ProductoRepository;
import com.mednova.inventarios_service.repository.DispensacionRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import com.mednova.inventarios_service.dto.FraccionamientoRequest;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.time.LocalDate;

@Service
public class InventarioService {

    private final WebClient webClient;
    private final InventarioRepository inventarioRepository;
    private final ProductoRepository productoRepository;
    private final FarmaciaRepository farmaciaRepository;
    private final DispensacionRepository dispensacionRepository;


    public InventarioService(WebClient.Builder builder, InventarioRepository inventarioRepository,
                             ProductoRepository productoRepository,
                             FarmaciaRepository farmaciaRepository,
                             DispensacionRepository dispensacionRepository) {
        this.webClient = builder.baseUrl("http://usuarios-service.usuarios.svc.cluster.local").build();
        this.inventarioRepository = inventarioRepository;
        this.productoRepository = productoRepository;
        this.farmaciaRepository = farmaciaRepository;
        this.dispensacionRepository = dispensacionRepository;
    }

    public void processPreciosFile(MultipartFile file) throws Exception {
        if (file.isEmpty()) throw new IllegalArgumentException("El archivo está vacío.");

        String fileName = file.getOriginalFilename();
        if (fileName == null) throw new IllegalArgumentException("Nombre de archivo no válido.");

        if (fileName.toLowerCase().endsWith(".csv")) {
            processPreciosCSV(file);
        } else if (fileName.toLowerCase().endsWith(".xlsx")) {
            processPreciosExcel(file);
        } else {
            throw new IllegalArgumentException("Formato no soportado. Solo CSV y XLSX permitidos.");
        }
    }

    private void processPreciosCSV(MultipartFile file) throws IOException {
        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVReader csvReader = new CSVReader(reader)) {

            List<String[]> rows = csvReader.readAll();
            if (rows.isEmpty()) throw new IllegalArgumentException("El archivo CSV está vacío.");

            validatePreciosCSVHeader(rows.get(0));
            for (int i = 1; i < rows.size(); i++) {
                String[] row = rows.get(i);
                processPreciosRow(row[0], row[1], i + 1);
            }

        } catch (CsvException e) {
            throw new RuntimeException("Error al leer el archivo CSV: " + e.getMessage());
        }
    }

    private void processPreciosExcel(MultipartFile file) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet.getPhysicalNumberOfRows() == 0)
                throw new IllegalArgumentException("El archivo Excel está vacío.");

            validatePreciosExcelHeader(sheet.getRow(0));
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    String idProd = getCellAsString(row.getCell(0));
                    String precio_unitario = getCellAsString(row.getCell(1));

                    processPreciosRow(idProd, precio_unitario, i + 1);
                }
            }
        }
    }

    private void validatePreciosCSVHeader(String[] header) {
        String[] expected = {"id_producto", "precio_unitario"};
        for (int i = 0; i < expected.length; i++) {
            if (!header[i].trim().equalsIgnoreCase(expected[i])) {
                throw new IllegalArgumentException("Columna " + (i + 1) + " debe ser '" + expected[i] + "'");
            }
        }
    }

    private void validatePreciosExcelHeader(Row headerRow) {
        String[] expected = {"id_producto", "precio_unitario"};
        for (int i = 0; i < expected.length; i++) {
            Cell cell = headerRow.getCell(i);
            if (cell == null || !cell.getStringCellValue().trim().equalsIgnoreCase(expected[i])) {
                throw new IllegalArgumentException("Columna " + (i + 1) + " debe ser '" + expected[i] + "'");
            }
        }
    }

    private void processPreciosRow(String idProdStr, String precioUnitStr, int fila) {
        try {
            int idProducto = Integer.parseInt(idProdStr.trim());
            double nuevoPrecio = Double.parseDouble(precioUnitStr.trim());

            if (nuevoPrecio < 0) {
                throw new IllegalArgumentException("El precio no puede ser negativo.");
            }

            Producto producto = productoRepository.findById(idProducto)
                    .orElseThrow(() -> new IllegalArgumentException("Producto con ID " + idProducto + " no existe."));

            producto.setPrecio_unitario(nuevoPrecio); // 👈 Asignamos nuevo precio
            productoRepository.save(producto);        // 👈 Guardamos el producto actualizado

        } catch (Exception e) {
            throw new RuntimeException("Error en fila " + fila + ": " + e.getMessage());
        }
    }


    public void processFile(MultipartFile file, String tipoInventario, String emailUsuario) throws Exception {
        if (file.isEmpty()) throw new IllegalArgumentException("El archivo está vacío.");
        if (emailUsuario == null || emailUsuario.isEmpty()) {
            throw new IllegalArgumentException("El email del usuario es obligatorio.");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null) throw new IllegalArgumentException("Nombre de archivo no válido.");

        // Buscar nombre de la farmacia del usuario llamando al microservicio de usuarios
        String nombreFarmacia = obtenerNombreFarmaciaDesdeUsuario(emailUsuario);
        Farmacia farmacia = farmaciaRepository.findByNombre(nombreFarmacia)
                .orElseThrow(() -> new IllegalArgumentException("La farmacia asociada al usuario no existe: " + nombreFarmacia));


        String tipo = tipoInventario.toLowerCase();
        if (!List.of("general", "selectivo", "barrido").contains(tipo))
            throw new IllegalArgumentException("Tipo de inventario no soportado: " + tipoInventario);

        if (tipo.equals("general")) inventarioRepository.resetearCantidades();

        if (fileName.toLowerCase().endsWith(".csv")) {
            processCSV(file, tipo, farmacia);
        } else if (fileName.toLowerCase().endsWith(".xlsx")) {
            processExcel(file, tipo, farmacia);
        } else {
            throw new IllegalArgumentException("Formato no soportado. Solo CSV y XLSX permitidos.");
        }
    }

    private void processCSV(MultipartFile file, String tipoInventario,Farmacia farmacia) throws IOException {
        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVReader csvReader = new CSVReader(reader)) {

            List<String[]> rows = csvReader.readAll();
            if (rows.isEmpty()) throw new IllegalArgumentException("El archivo CSV está vacío.");

            String nombreFarmacia = farmacia.getNombre();
            validateCSVHeader(rows.get(0));
            for (int i = 1; i < rows.size(); i++) {
                String[] row = rows.get(i);
                processRow(row[0], row[1], row[2], row[3], row[4], nombreFarmacia, tipoInventario, i + 1);
            }

        } catch (CsvException e) {
            throw new RuntimeException("Error al leer el archivo CSV: " + e.getMessage());
        }
    }

    private void processExcel(MultipartFile file, String tipoInventario, Farmacia farmacia) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet.getPhysicalNumberOfRows() == 0)
                throw new IllegalArgumentException("El archivo Excel está vacío.");

            validateExcelHeader(sheet.getRow(0));
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    String idProd = getCellAsString(row.getCell(0));
                    String cantidad = getCellAsString(row.getCell(1));
                    String ubicacion = getCellAsString(row.getCell(2));
                    String lote = getCellAsString(row.getCell(3));
                    String fecha = getCellAsString(row.getCell(4));
                    String nombreFarmacia = farmacia.getNombre();

                    processRow(idProd, cantidad, ubicacion, lote, fecha, nombreFarmacia, tipoInventario, i + 1);
                }
            }
        }
    }

    private void processRow(String idProdStr, String cantidadStr, String ubicacion, String lote, String fecha,
                            String nombreFarmacia, String tipoInventario, int fila) {
        try {
            int idProducto = Integer.parseInt(idProdStr.trim());
            int cantidad = Integer.parseInt(cantidadStr.trim());

            if (cantidad < 0 || ubicacion.isEmpty() || lote.isEmpty() || fecha.isEmpty() || nombreFarmacia.isEmpty())
                throw new IllegalArgumentException("Campos vacíos o inválidos en la fila " + fila);

            Producto producto = productoRepository.findById(idProducto)
                    .orElseThrow(() -> new IllegalArgumentException("Producto con ID " + idProducto + " no existe."));

            Farmacia farmacia = farmaciaRepository.findByNombre(nombreFarmacia)
                    .orElseThrow(() -> new IllegalArgumentException("Farmacia '" + nombreFarmacia + "' no existe."));

            Optional<Inventario> inventarioOpt = inventarioRepository.findByProductoAndLoteAndFarmacia(producto, lote, farmacia);

            if (inventarioOpt.isPresent()) {
                Inventario inv = inventarioOpt.get();
                if (tipoInventario.equals("general") || tipoInventario.equals("barrido")) {
                    inv.setCantidad_disponible(cantidad);
                } else {
                    inv.setCantidad_disponible(inv.getCantidad_disponible() + cantidad);
                }
                inventarioRepository.save(inv);
            } else {
                Inventario nuevo = new Inventario();
                nuevo.setProducto(producto);
                nuevo.setFarmacia(farmacia);
                nuevo.setCantidad_disponible(cantidad);
                nuevo.setUbicacion(ubicacion);
                nuevo.setLote(lote);
                nuevo.setFecha_vencimiento(fecha);
                inventarioRepository.save(nuevo);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error en fila " + fila + ": " + e.getMessage());
        }
    }

    private void validateCSVHeader(String[] header) {
        String[] expected = {"id_producto", "cantidad_disponible", "ubicacion", "lote", "fecha_vencimiento"};
        for (int i = 0; i < expected.length; i++) {
            if (!header[i].trim().equalsIgnoreCase(expected[i])) {
                throw new IllegalArgumentException("Columna " + (i + 1) + " debe ser '" + expected[i] + "'");
            }
        }
    }

    private void validateExcelHeader(Row headerRow) {
        String[] expected = {"id_producto", "cantidad_disponible", "ubicacion", "lote", "fecha_vencimiento"};
        for (int i = 0; i < expected.length; i++) {
            Cell cell = headerRow.getCell(i);
            if (cell == null || !cell.getStringCellValue().trim().equalsIgnoreCase(expected[i])) {
                throw new IllegalArgumentException("Columna " + (i + 1) + " debe ser '" + expected[i] + "'");
            }
        }
    }

    private String getCellAsString(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }

    public List<InventarioProductoDTO> obtenerInventarioConProducto(String emailUsuario) {
        String nombre_farmacia = obtenerNombreFarmaciaDesdeUsuario(emailUsuario);

        Farmacia farmacia = farmaciaRepository.findByNombre(nombre_farmacia)
                .orElseThrow(() -> new IllegalArgumentException("Farmacia '" + nombre_farmacia + "' no existe."));

        List<Inventario> inventarios = inventarioRepository.findByFarmacia(farmacia); // ← Solo los de esta farmacia

        List<InventarioProductoDTO> resultado = new ArrayList<>();

        for (Inventario inv : inventarios) {
            Producto prod = inv.getProducto();

            InventarioProductoDTO dto = new InventarioProductoDTO();
            dto.setId_inventario(inv.getId_inventario());
            dto.setId_producto(prod.getId_producto());
            dto.setNombre(prod.getNombre());
            dto.setDescripcion(prod.getDescripcion());
            dto.setLaboratorio(prod.getLaboratorio());
            dto.setTipo(prod.getTipo());
            dto.setPrecio_unitario(prod.getPrecio_unitario());
            dto.setCantidad_disponible(inv.getCantidad_disponible());
            dto.setUbicacion(inv.getUbicacion());
            dto.setLote(inv.getLote());
            dto.setFecha_vencimiento(inv.getFecha_vencimiento());
            dto.setNombre_farmacia(farmacia.getNombre());

            resultado.add(dto);
        }

        return resultado;
    }

    public void realizarFraccionamiento(FraccionamientoRequest request) {
        Inventario inventario = inventarioRepository.findById(request.getIdInventario())
                .orElseThrow(() -> new IllegalArgumentException("Inventario no encontrado"));

        if (request.getCantidadFraccionada() <= 0 || request.getCantidadFraccionada() > inventario.getCantidad_disponible()) {
            throw new IllegalArgumentException("Cantidad inválida para fraccionar");
        }

        // Reducir inventario original
        inventario.setCantidad_disponible(inventario.getCantidad_disponible() - request.getCantidadFraccionada());
        inventarioRepository.save(inventario);

        // Crear nuevo inventario fraccionado
        Inventario fraccionado = new Inventario();
        fraccionado.setProducto(inventario.getProducto());
        fraccionado.setFarmacia(inventario.getFarmacia());
        fraccionado.setCantidad_disponible(request.getCantidadFraccionada());
        fraccionado.setUbicacion(inventario.getUbicacion());
        fraccionado.setLote(request.getNuevoLote());
        fraccionado.setFecha_vencimiento(inventario.getFecha_vencimiento());

        inventarioRepository.save(fraccionado);
    }

    @Transactional
    public void dispersarMedicamento(DispersarRequest request) {
            Producto producto = productoRepository.findById(request.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        String nombreFarmacia = obtenerNombreFarmaciaDesdeUsuario(request.getEmailUsuario());
        Farmacia farmacia = farmaciaRepository.findByNombre(nombreFarmacia)
                .orElseThrow(() -> new RuntimeException("Farmacia no encontrada"));

        Inventario inventario = inventarioRepository.findByproducto(producto)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        int cantidadNueva = inventario.getCantidad_disponible() - request.getCantidad();
        if (cantidadNueva < 0) {
            throw new RuntimeException("Stock insuficiente para el producto.");
        }

        inventario.setCantidad_disponible(cantidadNueva);
        inventarioRepository.save(inventario);

        Dispensacion dispensacion = new Dispensacion();
        dispensacion.setProducto(producto);
        dispensacion.setFarmacia(farmacia);
        dispensacion.setCantidad(request.getCantidad());
        dispensacion.setRutPaciente(request.getRutPaciente());
        dispensacion.setNombrePaciente(request.getRutPaciente());
        dispensacion.setFechaDispensacion(LocalDate.now());

        dispensacionRepository.save(dispensacion);
    }



    private String obtenerNombreFarmaciaDesdeUsuario(String emailUsuario) {
        try {
            String nombreFarmacia = webClient.get()
                    .uri("/api/usuarios/nombre-farmacia/" + emailUsuario)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            if (nombreFarmacia == null || nombreFarmacia.isEmpty()) {
                throw new RuntimeException("Nombre de farmacia no disponible para el usuario: " + emailUsuario);
            }

            return nombreFarmacia;

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar nombre de farmacia: " + e.getMessage());
        }
    }

    @Transactional
    public void descontarStock(DescuentoStockRequest request) {
        Producto productoEntity = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Inventario inventario = inventarioRepository.findByproducto(productoEntity)
                .orElseThrow(() -> new RuntimeException(
                        "Inventario no encontrado para el producto " + request.getProductoId()));

        int nuevaCantidad = inventario.getCantidad_disponible() - request.getCantidad();

        if (nuevaCantidad < 0) {
            throw new RuntimeException("Stock insuficiente para producto " + request.getProductoId());
        }

        inventario.setCantidad_disponible(nuevaCantidad);
        inventarioRepository.save(inventario);
    }


    public List<Inventario> getAllInventarios() {
        return inventarioRepository.findAll();
    }

    public Optional<Inventario> getInventarioById(int id) {
        return inventarioRepository.findById(id);
    }

    public Inventario saveInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    public List<Farmacia> getAllFarmacias() { return farmaciaRepository.findAll(); }

    public Farmacia saveFarmacia(Farmacia farmacia) {
        return farmaciaRepository.save(farmacia);
    }

    public void deleteInventario(int id) {
        inventarioRepository.deleteById(id);
    }

    public void deleteAllInventarios() {
        inventarioRepository.deleteAll(); // Este método debes crearlo en el repo
    }

    public void deleteAllFarmacias() {
        farmaciaRepository.deleteAll();
    }

    public Optional<FarmaciaResponseDTO> getFarmaciaById(int id) {
        Optional<Farmacia> opt = farmaciaRepository.findById(id);
        if (opt.isPresent()) {
            Farmacia farmacia = opt.get();
            FarmaciaResponseDTO dto = new FarmaciaResponseDTO();
            dto.setId(farmacia.getId());
            dto.setNombre(farmacia.getNombre());
            dto.setDireccion(farmacia.getDireccion());
            dto.setComuna(farmacia.getComuna());
            dto.setTelefono(farmacia.getTelefono());
            dto.setEmailContacto(farmacia.getEmailContacto());
            dto.setHorarioAtencion(farmacia.getHorarioAtencion());
            dto.setActivo(farmacia.isActivo());
            return Optional.of(dto);
        }
        return Optional.empty();
    }
}
