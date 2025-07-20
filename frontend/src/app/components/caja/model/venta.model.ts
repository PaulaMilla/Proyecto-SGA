export interface Venta {
  id: number;
  pacienteId: number;
  fechaVenta: string;
  total: number;
  usuarioId: number;
  pagada: boolean;
}