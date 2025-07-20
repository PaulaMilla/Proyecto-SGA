import { Component, Input } from '@angular/core';
import { Caja } from '../model/caja.model';
import { CajaService } from '../caja.service';
import { Movimiento } from '../model/movimiento.model';

@Component({
  selector: 'app-listado-movimiento',
  templateUrl: './listado-movimiento.component.html',
  styleUrl: './listado-movimiento.component.scss'
})
export class ListadoMovimientoComponent {
  @Input() caja!: Caja;
  movimientos: Movimiento[] = [];

  constructor(private cajaService: CajaService) {}

  ngOnInit(): void {
    this.cargarMovimientos();
  }

  cargarMovimientos() {
    this.cajaService.obtenerMovimientosPorCaja(this.caja.id_caja)
      .subscribe(data => this.movimientos = data);
  }
}
