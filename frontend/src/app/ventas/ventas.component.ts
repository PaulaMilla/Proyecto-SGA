import { Component, HostListener, OnInit } from '@angular/core';
import { VentasService } from './services/ventas.service';
import { DetalleVentaDTO, Producto, VentaConDetallesDTO } from "./model/ventas.model";
import {DetalleVentaConNombre, VentaConNombresYDetalles} from "./model/venta-con-detalles-view.dto";
import {UsuariosService} from "../services/usuarios.service";
import {InventarioService} from "../inventario/services/inventario.service";

@Component({
  selector: 'app-ventas',
  templateUrl: './ventas.component.html',
  styleUrls: ['./ventas.component.scss']
})
export class VentasComponent implements OnInit {
  
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

  pacientes: any[] = [];
  usuarios: any[] = [];
  productos: any[] = [];


  constructor(private ventasService: VentasService, private usuariosService: UsuariosService,
              private inventarioService: InventarioService) {}

  ngOnInit(): void {
    this.cargarVentas();
    this.cargarPacientes();
    this.cargarUsuarios();
    this.cargarProductos();
  }

  ventasRegistradas: VentaConNombresYDetalles[] = [];

  cargarPacientes(): void {
    this.ventasService.getTodosPacientes().subscribe(data => {
      this.pacientes = data;
      console.log('Pacientes cargados:', this.pacientes);
      // Debug: Ver la estructura del primer paciente
      if (this.pacientes.length > 0) {
        console.log('Estructura del primer paciente:', this.pacientes[0]);
        console.log('Claves del primer paciente:', Object.keys(this.pacientes[0]));
      }
    });
  }
  
  cargarUsuarios(): void {
    this.usuariosService.obtenerTodas().subscribe(data => {
      this.usuarios = data;
      console.log('Usuarios cargados:', this.usuarios);
      // Debug: Ver la estructura del primer usuario
      if (this.usuarios.length > 0) {
        console.log('Estructura del primer usuario:', this.usuarios[0]);
        console.log('Claves del primer usuario:', Object.keys(this.usuarios[0]));
      }
    });
  }

  cargarProductos(): void {
  this.inventarioService.getTodosProductos().subscribe(data => {
    this.productos = data;
    console.log(' Productos cargados:', this.productos);
  });
}

  getNombreProducto(id: number): string {
    const producto = this.productos.find(p => p.id_producto === id);
    return producto ? producto.nombre : 'Desconocido';
  }

  cargarVentas(): void {
    this.ventasService.obtenerTodas().subscribe((ventas: any[]) => {
      const ventasConTodo: VentaConNombresYDetalles[] = [];

      ventas.forEach((v) => {
        const ventaExtendida: VentaConNombresYDetalles = {
          id: v.id,
          pacienteId: v.pacienteId,
          idUsuarioVendedor: v.idUsuarioVendedor,
          fechaVenta: v.fechaVenta,
          total: v.total,
          nombrePaciente: '',
          nombreUsuario: '',
          apellidoUsuario: '',
          detalles: []
        };
        // Obtener nombres por ID
        this.ventasService.getPacientePorId(v.pacienteId).subscribe((p) => {
          ventaExtendida.nombrePaciente = p.nombre;
        });

        this.ventasService.getUsuarioPorId(v.idUsuarioVendedor).subscribe((u) => {
          ventaExtendida.nombreUsuario = u.nombre;
          ventaExtendida.apellidoUsuario = u.apellido;
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
    const productoId = Number(this.nuevoDetalle.productoId);
    const cantidad = Number(this.nuevoDetalle.cantidad);
  
    console.log('Valor original del select:', this.nuevoDetalle.productoId);
    console.log('Intentando agregar:', { productoId, cantidad });
    console.log('Productos disponibles:', this.productos);
  
    if (productoId > 0 && cantidad > 0) {
      this.venta.detalles.push({ productoId, cantidad });
      this.nuevoDetalle = { productoId: 0, cantidad: 1 };
      console.log('Producto agregado. Detalles actuales:', this.venta.detalles);
    } else {
      alert('Selecciona un producto válido y una cantidad mayor a 0.');
    }
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
  // Debug completo
    console.log('=== DEBUG COMPLETO ===');
    console.log('venta.pacienteId:', this.venta.pacienteId, 'tipo:', typeof this.venta.pacienteId);
    console.log('venta.usuarioId:', this.venta.usuarioId, 'tipo:', typeof this.venta.usuarioId);
    console.log('pacientes disponibles:', this.pacientes);
    console.log('usuarios disponibles:', this.usuarios);
    
    // Verificar si son truthy
    console.log('¿pacienteId es truthy?', !!this.venta.pacienteId);
    console.log('¿usuarioId es truthy?', !!this.venta.usuarioId);
    
    // Verificar si son mayor a 0
    console.log('¿pacienteId > 0?', this.venta.pacienteId > 0);
    console.log('¿usuarioId > 0?', this.venta.usuarioId > 0);
    
    // Convertir a número explícitamente
    const pacienteIdNum = Number(this.venta.pacienteId);
    const usuarioIdNum = Number(this.venta.usuarioId);
    
    console.log('pacienteId como número:', pacienteIdNum);
    console.log('usuarioId como número:', usuarioIdNum);
  
    // Cambiar la validación
    if (!pacienteIdNum || !usuarioIdNum || pacienteIdNum <= 0 || usuarioIdNum <= 0) {
      console.log('Error: Faltan paciente o usuario');
      alert('Selecciona paciente y usuario');
      return;
    }
  
    if (this.venta.detalles.length === 0) {
      alert('Agrega al menos un producto.');
      return;
    }
  
    // Actualizar la venta con los valores convertidos
    this.venta.pacienteId = pacienteIdNum;
    this.venta.usuarioId = usuarioIdNum;
  
    console.log('Venta a guardar:', this.venta);
  
    this.ventasService.registrarVenta(this.venta).subscribe(() => {
      alert('Venta guardada correctamente');
      this.venta = { pacienteId: 0, usuarioId: 0, detalles: [] };
      this.nuevoDetalle = { productoId: 0, cantidad: 1 };
      this.mostrarFormulario = false;
      this.cargarVentas();
    });
  }

  onPacienteChange(): void {
    console.log('Paciente seleccionado:', this.venta.pacienteId, 'tipo:', typeof this.venta.pacienteId);
  }
  
  onUsuarioChange(): void {
    console.log('Usuario seleccionado:', this.venta.usuarioId, 'tipo:', typeof this.venta.usuarioId);
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
