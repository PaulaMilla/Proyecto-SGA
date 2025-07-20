package com.mednova.caja_service.service;

import com.mednova.caja_service.dto.*;
import com.mednova.caja_service.model.*;
import com.mednova.caja_service.repository.CajaRepository;
import com.mednova.caja_service.repository.MovimientoCajaRepository;
import com.mednova.caja_service.repository.PagoRepository;
import com.mednova.caja_service.repository.TurnoCajaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CajaService {

    private final TurnoCajaRepository turnoCajaRepo;
    private final CajaRepository cajaRepo;
    private final MovimientoCajaRepository movimientoCajaRepo;
    private final PagoRepository pagoRepo;
    private final WebClient webClient;

    public CajaService(WebClient.Builder builder, TurnoCajaRepository turnoCajaRepo, CajaRepository cajaRepo, MovimientoCajaRepository movimientoCajaRepo, PagoRepository pagoRepo) {
        this.webClient = builder.baseUrl("http://inventarios-service.inventarios.svc.cluster.local").build();
        this.turnoCajaRepo = turnoCajaRepo;
        this.cajaRepo = cajaRepo;
        this.movimientoCajaRepo = movimientoCajaRepo;
        this.pagoRepo = pagoRepo;
    }

    public List<Caja> obtenerTodasLasCajas() {
        return cajaRepo.findAll();
    }

    public Caja crearCaja(Caja caja) {
        return cajaRepo.save(caja);
    }

    public Caja obtenerCajaPorId(int id) {
        return cajaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Caja no encontrada con id: " + id));
    }

    public Caja actualizarEstadoCaja(int id, boolean activa) {
        Caja caja = obtenerCajaPorId(id);
        caja.setActiva(activa);
        return cajaRepo.save(caja);
    }

    public void eliminarCaja(int id) {
        cajaRepo.deleteById(id);
    }

    public TurnoCajaDTO abrirTurno(int cajaId, int idUsuario, BigDecimal montoApertura){
        turnoCajaRepo.findTurnoAbiertoPorCaja(cajaId).ifPresent(
                t -> {throw new RuntimeException("Ya hay un turno abierto para esta caja");}
        );

        Caja caja = cajaRepo.findById(cajaId)
                .orElseThrow(() -> new RuntimeException("Caja no encontrada"));

        TurnoCaja turno = new TurnoCaja();
        turno.setCaja(caja);
        turno.setIdUsuario(idUsuario);
        turno.setMontoApertura(montoApertura);
        turno.setHoraApertura(LocalDateTime.now());
        turno.setCerrado(false);

        TurnoCaja saved = turnoCajaRepo.save(turno);

        TurnoCajaDTO dto = new TurnoCajaDTO();
        dto.setId(saved.getId());
        dto.setCajaId(cajaId);
        dto.setIdUsuario(idUsuario);
        dto.setHoraApertura(saved.getHoraApertura());
        dto.setCerrado(false);
        dto.setMontoApertura(montoApertura);

        return dto;
    }

    public TurnoCajaDTO cerrarTurno(int turnoId, BigDecimal montoCierre){
        TurnoCaja turno = turnoCajaRepo.findById(turnoId)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));

        if (Boolean.TRUE.equals(turno.getCerrado()))
            throw new RuntimeException("El turno ya está cerrado");

        turno.setHoraCierre(LocalDateTime.now());
        turno.setMontoCierre(montoCierre);
        turno.setCerrado(true);

        TurnoCaja saved = turnoCajaRepo.save(turno);

        TurnoCajaDTO dto = new TurnoCajaDTO();
        dto.setId(saved.getId());
        dto.setCajaId(saved.getCaja().getId_caja());
        dto.setIdUsuario(saved.getIdUsuario());
        dto.setHoraApertura(saved.getHoraApertura());
        dto.setHoraCierre(saved.getHoraCierre());
        dto.setMontoApertura(saved.getMontoApertura());
        dto.setMontoCierre(saved.getMontoCierre());
        dto.setCerrado(saved.getCerrado());

        return dto;
    }

    public MovimientoCajaDTO registrarMovimiento(int turnoId, BigDecimal monto, TipoMovimiento tipo, String descripcion, int idVenta){
        TurnoCaja turno = turnoCajaRepo.findById(turnoId)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));
        MovimientoCaja mov = new MovimientoCaja();
        mov.setTurnoCaja(turno);
        mov.setFechaHora(LocalDateTime.now());
        mov.setMonto(monto);
        mov.setTipoMovimiento(tipo);
        mov.setDescripcion(descripcion);
        mov.setId_venta(idVenta);

        MovimientoCaja saved = movimientoCajaRepo.save(mov);

        MovimientoCajaDTO dto = new MovimientoCajaDTO();
        dto.setId(saved.getId_movimientocaja());
        dto.setTurnoCajaId(turnoId);
        dto.setFechaHora(saved.getFechaHora());
        dto.setMonto(saved.getMonto());
        dto.setTipoMovimiento(saved.getTipoMovimiento());
        dto.setDescripcion(saved.getDescripcion());
        dto.setIdVenta(idVenta);

        return dto;

    }

    public PagoDTO registrarPago(int turnoId, int idVenta, BigDecimal monto,MetodoPago metodo, String observacion){
        TurnoCaja turno = turnoCajaRepo.findById(turnoId).orElseThrow(() -> new RuntimeException("Turno no encontrado"));

        Pago pago = new Pago();
        pago.setTurnoCaja(turno);
        pago.setFechaHora(LocalDateTime.now());
        pago.setMonto(monto);
        pago.setObservacion(observacion);
        pago.setIdVenta(idVenta);
        pago.setMetodoPago(metodo);

        Pago saved = pagoRepo.save(pago);

        PagoDTO dto = new PagoDTO();
        dto.setId(saved.getId_pago());
        dto.setTurnoCajaId(turnoId);
        dto.setIdVenta(idVenta);
        dto.setFechaHora(saved.getFechaHora());
        dto.setMonto(saved.getMonto());
        dto.setMetodoPago(saved.getMetodoPago());
        dto.setObservacion(saved.getObservacion());

        return dto;

    }

    public List<MovimientoCajaDTO> obtenerMovimientosPorTurno(int turnoId) {
        List<MovimientoCaja> movimientos = movimientoCajaRepo.findByTurnoCajaId(turnoId);
        List<MovimientoCajaDTO> resultado = new ArrayList<>();

        for (MovimientoCaja mov : movimientos) {
            MovimientoCajaDTO dto = new MovimientoCajaDTO();
            dto.setId(mov.getId_movimientocaja());
            dto.setTurnoCajaId(mov.getTurnoCaja().getId());
            dto.setFechaHora(mov.getFechaHora());
            dto.setMonto(mov.getMonto());
            dto.setTipoMovimiento(mov.getTipoMovimiento());
            dto.setDescripcion(mov.getDescripcion());
            dto.setIdVenta(mov.getId_venta());

            resultado.add(dto);
        }

        return resultado;
    }

    public List<PagoDTO> obtenerPagosPorTurno(int turnoId) {
        List<Pago> pagos = pagoRepo.findByTurnoCajaId(turnoId);
        List<PagoDTO> resultado = new ArrayList<>();

        for (Pago pago : pagos) {
            PagoDTO dto = new PagoDTO();
            dto.setId(pago.getId_pago());
            dto.setTurnoCajaId(pago.getTurnoCaja().getId());
            dto.setIdVenta(pago.getIdVenta());
            dto.setFechaHora(pago.getFechaHora());
            dto.setMonto(pago.getMonto());
            dto.setMetodoPago(pago.getMetodoPago());
            dto.setObservacion(pago.getObservacion());

            resultado.add(dto);
        }

        return resultado;
    }

    public int comprobarFarmacia(int id){
        FarmaciaRequestDTO farmacia = webClient.get()
                .uri("/api/inventarios/farmacia/"+ id)
                .retrieve()
                .bodyToMono(FarmaciaRequestDTO.class)
                .block();

        if(farmacia == null){
            throw new RuntimeException("Farmacia no encontrada, ID: "+ id);
        }
        return farmacia.getId();
    }

    public int obtenerIdUsuario(String emailUsuario) {
        try{
            UsuarioRequestDTO usuario = webClient.get()
                    .uri("/api/usuarios/usuario-email/" + emailUsuario)
                    .retrieve()
                    .bodyToMono(UsuarioRequestDTO.class)
                    .block();
            if(usuario == null) {
                throw new RuntimeException("Usuario no encontrado");
            }
            return usuario.getId();
        }catch (Exception e) {
            throw new RuntimeException("Error al consultar id: " + e.getMessage());
        }
    }
}
