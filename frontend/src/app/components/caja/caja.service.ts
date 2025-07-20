import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Movimiento } from './model/movimiento.model';
import { TurnoCaja } from './model/turno-caja.model';
import { Caja } from './model/caja.model';
import { Observable } from 'rxjs';
import { Venta } from './model/venta.model';

@Injectable({
  providedIn: 'root'
})
export class CajaService {
  private apiUrl = 'http://34.61.182.228/api/caja';
  private ventasUrl = 'http://34.61.182.228/api/ventas';

  constructor(private http: HttpClient) { }

  obtenerTodas(): Observable<Caja[]> {
    return this.http.get<Caja[]>(this.apiUrl);
  }

  obtenerTurnoActual(cajaId: number): Observable<TurnoCaja> {
    return this.http.get<TurnoCaja>(`${this.apiUrl}/turno-actual/${cajaId}`);
  }

  obtenerVentasNoPagadas(): Observable<Venta[]> {
    return this.http.get<Venta[]>(`${this.ventasUrl}/no-pagadas`);
  }

  registrarPago(cajaId: number, venta: Venta, metodo: string): Observable<any> {
    const body = new URLSearchParams();
    body.set('turnoId', cajaId.toString());
    body.set('idVenta', venta.id.toString());
    body.set('monto', venta.total.toString()); // Completa con el monto
    body.set('metodo', metodo);

    return this.http.post(`${this.apiUrl}/pago`, body.toString(), {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
    });
  }

  registrarMovimiento(cajaId: number, tipo: string, monto: number, descripcion: string): Observable<any> {
    const body = new URLSearchParams();
    body.set('turnoId', cajaId.toString());
    body.set('monto', monto.toString());
    body.set('tipo', tipo);
    body.set('descripcion', descripcion);

    return this.http.post(`${this.apiUrl}/movimiento`, body.toString(), {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
    });
  }

  obtenerMovimientosPorCaja(cajaId: number): Observable<Movimiento[]> {
    return this.http.get<Movimiento[]>(`${this.apiUrl}/movimientos/${cajaId}`);
  }

  abrirTurno(cajaId: number, emailUsuario: string, montoApertura: number): Observable<TurnoCaja> {
    return this.http.post<TurnoCaja>(`${this.apiUrl}/abrir-turno`, null, {
      params: {
        cajaId: cajaId.toString(),
        emailUsuario,
        montoApertura: montoApertura.toString()
      }
    });
  }
  
  cerrarTurno(turnoId: number): Observable<TurnoCaja> {
    return this.http.post<TurnoCaja>(`${this.apiUrl}/cerrar-turno`, null, {
      params: {
        turnoId: turnoId.toString()
      }
    });
  }
}
