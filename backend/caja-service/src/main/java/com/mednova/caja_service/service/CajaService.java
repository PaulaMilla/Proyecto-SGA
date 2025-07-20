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
import java.util.Optional;

@Service
public class CajaService {

    private final TurnoCajaRepository turnoCajaRepo;
    private final CajaRepository cajaRepo;
    private final MovimientoCajaRepository movimientoCajaRepo;
    private final PagoRepository pagoRepo;
    private final WebClient webClientInventario;
    private final WebClient webClientUsuario;
    private final WebClient webClientVenta;
    private final WebClient webClientCompra;

    public CajaService(WebClient.Builder builder, WebClient.Builder builder2, WebClient.Builder builder3, WebClient.Builder builder4, TurnoCajaRepository turnoCajaRepo, CajaRepository cajaRepo, MovimientoCajaRepository movimientoCajaRepo, PagoRepository pagoRepo) {
        this.webClientInventario = builder.baseUrl("http://inventarios-service.inventarios.svc.cluster.local").build();
        this.webClientUsuario = builder2.baseUrl("http://usuarios-service.usuarios.svc.cluster.local").build();
        this.webClientVenta = builder3.baseUrl("http://ventas-service.ventas.svc.cluster.local").build();
        this.webClientCompra = builder4.baseUrl("http://compras-service.compras.svc.cluster.local").build();
        this.turnoCajaRepo = turnoCajaRepo;
        this.cajaRepo = cajaRepo;
        this.movimientoCajaRepo = movimientoCajaRepo;
        this.pagoRepo = pagoRepo;
    }

    public List<Caja> obtenerTodasLasCajas() {
        return cajaRepo.findAll();
    }

    public List<TurnoCaja> obtenerTodasLosTurnos() { return turnoCajaRepo.findAll(); }

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

    public TurnoCajaDTO cerrarTurno(int turnoId){
        TurnoCaja turno = turnoCajaRepo.findById(turnoId)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));

        if (Boolean.TRUE.equals(turno.getCerrado()))
            throw new RuntimeException("El turno ya está cerrado");

        turno.setHoraCierre(LocalDateTime.now());
        System.out.println(turno.getMontoCierre());
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

    public MovimientoCajaDTO registrarMovimiento(int turnoId, BigDecimal monto, TipoMovimiento tipo, String descripcion){
        TurnoCaja turno = turnoCajaRepo.findById(turnoId)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));
        MovimientoCaja mov = new MovimientoCaja();
        mov.setTurnoCaja(turno);
        mov.setFechaHora(LocalDateTime.now());
        mov.setMonto(monto);
        mov.setTipoMovimiento(tipo);
        mov.setDescripcion(descripcion);

        MovimientoCaja saved = movimientoCajaRepo.save(mov);

        // Actualizar montoCierre según el tipo de movimiento
        BigDecimal montoCierreActual = (turno.getMontoCierre() != null ? turno.getMontoCierre() : turno.getMontoApertura());
        BigDecimal nuevoMontoCierre;

        if (tipo == TipoMovimiento.INGRESO) {
            nuevoMontoCierre = montoCierreActual.add(monto);
        } else if (tipo == TipoMovimiento.EGRESO) {
            nuevoMontoCierre = montoCierreActual.subtract(monto);
        } else {
            // Para otros tipos, por si acaso
            nuevoMontoCierre = montoCierreActual;
        }

        turno.setMontoCierre(nuevoMontoCierre);
        turnoCajaRepo.save(turno);

        MovimientoCajaDTO dto = new MovimientoCajaDTO();
        dto.setId(saved.getId_movimientocaja());
        dto.setTurnoCajaId(turnoId);
        dto.setFechaHora(saved.getFechaHora());
        dto.setMonto(saved.getMonto());
        dto.setTipoMovimiento(saved.getTipoMovimiento());
        dto.setDescripcion(saved.getDescripcion());

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

        // Actualizar montoCierre sumando el pago
        BigDecimal nuevoMontoCierre = (turno.getMontoCierre() != null ? turno.getMontoCierre() : turno.getMontoApertura()).add(monto);
        turno.setMontoCierre(nuevoMontoCierre);
        turnoCajaRepo.save(turno);


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

    public void notificarVentaPagada(int idVenta, BigDecimal monto){
        NotificarVentaDTO dto = new NotificarVentaDTO();
        dto.setIdVenta(idVenta);
        dto.setMonto(monto);

        try{
            webClientVenta.post()
                    .uri("/api/ventas/notificar-pago")
                    .bodyValue(dto)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        }catch (Exception e){
            throw new RuntimeException("Error al notificar pago a ventas-service: "+ e.getMessage());
        }
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
        FarmaciaRequestDTO farmacia = webClientInventario.get()
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
            UsuarioRequestDTO usuario = webClientUsuario.get()
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

    public TurnoCajaDTO obtenerTurnoActual(int cajaId){
        Optional<TurnoCaja> turnoActivo = turnoCajaRepo.findByCajaIdAndActivoTrue(cajaId);

        if (turnoActivo.isPresent()) {
            TurnoCaja turno = turnoActivo.get();
            TurnoCajaDTO dto = new TurnoCajaDTO();
            dto.setId(turno.getId());
            dto.setCajaId(turno.getCaja().getId_caja());
            dto.setIdUsuario(turno.getIdUsuario());
            dto.setHoraApertura(turno.getHoraApertura());
            dto.setMontoApertura(turno.getMontoApertura());
            dto.setHoraCierre(turno.getHoraCierre());
            dto.setMontoCierre(turno.getMontoCierre());
            dto.setCerrado(turno.getCerrado());

            return dto;
        }

        return null;
    }

    public List<Caja> obtenerCajasPorEmailUsuario(String emailUsuario) {
        // Paso 1: Obtener el nombre de la farmacia desde usuarios-service
        String nombreFarmacia = webClientUsuario
                .get()
                .uri("/api/usuarios/nombre-farmacia/{email}", emailUsuario)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        if (nombreFarmacia == null || nombreFarmacia.isEmpty()) {
            throw new RuntimeException("No se pudo obtener el nombre de la farmacia para el usuario: " + emailUsuario);
        }

        // Paso 2: Obtener el ID de la farmacia desde inventarios-service
        Integer idFarmacia = webClientInventario
                .get()
                .uri("/api/inventarios/farmacia/nombre/{nombre}", nombreFarmacia)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();

        if (idFarmacia == null) {
            throw new RuntimeException("No se encontró una farmacia con nombre: " + nombreFarmacia);
        }

        // Paso 3: Buscar las cajas asociadas a esa farmacia en la base local
        return cajaRepo.findByIdFarmacia(idFarmacia);
    }
}
