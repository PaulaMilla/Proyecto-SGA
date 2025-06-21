import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Venta {
  id?: number;
  pacienteId: number;
  fechaVenta: string;
  total: number;
  usuarioId: number;
}

export interface DetalleVenta {
  id?: number;
  ventaId?: number;
  productoId: number;
  cantidad: number;
  precioUnitario: number;
  subtotal: number;
}
@Injectable({
  providedIn: 'root'
})
export class VentasService {

  private apiUrl = 'http://34.61.182.228/api/ventas';

  constructor(private http: HttpClient) {}

  // POST /ventas
  registrarVenta(venta: Venta): Observable<Venta> {
    return this.http.post<Venta>(`${this.apiUrl}`, venta);
  }

  // GET /ventas
  obtenerTodas(): Observable<Venta[]> {
    return this.http.get<Venta[]>(`${this.apiUrl}`);
  }

  // GET /ventas/{id}
  obtenerPorId(id: number): Observable<Venta> {
    return this.http.get<Venta>(`${this.apiUrl}/${id}`);
  }

  // GET /ventas/paciente/{pacienteId}
  obtenerPorPaciente(pacienteId: number): Observable<Venta[]> {
    return this.http.get<Venta[]>(`${this.apiUrl}/paciente/${pacienteId}`);
  }

  // GET /ventas/fecha?desde=...&hasta=...
  obtenerPorRangoFechas(desde: string, hasta: string): Observable<Venta[]> {
    return this.http.get<Venta[]>(`${this.apiUrl}/fecha`, {
      params: {
        desde,
        hasta
      }
    });
  }
}
