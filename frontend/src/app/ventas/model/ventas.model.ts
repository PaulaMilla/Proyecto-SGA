export interface Venta {
  pacienteId?: number;
  productoId?: number;
  cantidad: number;
  precioUnitario?: number; // Opcional porque lo carga automáticamente
  total?: number;
}

export interface VentaRegistrada {
  cliente: string;
  producto: string;
  cantidad: number;
  precioUnitario: number;
  total: number;
}

export interface DetalleVenta {
  productoId?: number;
  cantidad: number;
}

export interface VentaConDetalles {
  pacienteId?: number;
  usuarioId?: number;
  detalles: DetalleVenta[];
}



