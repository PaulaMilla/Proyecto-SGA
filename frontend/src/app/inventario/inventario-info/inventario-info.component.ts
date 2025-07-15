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
    const emailUsuario = localStorage.getItem('email');
    if (!emailUsuario) {
      console.warn('No se encontró email en localStorage');
      this.loading = false;
      return;
    }
  
    this.inventarioService.getInventarioConProducto(emailUsuario).subscribe({
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
