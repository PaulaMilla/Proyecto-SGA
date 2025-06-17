import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


export interface Usuario {
  id?: number;
  apellido: string;
  correo: string;
  estado: string;
  fechaCreacion: string;
  fechaModificacion: string;
  fechaUltimoAcceso: string;
  nombre: string;
  password: string;
  rol: string;
}

@Injectable({
  providedIn: 'root'
})
export class UsuariosService {

  constructor(private http: HttpClient) { }

  apiUrl = 'http://api-gateway.local/api/usuarios';

  // POST /usuarios
    registrarVenta(usuario: Usuario): Observable<Usuario> {
      return this.http.post<Usuario>(`${this.apiUrl}`, usuario);
    }

  // GET /usuarios
    obtenerTodas(): Observable<Usuario[]> {
      return this.http.get<Usuario[]>(`${this.apiUrl}`);
    }

}
