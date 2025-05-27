import { Component, OnInit } from '@angular/core';
import { VentasService, Venta } from '../services/ventas.service';

@Component({
  selector: 'app-ventas',
  templateUrl: './ventas.component.html'
})
export class VentasComponent implements OnInit {
  ventas: Venta[] = [];

  constructor(private ventasService: VentasService) {}

  ngOnInit() {
    this.ventasService.obtenerTodas().subscribe(data => {
      this.ventas = data;
    });
  }
}
