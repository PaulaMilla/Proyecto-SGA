import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Venta, VentaConDetalles, VentaRegistrada} from "../model/ventas.model";

/* export interface Venta {
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
} */

@Injectable({
  providedIn: 'root'
})
export class VentasService {

  private apiUrl = 'http://34.61.182.228/api/ventas';

  constructor(private http: HttpClient) {}

  // POST /ventas
  registrarVenta(venta: VentaConDetalles): Observable<any> {
    return this.http.post(`${this.apiUrl}`, venta);
  }

  // GET /ventas
  obtenerTodas(): Observable<VentaRegistrada[]> {
    return this.http.get<VentaRegistrada[]>(`${this.apiUrl}`);
  }

  eliminarVenta(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getProductoPorId(productoId: number): Observable<any> {
    return this.http.get(`http://34.61.182.228/api/inventarios/productos/${productoId}`);
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
