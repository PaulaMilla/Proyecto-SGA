export interface InventarioProducto {
  id_inventario: number;
  id_producto: number;
  cantidad_disponible: number;
  ubicacion: string;
  lote: string;
  fecha_vencimiento: string;
  nombre_farmacia: string;

  // datos del producto
  nombre: string;
  descripcion: string;
  laboratorio: string;
  tipo: string;
  precio_unitario: number;
}
