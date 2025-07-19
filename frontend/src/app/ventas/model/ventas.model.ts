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

export interface DetalleVentaForm {
  productoId: number;
  productoNombre: string;
  cantidad: number;
  precioUnitario: number;
  subtotal: number;
}

export interface VentaForm {
  pacienteId?: number;
  usuarioId?: number;
  detalles: DetalleVentaForm[];
}

export interface DetalleVentaDTO {
  productoId: number;
  cantidad: number;
}

export interface VentaConDetallesDTO {
  pacienteId: number;
  usuarioId: number;
  detalles: DetalleVentaDTO[];
}

/*export interface DetalleVenta {
  productoId?: number;
  cantidad: number;
}

export interface VentaConDetalles {
  pacienteId?: number;
  usuarioId?: number;
  detalles: DetalleVenta[];
}*/

export interface Producto {
  id: number;
  nombre: string;
  precio_unitario: number;
}

export interface Paciente {
  id: number;
  nombre: string;
}

export interface Usuario {
  id: number;
  nombre: string;
}






