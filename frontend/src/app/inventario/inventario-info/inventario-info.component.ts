import {Component, OnInit} from '@angular/core';
import {InventarioProducto} from "../model/inventarioproducto.model";
import {InventarioService} from "../services/inventario.service";

@Component({
  selector: 'app-inventario-info',
  templateUrl: './inventario-info.component.html',
  styleUrl: './inventario-info.component.scss'
})
export class InventarioInfoComponent implements OnInit {
  inventarioList: InventarioProducto[] = [];
  loading = true;

  constructor(private inventarioService: InventarioService) {}

  ngOnInit(): void {
    this.inventarioService.getInventarioConProducto().subscribe({
      next: (data) => {
        this.inventarioList = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al obtener inventario', err);
        this.loading = false;
      }
    });
  }
}
