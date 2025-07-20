import { Component, Input } from '@angular/core';
import { Caja } from '../model/caja.model';
import { CajaService } from '../caja.service';
import { TurnoCaja } from '../model/turno-caja.model';

@Component({
  selector: 'app-estado-caja',
  templateUrl: './estado-caja.component.html',
  styleUrl: './estado-caja.component.scss'
})
export class EstadoCajaComponent {
  @Input() caja!: Caja;
  turnoActual?: TurnoCaja;

  constructor(private cajaService: CajaService) {}

  ngOnInit(): void {
    this.cajaService.obtenerTurnoActual(this.caja.id_caja).subscribe(turno => {
      this.turnoActual = turno;
    });
  }
}
