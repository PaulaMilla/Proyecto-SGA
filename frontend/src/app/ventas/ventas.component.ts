import { Component, HostListener, OnInit } from '@angular/core';
import { VentasService } from './services/ventas.service';
import {DetalleVenta, Venta, VentaConDetalles, VentaRegistrada} from "./model/ventas.model";

@Component({
  selector: 'app-ventas',
  templateUrl: './ventas.component.html',
  styleUrls: ['./ventas.component.scss']
})
export class VentasComponent implements OnInit {
  /*venta = {
    pacienteId: 0,
    productoId: 0,
    cantidad: 1,
    precioUnitario: 0,
    usuarioId: 0
  };*/

  ventasRegistradas: any[] = [];
  mostrarFormulario = false;

  venta: VentaConDetalles = {
    detalles: []
  };

  // Campos temporales para agregar un detalle
  nuevoDetalle: DetalleVenta = {
    cantidad: 1
  };
  mostrarBotonArriba = false;

  constructor(private ventasService: VentasService) {}

  ngOnInit(): void {
    this.cargarVentas();
  }

/*  actualizarPrecioYTotal() {
    if (this.venta.productoId) {
      this.ventasService.getProductoPorId(this.venta.productoId).subscribe(prod => {
        this.venta.precioUnitario = prod.precio_unitario;
        this.venta.total = this.venta.cantidad * prod.precio_unitario;
      });
    }
  }*/


  agregarProducto() {
    if (this.nuevoDetalle.productoId && this.nuevoDetalle.cantidad > 0) {
      this.venta.detalles.push({ ...this.nuevoDetalle });
      this.nuevoDetalle = { cantidad: 1 };
    } else {
      alert('Debe ingresar un producto y una cantidad válida.');
    }
  }

  quitarProducto(index: number) {
    this.venta.detalles.splice(index, 1);
  }

  eliminarVenta(id: number) {
    if (confirm('¿Estás seguro de que quieres eliminar esta venta?')) {
      this.ventasService.eliminarVenta(id).subscribe(() => {
        alert('Venta eliminada correctamente');
        this.cargarVentas();
      });
    }
  }


  guardarVenta() {
    if (!this.venta.pacienteId) {
      alert('Debe ingresar un ID de paciente.');
      return;
    }

    if (this.venta.detalles.length === 0) {
      alert('Debe agregar al menos un producto.');
      return;
    }

    this.ventasService.registrarVenta(this.venta).subscribe(() => {
      alert('Venta registrada correctamente');
      this.mostrarFormulario = false;
      this.cargarVentas();
    });
  }

  /*  guardarVenta() {
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
  }*/

 /* get totalFormateado(): string {
    if (this.venta.total != null) {
      return `$${this.venta.total.toFixed(0)}`;
    }
    return '$0';
  }*/


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
