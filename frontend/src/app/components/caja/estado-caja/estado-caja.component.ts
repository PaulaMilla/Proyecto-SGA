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
  montoApertura: number = 0;

  constructor(private cajaService: CajaService) {}

  ngOnInit(): void {
    this.obtenerTurno();
  }

  obtenerTurno() {
    this.cajaService.obtenerTurnoActual(this.caja.id_caja).subscribe(turno => {
      this.turnoActual = turno;
    });
  }

  abrirTurno() {
    const emailUsuario = localStorage.getItem('email') || ''; 

    this.cajaService.abrirTurno(this.caja.id_caja, emailUsuario, this.montoApertura).subscribe(turno => {
      this.turnoActual = turno;
    });
  }

  cerrarTurno() {
    const montoCierre = prompt("Ingrese el monto de cierre:");

    if (montoCierre && this.turnoActual) {
      const monto = parseFloat(montoCierre);

      this.cajaService.cerrarTurno(this.turnoActual.id, monto).subscribe(() => {
        this.turnoActual = undefined;
      });
    }
  }
}
