package com.mednova.pacientes_service.service;

import com.mednova.pacientes_service.model.Paciente;
import com.mednova.pacientes_service.repository.PacienteRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
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
            List<Paciente> pacientesRegistrados = pacienteRepository.findAll();
            StringBuilder pacientesInfo = new StringBuilder();
            for (Paciente p : pacientesRegistrados) {
                pacientesInfo.append(p.getId_paciente()).append(" (").append(p.getNombre()).append("), ");
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
                                ". Pacientes registrados: " + pacientesInfo.toString());
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

        String[] expectedColumns = {"nombre", "edad", "direccion", "beneficiario","nombre_farmacia"};
        for (int i = 0; i < expectedColumns.length; i++) {
            if (i >= header.length || !header[i].trim().equalsIgnoreCase(expectedColumns[i])) {
                throw new IllegalArgumentException("Columna " + (i + 1) + " debe ser '" + expectedColumns[i] + "'. Encontrada: " +
                        (i < header.length ? header[i] : "faltante"));
            }
        }
    }

    private void processCSVRow(String[] row, int rowNumber) throws Exception {
        if (row.length < 5) {
            throw new IllegalArgumentException("La fila debe tener al menos 5 columnas incluyendo nombre_farmacia");
        }

        String nombre = row[0].trim();

        int edad;
        try {
            edad = Integer.parseInt(row[1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La edad debe ser un número válido");
        }

        String direccion = row[2].trim();
        Boolean beneficiario = row[3].trim().equalsIgnoreCase("true");
        String nombreFarmacia = row[4].trim();

        // Crear nuevo inventario
        Paciente nuevoPaciente = new Paciente();
        nuevoPaciente.setNombre(nombre);
        nuevoPaciente.setEdad(edad);
        nuevoPaciente.setDireccion(direccion);
        nuevoPaciente.setBeneficiario(beneficiario);
        nuevoPaciente.setNombre_farmacia(nombreFarmacia);
        pacienteRepository.save(nuevoPaciente);

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

        String[] expectedColumns = {"nombre", "edad", "direccion", "beneficiario","nombre_farmacia"};
        for (int i = 0; i < expectedColumns.length; i++) {
            Cell cell = headerRow.getCell(i);
            if (cell == null || !cell.getStringCellValue().trim().equalsIgnoreCase(expectedColumns[i])) {
                throw new IllegalArgumentException("Columna " + (i + 1) + " debe ser '" + expectedColumns[i] + "'");
            }
        }
    }

    private void processExcelRow(Row row, int rowNumber) throws Exception {
        if (row.getLastCellNum() < 5) {
            throw new IllegalArgumentException("La fila debe tener al menos 5 columnas incluyendo nombre_farmacia");
        }

        Cell nombreCell = row.getCell(0);
        String nombre = (nombreCell != null) ? nombreCell.getStringCellValue().trim() : "";
        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        Cell edadCell = row.getCell(1);
        int edad;
        if (edadCell.getCellType() == CellType.NUMERIC) {
            edad = (int) edadCell.getNumericCellValue();
        } else {
            edad = Integer.parseInt(edadCell.getStringCellValue().trim());
        }

        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }

        Cell direccionCell = row.getCell(2);
        String direccion = (direccionCell != null) ? direccionCell.getStringCellValue().trim() : "";

        Cell beneficiarioCell = row.getCell(3);
        Boolean beneficiario = beneficiarioCell.getBooleanCellValue();

        // Nombre farmacia
        Cell farmaciaCell = row.getCell(4);
        String nombreFarmacia = (farmaciaCell != null) ? farmaciaCell.getStringCellValue().trim() : "";
        if (nombreFarmacia.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la farmacia no puede estar vacío");
        }

        // Crear nuevo inventario
        Paciente nuevoPaciente = new Paciente();
        nuevoPaciente.setNombre(nombre);
        nuevoPaciente.setEdad(edad);
        nuevoPaciente.setDireccion(direccion);
        nuevoPaciente.setBeneficiario(beneficiario);
        nuevoPaciente.setNombre_farmacia(nombreFarmacia);
        pacienteRepository.save(nuevoPaciente);
    }

    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> obtenerPorId(Integer id) {
        return pacienteRepository.findById(id);
    }

    public Paciente guardar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public void eliminar(Integer id) {
        pacienteRepository.deleteById(id);
    }

    public List<Paciente> obtenerPorBeneficiario(boolean esBeneficiario) {
        return pacienteRepository.findByBeneficiario(esBeneficiario);
    }
    
}
