export interface DetalleCompra {
  productoId: number | null;
  cantidad: number;
  precioUnitario: number;
  lote: string;
  fechaVencimiento: string; // formato ISO (yyyy-mm-dd)
  nombreProveedor?: string;
}

export interface Compra {
  numeroDocumento: string;
  tipo: 'FACTURA' | 'GUIA_DESPACHO' | 'NOTA_CREDITO';
  proveedorId: number | null;
  observacion: string;
  bodega: string;
  farmaciaId: number;
  detalles: DetalleCompra[];
}