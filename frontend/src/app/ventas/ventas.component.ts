import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-ventas',
  templateUrl: './ventas.component.html',
  styleUrls: ['./ventas.component.scss']
})
export class VentasComponent implements OnInit {
  venta = {
    cliente: '',
    producto: '',
    cantidad: 1,
    total: 0
  };

  constructor() {}

  ngOnInit(): void {}

  guardarVenta() {
    // Aquí pondrías la llamada a tu servicio para guardar la venta en backend
    console.log('Venta guardada:', this.venta);
    // Luego limpiar formulario
    this.venta = { cliente: '', producto: '', cantidad: 1, total: 0 };
  }
}
