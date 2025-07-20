import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Venta, VentaConDetallesDTO, VentaRegistrada} from "../model/ventas.model";

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
  registrarVenta(dto: VentaConDetallesDTO): Observable<any> {
    return this.http.post(`${this.apiUrl}`, dto);
  }

  // GET /ventas
  obtenerTodas(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}`);
  }

  getTodosPacientes(): Observable<any[]> {
    return this.http.get<any[]>('/api/pacientes');
  }

  getTodosUsuarios(): Observable<any[]> {
    return this.http.get<any[]>('/api/usuarios');
  }

  getTodosProductos(): Observable<any[]> {
    return this.http.get<any[]>('/api/inventarios/productos');
  }

  eliminarVenta(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getDetallesPorVentaId(ventaId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${ventaId}/detalles`);
  }

  getProductoPorId(productoId: number): Observable<any> {
    return this.http.get(`http://34.61.182.228/api/inventarios/productos/${productoId}`);
  }

  getPacientePorId(id: number): Observable<any> {
    return this.http.get(`http://34.61.182.228/api/pacientes/${id}`);
  }

  getUsuarioPorId(id: number): Observable<any> {
    return this.http.get(`http://34.61.182.228/api/usuarios/${id}`);
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
