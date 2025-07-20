package com.mednova.caja_service.controller;

import com.mednova.caja_service.dto.MovimientoCajaDTO;
import com.mednova.caja_service.dto.PagoDTO;
import com.mednova.caja_service.dto.TurnoCajaDTO;
import com.mednova.caja_service.model.Caja;
import com.mednova.caja_service.model.MetodoPago;
import com.mednova.caja_service.model.TipoMovimiento;
import com.mednova.caja_service.service.CajaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/caja")
public class CajaController {

    private final CajaService cajaService;

    public CajaController(CajaService cajaService) {
        this.cajaService = cajaService;
    }

    @GetMapping
    public ResponseEntity<List<Caja>> obtenerTodasLasCajas() {
        return ResponseEntity.ok(cajaService.obtenerTodasLasCajas());
    }

    @PostMapping
    public ResponseEntity<Caja> crearCaja(@RequestBody Caja newCaja) {
        if(cajaService.comprobarFarmacia(newCaja.getIdFarmacia()) > 0){
            return ResponseEntity.ok(cajaService.crearCaja(newCaja));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/abrir-turno")
    public ResponseEntity<TurnoCajaDTO> abrirTurno(@RequestParam int cajaId, @RequestParam String emailUsuario, @RequestParam BigDecimal montoApertura) {
        int idUsuario = cajaService.obtenerIdUsuario(emailUsuario);
        if(idUsuario>0){
            TurnoCajaDTO dto = cajaService.abrirTurno(cajaId, idUsuario, montoApertura);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/cerrar-turno")
    public ResponseEntity<TurnoCajaDTO> cerrarTurno(@RequestParam int turnoId,
                                                    @RequestParam BigDecimal montoCierre) {
        TurnoCajaDTO dto = cajaService.cerrarTurno(turnoId, montoCierre);
        return ResponseEntity.ok(dto);
    }

    // Registrar un movimiento (ingreso/egreso)
    @PostMapping("/movimiento")
    public ResponseEntity<MovimientoCajaDTO> registrarMovimiento(@RequestParam int turnoId,
                                                                 @RequestParam BigDecimal monto,
                                                                 @RequestParam TipoMovimiento tipo,
                                                                 @RequestParam String descripcion,
                                                                 @RequestParam(required = false) int idVenta) {
        MovimientoCajaDTO dto = cajaService.registrarMovimiento(turnoId, monto, tipo, descripcion, idVenta);
        return ResponseEntity.ok(dto);
    }

    // Registrar un pago
    @PostMapping("/pago")
    public ResponseEntity<PagoDTO> registrarPago(@RequestParam int turnoId,
                                                 @RequestParam int idVenta,
                                                 @RequestParam BigDecimal monto,
                                                 @RequestParam MetodoPago metodo,
                                                 @RequestParam(required = false) String observacion) {
        PagoDTO dto = cajaService.registrarPago(turnoId, idVenta, monto, metodo, observacion);
        return ResponseEntity.ok(dto);
    }

    // Obtener todos los movimientos de un turno
    @GetMapping("/movimientos/{turnoId}")
    public ResponseEntity<List<MovimientoCajaDTO>> getMovimientos(@PathVariable int turnoId) {
        List<MovimientoCajaDTO> movimientos = cajaService.obtenerMovimientosPorTurno(turnoId);
        return ResponseEntity.ok(movimientos);
    }

    // Obtener todos los pagos de un turno
    @GetMapping("/pagos/{turnoId}")
    public ResponseEntity<List<PagoDTO>> getPagos(@PathVariable int turnoId) {
        List<PagoDTO> pagos = cajaService.obtenerPagosPorTurno(turnoId);
        return ResponseEntity.ok(pagos);
    }

    // Obtener una caja por ID
    @GetMapping("/{id}")
    public ResponseEntity<Caja> obtenerCajaPorId(@PathVariable int id) {
        Caja caja = cajaService.obtenerCajaPorId(id);
        return ResponseEntity.ok(caja);
    }

    // Activar o desactivar una caja
    @PutMapping("/{id}/estado")
    public ResponseEntity<Caja> actualizarEstadoCaja(@PathVariable int id, @RequestParam boolean activa) {
        Caja actualizada = cajaService.actualizarEstadoCaja(id, activa);
        return ResponseEntity.ok(actualizada);
    }

    // Eliminar una caja
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCaja(@PathVariable int id) {
        cajaService.eliminarCaja(id);
        return ResponseEntity.noContent().build();
    }
}
