export interface Movimiento {
  id: number;
  turnoCajaId: number;
  monto: number;
  tipo: 'INGRESO' | 'EGRESO';
  descripcion: string;
  fechaHora: string;
  idVenta?: number;
  idCompra?: number;
}