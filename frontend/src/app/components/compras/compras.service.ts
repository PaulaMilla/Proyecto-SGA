import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Compra } from './model/compra.model';

@Injectable({
  providedIn: 'root'
})
export class ComprasService {
  private baseUrl = 'http://34.61.182.228/api/compras';
  private usuarioUrl = 'http://34.61.182.228/api/usuarios';

  constructor(private http: HttpClient) {}

  crearCompra(compra: Compra, archivo?: File): Observable<any> {
    const formData = new FormData();

    const compraJson = JSON.stringify(compra);
    formData.append('compra', new Blob([compraJson], { type: 'application/json' }));

    if (archivo) {
      formData.append('file', archivo);
    }

    return this.http.post(`${this.baseUrl}`, formData);
  }

  getProveedores(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/proveedores`);
  }

  getNombreFarmaciaPorEmail(email: string): Observable<string> {
    return this.http.get(`${this.usuarioUrl}/nombre-farmacia/${email}`, { responseType: 'text' });
  }

  getCompras(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}`);
  }
  
  subirDocumento(compraId: number, archivo: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', archivo);
    return this.http.post(`${this.baseUrl}/${compraId}/documento`, formData);
  }
}