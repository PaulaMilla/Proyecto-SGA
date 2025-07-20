import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Usuario} from "./model/usuarios.model";
import {UsuariosService} from "../services/usuarios.service";

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.scss']
})
export class UsuariosComponent implements OnInit {

  usuarios: Usuario[] = [];

  constructor(private usuarioService: UsuariosService) {}

  ngOnInit(): void {
    this.usuarioService.obtenerTodas().subscribe({
      next: data => this.usuarios = data,
      error: err => console.error('Error al obtener usuarios:', err)
    });
  }

  /*cargarUsuarios() {
    this.http.get<any[]>('http://34.61.182.228/api/usuarios').subscribe(data => {
      this.usuarios = data;
    });
  }¨*/

  /*guardarUsuario() {
    if (this.usuarios.id) {
      this.http.put(`http://34.61.182.228/api/usuarios/${this.usuario.id}`, this.usuario).subscribe(() => {
        this.resetFormulario();
        this.cargarUsuarios();
      });
    } else {
      this.http.post('http://34.61.182.228/api/usuarios', this.usuarios).subscribe(() => {
        this.resetFormulario();
      });
    }
  }*/

  editarUsuario(u: any) {
    this.usuarios = { ...u };
  }

  eliminarUsuario(id: number) {
    console.log(id);
    if (confirm('¿Seguro que deseas eliminar este usuario?')) {
      this.usuarioService.eliminar(id).subscribe({
        next: () => this.usuarios = this.usuarios.filter(u => u.id !== id),
        error: err => console.error('Error al eliminar:', err)
      });
    }
  }

}
