import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface FraccionamientoRequest {
  idInventario: number;
  cantidadFraccionada: number;
  nuevoLote: string;
}

@Injectable({
  providedIn: 'root',
})
export class FraccionamientoService {
  private apiUrl = 'http://34.61.182.228/api/fraccionamiento';

  constructor(private http: HttpClient) {}

  fraccionar(data: FraccionamientoRequest): Observable<any> {
    return this.http.post(this.apiUrl, data);
  }
}
