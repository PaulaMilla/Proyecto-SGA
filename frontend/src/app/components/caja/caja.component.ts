import { Component, ViewChild } from '@angular/core';
import { CajaService } from './caja.service';
import { Caja } from './model/caja.model';
import { ListadoMovimientoComponent } from './listado-movimiento/listado-movimiento.component';

@Component({
  selector: 'app-caja',
  templateUrl: './caja.component.html',
  styleUrl: './caja.component.scss'
})
export class CajaComponent {
  cajaSeleccionada: Caja | null = null;

  @ViewChild(ListadoMovimientoComponent) listadoMovimientos?: ListadoMovimientoComponent;

  constructor(private cajaService: CajaService) {}

  onCajaSeleccionada(caja: Caja) {
    this.cajaSeleccionada = caja;
  }

  actualizarListadoMovimientos() {
    this.listadoMovimientos?.cargarMovimientos();
  }
}
