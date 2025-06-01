package com.mednova.reportes_service.service;

import com.mednova.reportes_service.model.ReporteVenta;
import com.mednova.reportes_service.repository.ReporteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReporteService {

    private final ReporteRepository reporteRepository;

    public ReporteService(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    public List<ReporteVenta> generarReportePorFechas(LocalDate desde, LocalDate hasta) {
        return reporteRepository.obtenerReportePorFechas(desde, hasta);
    }


}
