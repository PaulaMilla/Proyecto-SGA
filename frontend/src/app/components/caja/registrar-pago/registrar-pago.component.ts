import { Component, Input } from '@angular/core';
import { Caja } from '../model/caja.model';
import { CajaService } from '../caja.service';
import { Venta } from '../model/venta.model';

@Component({
  selector: 'app-registrar-pago',
  templateUrl: './registrar-pago.component.html',
  styleUrl: './registrar-pago.component.scss'
})
export class RegistrarPagoComponent {
  @Input() caja!: Caja;
  ventas: Venta[] = [];
  ventaSeleccionada?: Venta;
  metodoPago: string = 'EFECTIVO';
  turnoId!: number; 

  constructor(private cajaService: CajaService) {}

  ngOnInit(): void {
    this.obtenerVentasNoPagadas();
    this.obtenerTurnoActual(); 
  }

  obtenerVentasNoPagadas() {
    this.cajaService.obtenerVentasNoPagadas().subscribe(data => {
      this.ventas = data;
    });
  }

  obtenerTurnoActual() {
    const idCaja = this.caja.id_caja;
    this.cajaService.obtenerTurnoActual(idCaja).subscribe(turno => {
      this.turnoId = turno.id;
    });
  }

  pagar() {
  if (!this.ventaSeleccionada || !this.metodoPago || !this.turnoId) {
    console.warn('Faltan datos para registrar el pago');
    return;
  }

  this.cajaService.registrarPago(
    this.turnoId,
    this.ventaSeleccionada,
    this.metodoPago
  ).subscribe(() => {
    alert('Pago registrado exitosamente');
    this.ventaSeleccionada = undefined;
    this.obtenerVentasNoPagadas(); 
  }, error => {
    console.error('Error al registrar el pago', error);
    alert('Ocurrió un error al registrar el pago');
  });
}
}  
