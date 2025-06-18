package com.mednova.inventarios_service.service;

import com.mednova.inventarios_service.model.Inventario;
import com.mednova.inventarios_service.model.Producto;
import com.mednova.inventarios_service.repository.InventarioRepository;
import com.mednova.inventarios_service.repository.ProductoRepository;
import com.opencsv.CSVParser;
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

import java.io.BufferedReader;
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

            for (int i = 1; i < rows.size(); i++) {
                String[] row = rows.get(i);
                try {
                    processCSVRow(row, i + 1);
                    processedRows++;
                } catch (Exception e) {
                    errors.add("Fila " + (i + 1) + ": " + e.getMessage());
                }
            }
        } catch (CsvException e) {
            throw new RuntimeException("Error al leer el archivo CSV: " + e.getMessage());
        }

        // Reportar resultados
        if (!errors.isEmpty()) {
            throw new RuntimeException("Procesamiento completado con errores. Filas procesadas: " + 
                processedRows + "/" + totalRows + ". Errores: " + String.join("; ", errors));
        }
    }

    private void validateCSVHeader(String[] header) {
        if (header.length < 5) {
            throw new IllegalArgumentException("El archivo debe tener al menos 5 columnas. Columnas encontradas: " + header.length);
        }
        
        String[] expectedColumns = {"id_producto", "cantidad_disponible", "ubicacion", "lote", "fecha_vencimiento"};
        for (int i = 0; i < expectedColumns.length; i++) {
            if (i >= header.length || !header[i].trim().equalsIgnoreCase(expectedColumns[i])) {
                throw new IllegalArgumentException("Columna " + (i + 1) + " debe ser '" + expectedColumns[i] + "'. Encontrada: " + 
                    (i < header.length ? header[i] : "faltante"));
            }
        }
    }

    private void processCSVRow(String[] row, int rowNumber) throws Exception {
        if (row.length < 5) {
            throw new IllegalArgumentException("La fila debe tener al menos 5 columnas");
        }

        // Validar y obtener id_producto
        int idProducto;
        try {
            idProducto = Integer.parseInt(row[0].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID de producto debe ser un número válido");
        }

        // Verificar que el producto existe
        Optional<Producto> productoOpt = productoRepository.findById(idProducto);
        if (productoOpt.isEmpty()) {
            throw new IllegalArgumentException("El producto con ID " + idProducto + " no existe en la base de datos");
        }

        // Validar cantidad disponible
        int cantidadDisponible;
        try {
            cantidadDisponible = Integer.parseInt(row[1].trim());
            if (cantidadDisponible < 0) {
                throw new IllegalArgumentException("La cantidad disponible no puede ser negativa");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cantidad disponible debe ser un número válido");
        }

        // Validar campos de texto
        String ubicacion = row[2].trim();
        if (ubicacion.isEmpty()) {
            throw new IllegalArgumentException("La ubicación no puede estar vacía");
        }

        String lote = row[3].trim();
        if (lote.isEmpty()) {
            throw new IllegalArgumentException("El lote no puede estar vacío");
        }

        String fechaVencimiento = row[4].trim();
        if (fechaVencimiento.isEmpty()) {
            throw new IllegalArgumentException("La fecha de vencimiento no puede estar vacía");
        }

        // Crear y guardar el inventario
        Inventario inventario = new Inventario();
        inventario.setId_producto(idProducto);
        inventario.setCantidad_disponible(cantidadDisponible);
        inventario.setUbicacion(ubicacion);
        inventario.setLote(lote);
        inventario.setFecha_vencimiento(fechaVencimiento);

        inventarioRepository.save(inventario);
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

        String[] expectedColumns = {"id_producto", "cantidad_disponible", "ubicacion", "lote", "fecha_vencimiento"};
        for (int i = 0; i < expectedColumns.length; i++) {
            Cell cell = headerRow.getCell(i);
            if (cell == null || !cell.getStringCellValue().trim().equalsIgnoreCase(expectedColumns[i])) {
                throw new IllegalArgumentException("Columna " + (i + 1) + " debe ser '" + expectedColumns[i] + "'");
            }
        }
    }

    private void processExcelRow(Row row, int rowNumber) throws Exception {
        if (row.getLastCellNum() < 5) {
            throw new IllegalArgumentException("La fila debe tener al menos 5 columnas");
        }

        // Validar y obtener id_producto
        Cell idProductoCell = row.getCell(0);
        int idProducto;
        if (idProductoCell == null) {
            throw new IllegalArgumentException("ID de producto no puede estar vacío");
        }
        
        if (idProductoCell.getCellType() == CellType.NUMERIC) {
            idProducto = (int) idProductoCell.getNumericCellValue();
        } else if (idProductoCell.getCellType() == CellType.STRING) {
            try {
                idProducto = Integer.parseInt(idProductoCell.getStringCellValue().trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("ID de producto debe ser un número válido");
            }
        } else {
            throw new IllegalArgumentException("ID de producto debe ser un número");
        }

        // Verificar que el producto existe
        Optional<Producto> productoOpt = productoRepository.findById(idProducto);
        if (productoOpt.isEmpty()) {
            throw new IllegalArgumentException("El producto con ID " + idProducto + " no existe en la base de datos");
        }

        // Validar cantidad disponible
        Cell cantidadCell = row.getCell(1);
        int cantidadDisponible;
        if (cantidadCell == null) {
            throw new IllegalArgumentException("Cantidad disponible no puede estar vacía");
        }
        
        if (cantidadCell.getCellType() == CellType.NUMERIC) {
            cantidadDisponible = (int) cantidadCell.getNumericCellValue();
        } else if (cantidadCell.getCellType() == CellType.STRING) {
            try {
                cantidadDisponible = Integer.parseInt(cantidadCell.getStringCellValue().trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Cantidad disponible debe ser un número válido");
            }
        } else {
            throw new IllegalArgumentException("Cantidad disponible debe ser un número");
        }

        if (cantidadDisponible < 0) {
            throw new IllegalArgumentException("La cantidad disponible no puede ser negativa");
        }

        // Validar ubicación
        Cell ubicacionCell = row.getCell(2);
        String ubicacion;
        if (ubicacionCell == null) {
            throw new IllegalArgumentException("La ubicación no puede estar vacía");
        }
        ubicacion = ubicacionCell.getStringCellValue().trim();
        if (ubicacion.isEmpty()) {
            throw new IllegalArgumentException("La ubicación no puede estar vacía");
        }

        // Validar lote
        Cell loteCell = row.getCell(3);
        String lote;
        if (loteCell == null) {
            throw new IllegalArgumentException("El lote no puede estar vacío");
        }
        lote = loteCell.getStringCellValue().trim();
        if (lote.isEmpty()) {
            throw new IllegalArgumentException("El lote no puede estar vacío");
        }

        // Validar fecha de vencimiento
        Cell fechaCell = row.getCell(4);
        String fechaVencimiento;
        if (fechaCell == null) {
            throw new IllegalArgumentException("La fecha de vencimiento no puede estar vacía");
        }
        
        if (fechaCell.getCellType() == CellType.STRING) {
            fechaVencimiento = fechaCell.getStringCellValue().trim();
        } else if (fechaCell.getCellType() == CellType.NUMERIC) {
            fechaVencimiento = fechaCell.getDateCellValue().toString();
        } else {
            throw new IllegalArgumentException("Fecha de vencimiento debe ser texto o fecha");
        }
        
        if (fechaVencimiento.isEmpty()) {
            throw new IllegalArgumentException("La fecha de vencimiento no puede estar vacía");
        }

        // Crear y guardar el inventario
        Inventario inventario = new Inventario();
        inventario.setId_producto(idProducto);
        inventario.setCantidad_disponible(cantidadDisponible);
        inventario.setUbicacion(ubicacion);
        inventario.setLote(lote);
        inventario.setFecha_vencimiento(fechaVencimiento);

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

    public void deleteInventario(int id) {
        inventarioRepository.deleteById(id);
    }
}
