import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InventarioService {

  private apiUrl = 'http://api-gateway.local/api/inventarios'; // Cambiar si usas gateway

  constructor(private http: HttpClient) {}

  subirArchivoInventario(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file, file.name);
    
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
