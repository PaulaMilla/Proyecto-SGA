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
  ventaSeleccionada?: number;
  metodoPago: string = 'EFECTIVO';

  constructor(private cajaService: CajaService) {}

  ngOnInit(): void {
    this.cajaService.obtenerVentasNoPagadas().subscribe(v => this.ventas = v);
  }

  pagar() {
    this.cajaService.registrarPago(this.caja.id_caja, this.ventaSeleccionada!, this.metodoPago).subscribe();
  }
}  
