import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface FraccionamientoRequest {
  idInventario: number;
  cantidadFraccionada: number;
  nuevoLote: string;
  emailUsuario: string; // <-- NUEVO
}


@Injectable({ providedIn: 'root' })
export class FraccionamientoService {
  constructor(private http: HttpClient) {}

  fraccionar(request: FraccionamientoRequest): Observable<any> {
    return this.http.post('http://34.61.182.228/api/fraccionamiento', request);
  }
}

