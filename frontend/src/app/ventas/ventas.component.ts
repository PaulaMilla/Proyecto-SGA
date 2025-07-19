import { Component, HostListener, OnInit } from '@angular/core';
import { VentasService } from './services/ventas.service';
import { DetalleVentaDTO, Paciente, Producto, Usuario,
  Venta, VentaConDetallesDTO, VentaForm,
  VentaRegistrada
} from "./model/ventas.model";
import {DetalleVentaConNombre, VentaConNombresYDetalles} from "./model/venta-con-detalles-view.dto";

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
  };

  ventasRegistradas: any[] = [];
  mostrarFormulario = false;

  venta: VentaConDetalles = {
    detalles: []
  };*/

  venta: VentaConDetallesDTO = {
    pacienteId: 0,
    usuarioId: 0,
    detalles: []
  };

  mostrarFormulario: boolean = false;

  nuevoDetalle: DetalleVentaDTO = {
    productoId: 0,
    cantidad: 1
  };

  detallesVenta: DetalleVentaDTO[] = [];
  productosInfo: { [id: number]: Producto } = {};
  total: number = 0;
  nombrePaciente: string = '';
  nombreUsuario: string = '';

  mostrarBotonArriba = false;


  constructor(private ventasService: VentasService) {}

  ngOnInit(): void {
  //  this.cargarVentas();
  }

/*  actualizarPrecioYTotal() {
    if (this.venta.productoId) {
      this.ventasService.getProductoPorId(this.venta.productoId).subscribe(prod => {
        this.venta.precioUnitario = prod.precio_unitario;
        this.venta.total = this.venta.cantidad * prod.precio_unitario;
      });
    }
  }*/

  ventasRegistradas: VentaConNombresYDetalles[] = [];

  cargarVentas(): void {
    this.ventasService.obtenerTodas().subscribe((ventas: any[]) => {
      const ventasConTodo: VentaConNombresYDetalles[] = [];

      ventas.forEach((v) => {
        const ventaExtendida: VentaConNombresYDetalles = {
          id: v.id,
          pacienteId: v.pacienteId,
          usuarioId: v.usuarioId,
          fechaVenta: v.fechaVenta,
          total: v.total,
          nombrePaciente: '',
          nombreUsuario: '',
          detalles: []
        };

        // Obtener nombres por ID
        this.ventasService.getPacientePorId(v.pacienteId).subscribe((p) => {
          ventaExtendida.nombrePaciente = p.nombre;
        });

        this.ventasService.getUsuarioPorId(v.usuarioId).subscribe((u) => {
          ventaExtendida.nombreUsuario = u.nombre;
        });

        // Obtener detalles de la venta
        this.ventasService.getDetallesPorVentaId(v.id).subscribe((detalles: any[]) => {
          detalles.forEach((d) => {
            const detalleExtendido: DetalleVentaConNombre = {
              productoId: d.productoId,
              cantidad: d.cantidad,
              precioUnitario: d.precioUnitario,
              subtotal: d.subtotal,
              nombreProducto: ''
            };

            this.ventasService.getProductoPorId(d.productoId).subscribe((prod) => {
              detalleExtendido.nombreProducto = prod.nombre;
            });

            ventaExtendida.detalles.push(detalleExtendido);
          });
        });

        ventasConTodo.push(ventaExtendida);
      });

      this.ventasRegistradas = ventasConTodo;
    });
  }



  agregarProducto(): void {
    this.detallesVenta.push({ productoId: 0, cantidad: 1 });
  }

  eliminarProducto(i: number): void {
    this.detallesVenta.splice(i, 1);
    this.actualizarTotal();
  }

  actualizarProductoInfo(index: number): void {
    const id = this.detallesVenta[index].productoId;
    if (id && !this.productosInfo[id]) {
      this.ventasService.getProductoPorId(id).subscribe(p => {
        this.productosInfo[id] = p;
        this.actualizarTotal();
      });
    } else {
      this.actualizarTotal();
    }
  }

  eliminarVenta(id: number) {
    if (confirm('¿Estás seguro de que quieres eliminar esta venta?')) {
      this.ventasService.eliminarVenta(id).subscribe(() => {
        alert('Venta eliminada correctamente');
        this.cargarVentas();
      });
    }
  }

  actualizarTotal(): void {
    this.total = this.detallesVenta.reduce((sum, d) => {
      const prod = this.productosInfo[d.productoId];
      return sum + (prod ? prod.precio_unitario * d.cantidad : 0);
    }, 0);
  }

  cargarPaciente(): void {
    if (this.venta.pacienteId) {
      this.ventasService.getPacientePorId(this.venta.pacienteId).subscribe(p => {
        this.nombrePaciente = p.nombre;
      });
    }
  }

  cargarUsuario(): void {
    if (this.venta.usuarioId) {
      this.ventasService.getUsuarioPorId(this.venta.usuarioId).subscribe(u => {
        this.nombreUsuario = u.nombre;
      });
    }
  }

  guardarVenta(): void {
    this.venta.detalles = this.detallesVenta;
    this.ventasService.registrarVenta(this.venta).subscribe(() => {
      alert('Venta registrada correctamente');
      this.venta = { pacienteId: 0, usuarioId: 0, detalles: [] };
      this.detallesVenta = [];
      this.productosInfo = {};
      this.total = 0;
      this.nombrePaciente = '';
      this.nombreUsuario = '';
    });
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
