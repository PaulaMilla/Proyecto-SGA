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
    const emailUsuario = localStorage.getItem('email');

    if (!emailUsuario) {
      alert('No se encontró el usuario en localStorage');
      return;
    }

    this.inventarioService.obtenerInventarios(emailUsuario).subscribe({
      next: (data: any[]) => {
        this.inventario = data;
      },
      error: (err) => {
        console.error('Error al cargar inventario:', err);
        alert('Error al cargar inventario');
      }
    });
  }




  onInventarioChange(): void {
      const id = Number(this.idSeleccionado); // asegura conversión explícita

      if (isNaN(id)) {
      this.nuevoLote = '';
      return;
    }

    const inventarioSeleccionado = this.inventario.find(i => i.id_inventario === id);

    if (inventarioSeleccionado) {
      const timestamp = new Date().getTime();
      this.nuevoLote = `FRAC-${inventarioSeleccionado.id_inventario}-${timestamp}`;
    } else {
      this.nuevoLote = '';
    }
  }


  fraccionar(): void {
    if (!this.idSeleccionado || this.cantidadFraccionada <= 0 || !this.nuevoLote) {
      alert('Completa todos los campos');
      return;
    }

    const emailUsuario = localStorage.getItem('email');
    if (!emailUsuario) {
      alert('No se encontró el usuario en localStorage');
      return;
    }

    const request: FraccionamientoRequest = {
      idInventario: this.idSeleccionado,
      cantidadFraccionada: this.cantidadFraccionada,
      nuevoLote: this.nuevoLote,
      emailUsuario: emailUsuario // <-- NUEVO
    };

    this.fraccionamientoService.fraccionar(request).subscribe({
      next: () => alert('Fraccionamiento exitoso'),
      error: (err) => alert('Error en fraccionamiento: ' + err.message)
    });
  }

}
