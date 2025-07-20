import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import {InventarioProducto} from "../model/inventarioproducto.model";

@Injectable({
  providedIn: 'root'
})
export class InventarioService {

  private apiUrl = 'http://34.61.182.228/api/inventarios'; // Cambiar si usas gateway

  constructor(private http: HttpClient) {}

  getTodosProductos(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/productos`);
  }

  getInventarioConProducto(emailUsuario: string): Observable<InventarioProducto[]> {
    let params = new HttpParams();
    params = params.append('emailUsuario', emailUsuario); // Nombre EXACTO del parámetro

    return this.http.get<InventarioProducto[]>(`${this.apiUrl}/con-producto`, { params });
  }

  subirArchivoInventario(formData: FormData): Observable<any> {

    // No establecer Content-Type manualmente, dejar que el navegador lo haga
    const headers = new HttpHeaders();

    return this.http.post(`${this.apiUrl}/upload`, formData, { headers });
  }

  subirArchivoPrecios(formData: FormData): Observable<any> {

    // No establecer Content-Type manualmente, dejar que el navegador lo haga
    const headers = new HttpHeaders();

    return this.http.post(`${this.apiUrl}/upload-precios`, formData, { headers });
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

  fraccionarInventario(request: any) {
    return this.http.post('http://34.61.182.228/api/fraccionamiento', request);
  }

  dispersarMedicamento(request: any) {
    return this.http.post('http://34.61.182.228/api/dispersion/dispersar', request);
  }

  obtenerInventarios() {
    return this.http.get<any[]>('http://34.61.182.228/api/inventario');
  }



  // Método para crear productos de prueba
  createTestProducts(): Observable<any> {
    return this.http.post(`${this.apiUrl}/create-test-products`, {}, { responseType: 'text' });
  }
  getProductosPublicos(): Observable<any[]> {
    return this.http.get<any[]>('http://34.61.182.228/api/inventarios/public-productos');
  }


}

