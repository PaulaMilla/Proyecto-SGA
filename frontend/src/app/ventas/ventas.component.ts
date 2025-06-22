import { Component, HostListener, OnInit } from '@angular/core';

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
    precioUnitario: 0
  };

  mostrarBotonArriba = false;

  constructor() {}

  ngOnInit(): void {}

  guardarVenta() {
    const total = this.venta.cantidad * this.venta.precioUnitario;
    console.log('Venta guardada:', {
      ...this.venta,
      total
    });

    this.venta = {
      cliente: '',
      producto: '',
      cantidad: 1,
      precioUnitario: 0
    };
  }

  get total(): number {
    return this.venta.cantidad * this.venta.precioUnitario;
  }

  get totalFormateado(): string {
    return '$' + this.total.toFixed(0);
  }

  volverArriba() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  @HostListener('window:scroll', [])
  onWindowScroll() {
    this.mostrarBotonArriba = window.pageYOffset > 150;
  }
}
