package com.mednova.alertas_service.service;

import com.mednova.alertas_service.client.InventarioClient;
import com.mednova.alertas_service.client.dto.ProductoDTO;
import com.mednova.alertas_service.model.AlertaRetiro;
import com.mednova.alertas_service.model.Retiro;
import com.mednova.alertas_service.model.enums.TipoProducto;
import com.mednova.alertas_service.repository.AlertaRepository;
import com.mednova.alertas_service.repository.RetiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private RetiroRepository retiroRepository;

    @Autowired
    private InventarioClient inventarioClient;

    public boolean verificarYGenerarAlerta(Retiro nuevoRetiro) {
        ProductoDTO producto = inventarioClient.obtenerProductoPorId(nuevoRetiro.getId_producto());

        if (producto.getTipo() != TipoProducto.RESTRINGIDO) {
            retiroRepository.save(nuevoRetiro);
            return false;
        }

        Optional<Retiro> retiros = retiroRepository.findUltimoRetiro(nuevoRetiro.getId_paciente(), nuevoRetiro.getId_producto());

        if (!retiros.isEmpty()) {
            Retiro ultimo = retiros.get();
            long dias = ChronoUnit.DAYS.between(ultimo.getFecha_retiro(), nuevoRetiro.getFecha_retiro());

            if (dias < 30) {
                AlertaRetiro alerta = new AlertaRetiro();
                alerta.setId_paciente(nuevoRetiro.getId_paciente());
                alerta.setId_producto(nuevoRetiro.getId_producto());
                alerta.setMensaje("Retiro anticipado de medicamento restringido.");
                alerta.setFecha_programada(LocalDate.now());

                alertaRepository.save(alerta);
                return true;
            }
        }

        retiroRepository.save(nuevoRetiro);
        return false;
    }
}
