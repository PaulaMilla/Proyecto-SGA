export interface TurnoCaja {
  id: number;
  cajaId: number;
  idUsuario: number;
  horaApertura: string;
  horaCierre?: string;
  montoApertura: number;
  montoCierre?: number;
  cerrado: boolean;
}