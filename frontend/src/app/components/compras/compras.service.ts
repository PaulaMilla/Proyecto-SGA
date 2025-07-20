import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ComprasService {
  private baseUrl = 'http://34.61.182.228/api/compras';
  
    constructor(private http: HttpClient) {}
  
    crearCompra(data: any): Observable<any> {
      return this.http.post(`${this.baseUrl}`, data);
    }
  
    getProveedores(): Observable<any[]> {
      return this.http.get<any[]>('/api/proveedores');
    }
}
