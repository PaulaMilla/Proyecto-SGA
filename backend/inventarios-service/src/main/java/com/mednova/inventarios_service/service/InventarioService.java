package com.mednova.inventarios_service.service;

import com.mednova.inventarios_service.model.Inventario;
import com.mednova.inventarios_service.repository.InventarioRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
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
import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {
    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public void processFile(MultipartFile file) throws Exception {
        if (file.getOriginalFilename().endsWith(".csv")) {
            processCSV(file);
        } else if (file.getOriginalFilename().endsWith(".xlsx")) {
            processExcel(file);
        } else {
            throw new IllegalArgumentException("Formato no soportado.");
        }
    }

    private void processCSV(MultipartFile file) throws IOException {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            CSVReader csvReader = new CSVReader(reader);
            List<String[]> rows = csvReader.readAll();
            for (String[] row : rows.subList(1, rows.size())) { // skip header
                // Validación y mapeo
                Inventario inv = new Inventario();
                inv.setId_inventario(Integer.parseInt(row[0]));
                inv.setId_producto(Integer.parseInt(row[1]));
                inv.setCantidad_disponible(Integer.parseInt(row[2]));
                inv.setUbicacion(String.valueOf(row[3]));
                inv.setLote(String.valueOf(row[4]));
                inv.setFecha_vencimiento(String.valueOf(row[5]));

                // Validaciones aquí...
                inventarioRepository.save(inv);
            }
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    private void processExcel(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // skip header
            Inventario inv = new Inventario();
            inv.setId_inventario((int) row.getCell(0).getNumericCellValue());
            inv.setId_producto((int) row.getCell(1).getNumericCellValue());
            inv.setCantidad_disponible((int) row.getCell(2).getNumericCellValue());
            inv.setUbicacion(row.getCell(3).getStringCellValue());
            inv.setLote(row.getCell(4).getStringCellValue());
            inv.setFecha_vencimiento(row.getCell(5).getStringCellValue());
            // Validaciones aquí...
            inventarioRepository.save(inv);
        }
        workbook.close();
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
