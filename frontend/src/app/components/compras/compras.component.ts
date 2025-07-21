import { Component } from '@angular/core';
import { ComprasService } from './compras.service';
import { InventarioService } from '../../inventario/services/inventario.service';
import { Compra, DetalleCompra } from './model/compra.model';

@Component({
  selector: 'app-compras',
  templateUrl: './compras.component.html',
  styleUrl: './compras.component.scss'
})
export class ComprasComponent {
  proveedores: any[] = [];
  productos: any[] = [];
  detalles: DetalleCompra[] = [];

  selectedFile: File | undefined;

  compra: Compra = {
    numeroDocumento: '',
    tipo: 'FACTURA',
    proveedorId: null,
    observacion: '',
    bodega: '',
    farmaciaId: 1, 
    detalles: []
  };

  constructor(
    private comprasService: ComprasService,
    private inventariosService: InventarioService
  ) {}

  ngOnInit(): void {
    this.definirIdFarmacia();
    this.cargarProveedores();
    this.cargarProductos();
    this.agregarDetalle(); // para iniciar con un producto vacío
  }
  
  definirIdFarmacia(): void{
    const email = localStorage.getItem('email');
    if (!email) return;
  
    this.comprasService.getNombreFarmaciaPorEmail(email).subscribe({
      next: nombre => {
        this.inventariosService.getIdFarmaciaPorNombre(nombre).subscribe({
          next: id => this.compra.farmaciaId = id,
          error: () => console.warn('No se pudo obtener ID de farmacia')
        });
      },
      error: () => console.warn('No se pudo obtener nombre de farmacia')
    });
  }
  cargarProveedores(): void {
    this.comprasService.getProveedores().subscribe(data => this.proveedores = data);
  }

  cargarProductos(): void {
    this.inventariosService.getProductosPublicos().subscribe(data => this.productos = data);
  }


  agregarDetalle(): void {
    const proveedor = this.proveedores.find(p => p.id === this.compra.proveedorId);
    const nombre = proveedor?.nombre || '';
  
    const nuevoDetalle: DetalleCompra = {
      productoId: null,
      cantidad: 1,
      precioUnitario: 0,
      lote: '',
      fechaVencimiento: '',
      nombreProveedor: nombre
    };
  
    this.detalles.push(nuevoDetalle);
    }

  eliminarDetalle(index: number): void {
    this.detalles.splice(index, 1);
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  onProveedorSeleccionado(): void{
    const proveedor = this.proveedores.find(p => p.id === this.compra.proveedorId);
    const nombre = proveedor?.nombre || '';

    this.detalles.forEach(d => d.nombreProveedor = nombre);
  }

  registrarCompra(): void {
    this.compra.detalles = this.detalles;
  
    this.comprasService.crearCompra(this.compra, this.selectedFile).subscribe({
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
