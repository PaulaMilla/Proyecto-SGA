import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-carga-datos',
  templateUrl: './carga-datos.component.html',
  styleUrl: './carga-datos.component.scss'
})
export class CargaDatosComponent {
  constructor(private router: Router) {}

  redirigir(tipo: string): void {
    if (tipo === 'inventario') {
      this.router.navigate(['/inventario/upload']);
    } else if (tipo === 'pacientes') {
      this.router.navigate(['/pacientes/upload']);
    }
    // Puedes agregar más condiciones si tienes más tipos de carga
  }

}
