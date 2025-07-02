import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {InventarioProducto} from "../model/inventarioproducto.model";

@Injectable({
  providedIn: 'root'
})
export class InventarioService {

  private apiUrl = 'http://34.61.182.228/api/inventarios'; // Cambiar si usas gateway

  constructor(private http: HttpClient) {}

  getInventarioConProducto(): Observable<InventarioProducto[]> {
    return this.http.get<InventarioProducto[]>(`${this.apiUrl}/con-producto`);
  }

  subirArchivoInventario(formData: FormData): Observable<any> {

    // No establecer Content-Type manualmente, dejar que el navegador lo haga
    const headers = new HttpHeaders();

    return this.http.post(`${this.apiUrl}/upload`, formData, { headers });
  }

  // Método para probar la carga de archivos
  testUpload(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file, file.name);

    const headers = new HttpHeaders();

    return this.http.post(`${this.apiUrl}/test-upload`, formData, { headers });
  }

  // Método para obtener información de productos
  getProductosInfo(): Observable<any> {
    return this.http.get(`${this.apiUrl}/productos-info`, { responseType: 'text' });
  }

  // Método para crear productos de prueba
  createTestProducts(): Observable<any> {
    return this.http.post(`${this.apiUrl}/create-test-products`, {}, { responseType: 'text' });
  }
}
