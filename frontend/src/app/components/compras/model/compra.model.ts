interface DetalleCompra {
  productoId: number | null;
  cantidad: number;
  precioUnitario: number;
  lote: string;
  fechaVencimiento: string;
}

interface Compra {
  numeroDocumento: string;
  tipo: string;
  proveedorId: number | null;
  observacion: string;
  bodega: string;
  farmaciaId: number;
  detalles: DetalleCompra[];
}