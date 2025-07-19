import { Component, OnInit } from '@angular/core';
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

  constructor(private inventarioService: InventarioService) {}

  ngOnInit(): void {
    const email = localStorage.getItem('email') || '';
    this.inventarioService.getInventarioConProducto(email).subscribe({
      next: (data) => {
        this.inventario = data;
      },
      error: (error) => {
        console.error('Error al cargar inventario', error);
      }
    });
  }

  fraccionar(): void {
    if (this.idSeleccionado === null || this.cantidadFraccionada <= 0 || this.nuevoLote.trim() === '') {
      alert('Completa todos los campos');
      return;
    }

    const payload = {
      idInventario: this.idSeleccionado,
      cantidadFraccionada: this.cantidadFraccionada,
      nuevoLote: this.nuevoLote
    };

    this.inventarioService.fraccionarInventario(payload).subscribe({
      next: () => {
        alert('Fraccionamiento realizado');
        this.cantidadFraccionada = 0;
        this.nuevoLote = '';
      },
      error: (err) => {
        alert('Error al fraccionar: ' + err.error?.message || 'Error desconocido');
      }
    });
  }
}
