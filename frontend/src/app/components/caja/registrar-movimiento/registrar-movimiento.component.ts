import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Caja } from '../model/caja.model';
import { CajaService } from '../caja.service';

@Component({
  selector: 'app-registrar-movimiento',
  templateUrl: './registrar-movimiento.component.html',
  styleUrl: './registrar-movimiento.component.scss'
})
export class RegistrarMovimientoComponent {
  @Input() caja!: Caja;
  @Output() movimientoRegistrado = new EventEmitter<void>();
  tipo: 'INGRESO' | 'EGRESO' = 'INGRESO';
  monto: number = 0;
  descripcion: string = '';

  constructor(private cajaService: CajaService) {}

  registrar() {
    this.cajaService.registrarMovimiento(this.caja.id_caja, this.tipo, this.monto, this.descripcion)
      .subscribe(() => {
        this.movimientoRegistrado.emit();
        this.monto = 0;
        this.descripcion = '';
      });
  }
}
