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

  ventasRegistradas: any[] = [];

  mostrarFormulario: boolean = false;  // controla si se ve el formulario
  mostrarBotonArriba = false;

  constructor() {}

  ngOnInit(): void {}

  guardarVenta() {
    const total = this.venta.cantidad * this.venta.precioUnitario;
    const ventaCompleta = { ...this.venta, total };

    this.ventasRegistradas.push(ventaCompleta);

    console.log('Venta guardada:', ventaCompleta);

    this.venta = {
      cliente: '',
      producto: '',
      cantidad: 1,
      precioUnitario: 0
    };

    this.mostrarFormulario = false; // ocultar formulario al guardar
  }

  get total(): number {
    return this.venta.cantidad * this.venta.precioUnitario;
  }

  get totalFormateado(): string {
    return '$' + this.total.toFixed(0);
  }

  mostrarAgregarVenta() {
    this.mostrarFormulario = true;
  }

  cancelarAgregar() {
    this.mostrarFormulario = false;
  }

  volverArriba() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  @HostListener('window:scroll', [])
  onWindowScroll() {
    this.mostrarBotonArriba = window.pageYOffset > 150;
  }
}