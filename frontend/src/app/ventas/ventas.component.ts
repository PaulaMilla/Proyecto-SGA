import { Component, HostListener, OnInit } from '@angular/core';
import { VentasService, Venta } from './services/ventas.service';

@Component({
  selector: 'app-ventas',
  templateUrl: './ventas.component.html',
  styleUrls: ['./ventas.component.scss']
})
export class VentasComponent implements OnInit {
  venta = {
    pacienteId: 0,
    productoId: 0,
    cantidad: 1,
    precioUnitario: 0,
    usuarioId: 0
  };

  ventasRegistradas: any[] = [];

  mostrarFormulario: boolean = false;  // controla si se ve el formulario
  mostrarBotonArriba = false;

  constructor(private ventasService: VentasService) {}

  ngOnInit(): void {
    this.cargarVentas();
  }

  guardarVenta() {
    const total = this.venta.cantidad * this.venta.precioUnitario;
    const ventaCompleta: Venta = {
        pacienteId: this.venta.pacienteId,
        fechaVenta: new Date().toISOString().slice(0,10),
        total: total,
        usuarioId: this.venta.usuarioId
     };

    this.ventasService.registrarVenta(ventaCompleta).subscribe(nuevaVenta => {
      this.cargarVentas();
      this.venta = {
        pacienteId: 0,
        productoId: 0,
        cantidad: 1,
        precioUnitario: 0,
        usuarioId: 0
      };
      this.mostrarFormulario = false;
    });
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

  cargarVentas():void{
    this.ventasService.obtenerTodas().subscribe(data => {
      this.ventasRegistradas = data;
    });
  }
  
}