import { Component, EventEmitter, Output } from '@angular/core';
import { Caja } from '../model/caja.model';
import { CajaService } from '../caja.service';

@Component({
  selector: 'app-seleccionar-caja',
  templateUrl: './seleccionar-caja.component.html',
  styleUrl: './seleccionar-caja.component.scss'
})
export class SeleccionarCajaComponent {
  cajas: Caja[] = [];
  cajaSeleccionadaTemp?: Caja;

  @Output() cajaSeleccionada = new EventEmitter<Caja>();

  constructor(private cajaService: CajaService) {}

  ngOnInit(): void {
    this.cajaService.obtenerTodas().subscribe(data => this.cajas = data);
  }

  onCajaChange(event: Event) {
    const target = event.target as HTMLSelectElement;
    const idSeleccionado = parseInt(target.value, 10);
    this.cajaSeleccionadaTemp = this.cajas.find(c => c.id_caja === idSeleccionado);
  }

  emitirCajaSeleccionada() {
    if (this.cajaSeleccionadaTemp) {
      this.cajaSeleccionada.emit(this.cajaSeleccionadaTemp);
    }
  }
}
