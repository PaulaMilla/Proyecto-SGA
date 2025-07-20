import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Usuario, UsuarioRequestDTO} from "./model/usuarios.model";
import {UsuariosService} from "../services/usuarios.service";

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.scss']
})
export class UsuariosComponent implements OnInit {

  usuarios: Usuario[] = [];

  usuarioDTO: UsuarioRequestDTO = {
    nombre: '',
    apellido: '',
    correo: '',
    password: '',
    estado: '',
    nombre_farmacia: '',
    idRol: 0 // o null si prefieres validar antes
  };

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

  guardarUsuario(): void {
    if (!this.usuarioDTO.idRol) {
      alert('Debe seleccionar un rol válido');
      return;
    }

    this.usuarioService.crear(this.usuarioDTO).subscribe({
      next: res => {
        alert('Usuario registrado');
        this.usuarioDTO = {
          nombre: '',
          apellido: '',
          correo: '',
          password: '',
          estado: '',
          nombre_farmacia: '',
          idRol: 0
        };
      },
      error: err => {
        console.error(err);
        alert('Error al registrar usuario');
      }
    });
  }

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
