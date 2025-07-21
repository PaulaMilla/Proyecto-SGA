import { Component, OnInit } from '@angular/core';
import { InventarioService } from '../../inventario/services/inventario.service';
import { DispersarService } from '../../inventario/services/dispersar.service';

@Component({
  selector: 'app-dispersar-medicamento',
  templateUrl: './dispersar-medicamento.component.html',
  styleUrls: ['./dispersar-medicamento.component.scss']
})
export class DispersarMedicamentoComponent implements OnInit {
  inventario: any[] = [];
  idSeleccionado: number | null = null;
  cantidad: number = 0;

  constructor(
    private inventarioService: InventarioService,
    private dispersarService: DispersarService
  ) {}

  ngOnInit(): void {
    this.cargarInventarios();
  }

  cargarInventarios(): void {
    const email = localStorage.getItem('email');
    if (!email) {
      alert('No se encontró email en localStorage');
      return;
    }

    this.inventarioService.obtenerInventarios(email).subscribe({
      next: (data) => {
        this.inventario = data.filter(item => item.cantidad_disponible > 0);
      },
      error: (err) => {
        console.error('Error al cargar inventarios:', err);
        alert('Error al cargar inventarios');
      }
    });
  }


  dispersar(): void {
    if (!this.idSeleccionado || this.cantidad <= 0) {
      alert('Completa todos los campos');
      return;
    }

    const email = localStorage.getItem('email');
    if (!email) {
      alert('No se encontró email en localStorage');
      return;
    }

    const request = {
      idInventario: this.idSeleccionado,
      cantidad: this.cantidad,
      emailUsuario: email
    };

    this.dispersarService.dispersar(request).subscribe({
      next: () => alert('Dispersión exitosa'),
      error: (err) => {
        console.error('Error en dispersión:', err);
        alert('Error en dispersión');
      }
    });
  }
}
