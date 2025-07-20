import { Component } from '@angular/core';
import { ComprasService } from './compras.service';
import { InventarioService } from '../../inventario/services/inventario.service';

@Component({
  selector: 'app-compras',
  templateUrl: './compras.component.html',
  styleUrl: './compras.component.scss'
})
export class ComprasComponent {
  proveedores: any[] = [];
  productos: any[] = [];
  detalles: DetalleCompra[] = [];

  compra: Compra = {
    numeroDocumento: '',
    tipo: 'FACTURA',
    proveedorId: null,
    observacion: '',
    bodega: '',
    farmaciaId: 1, // podrías reemplazarlo por uno obtenido del perfil o token
    detalles: []
  };

  constructor(
    private comprasService: ComprasService,
    private inventariosService: InventarioService
  ) {}

  ngOnInit(): void {
    this.cargarProveedores();
    this.cargarProductos();
    this.agregarDetalle(); // para iniciar con un producto vacío
  }

  cargarProveedores(): void {
    this.comprasService.getProveedores().subscribe(data => this.proveedores = data);
  }

  cargarProductos(): void {
    this.inventariosService.getProductosPublicos().subscribe(data => this.productos = data);
  }

  
  agregarDetalle(): void {
    const nuevoDetalle: DetalleCompra = {
      productoId: null,
      cantidad: 1,
      precioUnitario: 0,
      lote: '',
      fechaVencimiento: ''
    };
    this.detalles.push(nuevoDetalle);
  }

  eliminarDetalle(index: number): void {
    this.detalles.splice(index, 1);
  }

  registrarCompra(): void {
    this.compra.detalles = this.detalles;

    this.comprasService.crearCompra(this.compra).subscribe({
      next: () => {
        alert('Compra registrada con éxito');
        this.resetFormulario();
      },
      error: () => {
        alert('Error al registrar la compra');
      }
    });
  }

  resetFormulario(): void {
    this.compra = {
      numeroDocumento: '',
      tipo: 'FACTURA',
      proveedorId: null,
      observacion: '',
      bodega: '',
      farmaciaId: 1,
      detalles: []
    };
    this.detalles = [];
    this.agregarDetalle();
  }

}
