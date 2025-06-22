package com.mednova.inventarios_service.service;

import com.mednova.inventarios_service.dto.InventarioProductoDTO;
import com.mednova.inventarios_service.model.Inventario;
import com.mednova.inventarios_service.model.Producto;
import com.mednova.inventarios_service.repository.InventarioRepository;
import com.mednova.inventarios_service.repository.ProductoRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {
    private final InventarioRepository inventarioRepository;
    private final ProductoRepository productoRepository;

    public InventarioService(InventarioRepository inventarioRepository, ProductoRepository productoRepository) {
        this.inventarioRepository = inventarioRepository;
        this.productoRepository = productoRepository;
    }

    public void processFile(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío.");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new IllegalArgumentException("Nombre de archivo no válido.");
        }

        if (fileName.toLowerCase().endsWith(".csv")) {
            processCSV(file);
        } else if (fileName.toLowerCase().endsWith(".xlsx")) {
            processExcel(file);
        } else {
            throw new IllegalArgumentException("Formato no soportado. Solo se aceptan archivos CSV y XLSX.");
        }
    }

    private void processCSV(MultipartFile file) throws IOException {
        List<String> errors = new ArrayList<>();
        int processedRows = 0;
        int totalRows = 0;
        int skippedRows = 0;

        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            CSVReader csvReader = new CSVReader(reader);
            List<String[]> rows = csvReader.readAll();

            if (rows.isEmpty()) {
                throw new IllegalArgumentException("El archivo CSV está vacío.");
            }

            totalRows = rows.size() - 1; // Excluir header

            // Validar header
            String[] header = rows.get(0);
            validateCSVHeader(header);

            // Obtener lista de productos disponibles para mostrar en errores
            List<Producto> productosDisponibles = productoRepository.findAll();
            StringBuilder productosInfo = new StringBuilder();
            for (Producto p : productosDisponibles) {
                productosInfo.append(p.getId_producto()).append(" (").append(p.getNombre()).append("), ");
            }

            for (int i = 1; i < rows.size(); i++) {
                String[] row = rows.get(i);
                try {
                    processCSVRow(row, i + 1);
                    processedRows++;
                } catch (IllegalArgumentException e) {
                    // Si es error de producto no encontrado, agregar información útil
                    if (e.getMessage().contains("no existe en la base de datos")) {
                        errors.add("Fila " + (i + 1) + ": " + e.getMessage() +
                            ". Productos disponibles: " + productosInfo.toString());
                    } else {
                        errors.add("Fila " + (i + 1) + ": " + e.getMessage());
                    }
                    skippedRows++;
                } catch (Exception e) {
                    errors.add("Fila " + (i + 1) + ": Error inesperado - " + e.getMessage());
                    skippedRows++;
                }
            }
        } catch (CsvException e) {
            throw new RuntimeException("Error al leer el archivo CSV: " + e.getMessage());
        }

        // Reportar resultados
        StringBuilder result = new StringBuilder();
        result.append("Procesamiento completado. ");
        result.append("Filas procesadas: ").append(processedRows).append("/").append(totalRows).append(". ");

        if (skippedRows > 0) {
            result.append("Filas omitidas: ").append(skippedRows).append(". ");
        }

        if (!errors.isEmpty()) {
            result.append("Errores: ").append(String.join("; ", errors));
            throw new RuntimeException(result.toString());
        }

        if (processedRows == 0) {
            throw new RuntimeException("No se procesó ninguna fila. Verifique que los IDs de productos existan en la base de datos.");
        }
    }

    private void validateCSVHeader(String[] header) {
        if (header.length < 5) {
            throw new IllegalArgumentException("El archivo debe tener al menos 5 columnas. Columnas encontradas: " + header.length);
        }
        
        String[] expectedColumns = {"id_producto", "cantidad_disponible", "ubicacion", "lote", "fecha_vencimiento","nombre_farmacia"};
        for (int i = 0; i < expectedColumns.length; i++) {
            if (i >= header.length || !header[i].trim().equalsIgnoreCase(expectedColumns[i])) {
                throw new IllegalArgumentException("Columna " + (i + 1) + " debe ser '" + expectedColumns[i] + "'. Encontrada: " + 
                    (i < header.length ? header[i] : "faltante"));
            }
        }
    }

    private void processCSVRow(String[] row, int rowNumber) throws Exception {
        if (row.length < 6) {
            throw new IllegalArgumentException("La fila debe tener al menos 6 columnas incluyendo nombre_farmacia");
        }

        int idProducto;
        try {
            idProducto = Integer.parseInt(row[0].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID de producto debe ser un número válido");
        }

        Optional<Producto> productoOpt = productoRepository.findById(idProducto);
        if (productoOpt.isEmpty()) {
            throw new IllegalArgumentException("El producto con ID " + idProducto + " no existe en la base de datos");
        }

        int cantidadDisponible;
        try {
            cantidadDisponible = Integer.parseInt(row[1].trim());
            if (cantidadDisponible < 0) {
                throw new IllegalArgumentException("La cantidad disponible no puede ser negativa");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cantidad disponible debe ser un número válido");
        }

        String ubicacion = row[2].trim();
        String lote = row[3].trim();
        String fechaVencimiento = row[4].trim();
        String nombreFarmacia = row[5].trim();

        if (ubicacion.isEmpty() || lote.isEmpty() || fechaVencimiento.isEmpty() || nombreFarmacia.isEmpty()) {
            throw new IllegalArgumentException("Ninguno de los campos puede estar vacío");
        }

        // Verificar si ya existe inventario para ese producto, lote y farmacia
        Optional<Inventario> inventarioExistenteOpt = inventarioRepository
                .findByProductoYLoteYFarmacia(idProducto, lote, nombreFarmacia);

        if (inventarioExistenteOpt.isPresent()) {
            // Actualizar cantidad existente
            Inventario inventarioExistente = inventarioExistenteOpt.get();
            inventarioExistente.setCantidad_disponible(
                    inventarioExistente.getCantidad_disponible() + cantidadDisponible);
            inventarioRepository.save(inventarioExistente);
        } else {
            // Crear nuevo inventario
            Inventario nuevoInventario = new Inventario();
            nuevoInventario.setId_producto(idProducto);
            nuevoInventario.setCantidad_disponible(cantidadDisponible);
            nuevoInventario.setUbicacion(ubicacion);
            nuevoInventario.setLote(lote);
            nuevoInventario.setFecha_vencimiento(fechaVencimiento);
            nuevoInventario.setNombre_farmacia(nombreFarmacia);
            inventarioRepository.save(nuevoInventario);
        }
    }

    private void processExcel(MultipartFile file) throws IOException {
        List<String> errors = new ArrayList<>();
        int processedRows = 0;
        int totalRows = 0;

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            if (sheet.getPhysicalNumberOfRows() == 0) {
                throw new IllegalArgumentException("El archivo Excel está vacío.");
            }

            totalRows = sheet.getPhysicalNumberOfRows() - 1; // Excluir header

            // Validar header
            Row headerRow = sheet.getRow(0);
            validateExcelHeader(headerRow);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    try {
                        processExcelRow(row, i + 1);
                        processedRows++;
                    } catch (Exception e) {
                        errors.add("Fila " + (i + 1) + ": " + e.getMessage());
                    }
                }
            }
        }

        // Reportar resultados
        if (!errors.isEmpty()) {
            throw new RuntimeException("Procesamiento completado con errores. Filas procesadas: " +
                processedRows + "/" + totalRows + ". Errores: " + String.join("; ", errors));
        }
    }

    private void validateExcelHeader(Row headerRow) {
        if (headerRow == null || headerRow.getLastCellNum() < 5) {
            throw new IllegalArgumentException("El archivo debe tener al menos 5 columnas.");
        }

        String[] expectedColumns = {"id_producto", "cantidad_disponible", "ubicacion", "lote", "fecha_vencimiento","nombre_farmacia"};
        for (int i = 0; i < expectedColumns.length; i++) {
            Cell cell = headerRow.getCell(i);
            if (cell == null || !cell.getStringCellValue().trim().equalsIgnoreCase(expectedColumns[i])) {
                throw new IllegalArgumentException("Columna " + (i + 1) + " debe ser '" + expectedColumns[i] + "'");
            }
        }
    }

    private void processExcelRow(Row row, int rowNumber) throws Exception {
        if (row.getLastCellNum() < 6) {
            throw new IllegalArgumentException("La fila debe tener al menos 6 columnas incluyendo nombre_farmacia");
        }

        // ID del producto
        Cell idProductoCell = row.getCell(0);
        int idProducto;
        if (idProductoCell == null) {
            throw new IllegalArgumentException("ID de producto no puede estar vacío");
        }

        if (idProductoCell.getCellType() == CellType.NUMERIC) {
            idProducto = (int) idProductoCell.getNumericCellValue();
        } else {
            idProducto = Integer.parseInt(idProductoCell.getStringCellValue().trim());
        }

        Optional<Producto> productoOpt = productoRepository.findById(idProducto);
        if (productoOpt.isEmpty()) {
            throw new IllegalArgumentException("El producto con ID " + idProducto + " no existe en la base de datos");
        }

        // Cantidad disponible
        Cell cantidadCell = row.getCell(1);
        int cantidadDisponible;
        if (cantidadCell == null) {
            throw new IllegalArgumentException("Cantidad disponible no puede estar vacía");
        }

        if (cantidadCell.getCellType() == CellType.NUMERIC) {
            cantidadDisponible = (int) cantidadCell.getNumericCellValue();
        } else {
            cantidadDisponible = Integer.parseInt(cantidadCell.getStringCellValue().trim());
        }

        if (cantidadDisponible < 0) {
            throw new IllegalArgumentException("La cantidad disponible no puede ser negativa");
        }

        // Ubicación
        Cell ubicacionCell = row.getCell(2);
        String ubicacion = (ubicacionCell != null) ? ubicacionCell.getStringCellValue().trim() : "";
        if (ubicacion.isEmpty()) {
            throw new IllegalArgumentException("La ubicación no puede estar vacía");
        }

        // Lote
        Cell loteCell = row.getCell(3);
        String lote = (loteCell != null) ? loteCell.getStringCellValue().trim() : "";
        if (lote.isEmpty()) {
            throw new IllegalArgumentException("El lote no puede estar vacío");
        }

        // Fecha de vencimiento
        Cell fechaCell = row.getCell(4);
        String fechaVencimiento;
        if (fechaCell == null) {
            throw new IllegalArgumentException("La fecha de vencimiento no puede estar vacía");
        }

        if (fechaCell.getCellType() == CellType.STRING) {
            fechaVencimiento = fechaCell.getStringCellValue().trim();
        } else {
            fechaVencimiento = fechaCell.getDateCellValue().toString(); // Puedes formatear si quieres
        }

        // Nombre farmacia
        Cell farmaciaCell = row.getCell(5);
        String nombreFarmacia = (farmaciaCell != null) ? farmaciaCell.getStringCellValue().trim() : "";
        if (nombreFarmacia.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la farmacia no puede estar vacío");
        }

        // Verificar si ya existe inventario para ese producto, lote y farmacia
        Optional<Inventario> inventarioExistenteOpt = inventarioRepository
                .findByProductoYLoteYFarmacia(idProducto, lote, nombreFarmacia);

        if (inventarioExistenteOpt.isPresent()) {
            // Actualizar cantidad existente
            Inventario inventarioExistente = inventarioExistenteOpt.get();
            inventarioExistente.setCantidad_disponible(
                    inventarioExistente.getCantidad_disponible() + cantidadDisponible);
            inventarioRepository.save(inventarioExistente);
        } else {
            // Crear nuevo inventario
            Inventario nuevoInventario = new Inventario();
            nuevoInventario.setId_producto(idProducto);
            nuevoInventario.setCantidad_disponible(cantidadDisponible);
            nuevoInventario.setUbicacion(ubicacion);
            nuevoInventario.setLote(lote);
            nuevoInventario.setFecha_vencimiento(fechaVencimiento);
            nuevoInventario.setNombre_farmacia(nombreFarmacia);
            inventarioRepository.save(nuevoInventario);
        }
    }

    public List<InventarioProductoDTO> obtenerInventarioConProducto() {
        List<Inventario> inventarios = inventarioRepository.findAll();
        List<InventarioProductoDTO> resultado = new ArrayList<>();

        for (Inventario inv : inventarios) {
            Optional<Producto> productoOpt = productoRepository.findById(inv.getId_producto());

            if (productoOpt.isPresent()) {
                Producto prod = productoOpt.get();
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
                dto.setNombre_farmacia(inv.getNombre_farmacia());

                resultado.add(dto);
            }
        }

        return resultado;
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

    public void deleteInventario(int id) {
        inventarioRepository.deleteById(id);
    }
}
