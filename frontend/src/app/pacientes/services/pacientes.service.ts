import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PacientesService {

  private apiUrl = 'http://34.61.182.228/api/pacientes'; // Cambiar si usas gateway

  constructor(private http: HttpClient) {}

  obtenerTodos(): Observable<any[]> {
    return this.http.get<any[]>('http://34.61.182.228/api/pacientes');
  }

  subirArchivoPacientes(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file, file.name);

    // No establecer Content-Type manualmente, dejar que el navegador lo haga
    const headers = new HttpHeaders();

    return this.http.post(`${this.apiUrl}/upload`, formData, { headers });
  }

  // Metodo para probar la carga de archivos
  testUpload(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file, file.name);

    const headers = new HttpHeaders();

    return this.http.post(`${this.apiUrl}/test-upload`, formData, { headers });
  }
  obtenerPacientes(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  listarTodos(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}`);


  }
}
