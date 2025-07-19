export interface VentaConNombresYDetalles {
  id: number;
  pacienteId: number;
  usuarioId: number;
  fechaVenta: string;
  total: number;
  nombrePaciente: string;
  nombreUsuario: string;
  detalles: DetalleVentaConNombre[];
}

export interface DetalleVentaConNombre {
  productoId: number;
  cantidad: number;
  precioUnitario: number;
  subtotal: number;
  nombreProducto: string;
}
