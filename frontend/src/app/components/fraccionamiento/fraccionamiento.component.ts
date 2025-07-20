import { Component, OnInit } from '@angular/core';
import { FraccionamientoService, FraccionamientoRequest } from './fraccionamiento.service';
import { InventarioService } from '../../inventario/services/inventario.service';

@Component({
  selector: 'app-fraccionamiento',
  templateUrl: './fraccionamiento.component.html',
  styleUrls: ['./fraccionamiento.component.scss']
})
export class FraccionamientoComponent implements OnInit {

  inventario: any[] = [];
  idSeleccionado: number | null = null;
  cantidadFraccionada: number = 0;
  nuevoLote: string = '';

  constructor(
    private fraccionamientoService: FraccionamientoService,
    private inventarioService: InventarioService
  ) {}

  ngOnInit(): void {
    this.cargarInventarios();
  }

    cargarInventarios(): void {
      this.inventarioService.obtenerInventarios().subscribe((data: any[]) => {
        this.inventario = data;
      });
    }

  fraccionar(): void {
    if (!this.idSeleccionado || this.cantidadFraccionada <= 0 || !this.nuevoLote) {
      alert('Completa todos los campos');
      return;
    }

    const request: FraccionamientoRequest = {
      idInventario: this.idSeleccionado,
      cantidadFraccionada: this.cantidadFraccionada,
      nuevoLote: this.nuevoLote
    };

    this.fraccionamientoService.fraccionar(request).subscribe({
      next: (res) => alert('Fraccionamiento exitoso'),
      error: (err) => alert('Error en fraccionamiento: ' + err.message)
    });
  }
}
