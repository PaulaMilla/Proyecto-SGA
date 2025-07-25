import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Rol, Usuario, UsuarioRequestDTO, UsuarioUpdateDTO} from "../usuarios/model/usuarios.model";


/*export interface Usuario {
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
}*/

@Injectable({
  providedIn: 'root'
})
export class UsuariosService {

  constructor(private http: HttpClient) { }

  private apiUrl = 'http://34.61.182.228/api/usuarios';

  // POST /usuarios
    registrarVenta(usuario: Usuario): Observable<Usuario> {
      return this.http.post<Usuario>(`${this.apiUrl}`, usuario);
    }

  obtenerPorId(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.apiUrl}/${id}`);
  }

  obtenerNombresFarmacias(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/farmacias`);
  }

  crear(usuario: UsuarioRequestDTO): Observable<any> {
    return this.http.post(`${this.apiUrl}`, usuario); // cambia el endpoint según tu ruta real
  }

  eliminar(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  actualizar(dto: UsuarioUpdateDTO): Observable<any> {
    return this.http.put(`${this.apiUrl}/usuarios`, dto);
  }

  // GET /usuarios
    obtenerTodas(): Observable<Usuario[]> {
      return this.http.get<Usuario[]>(`${this.apiUrl}`);
    }

  obtenerUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.apiUrl}/response`);
  }

  obtenerRoles():Observable<Rol[]> {
    return this.http.get<Rol[]>(`${this.apiUrl}/roles`);
  }


}
