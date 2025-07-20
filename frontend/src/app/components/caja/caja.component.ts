import { Component } from '@angular/core';
import { CajaService } from './caja.service';
import { Caja } from './model/caja.model';

@Component({
  selector: 'app-caja',
  templateUrl: './caja.component.html',
  styleUrl: './caja.component.scss'
})
export class CajaComponent {
  cajaSeleccionada: Caja | null = null;

  constructor(private cajaService: CajaService) {}

  ngOnInit(): void {}
  
  onCajaSeleccionada(caja: Caja) {
    this.cajaSeleccionada = caja;
  }
}
