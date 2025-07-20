import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Usuario, UsuarioRequestDTO, UsuarioUpdateDTO} from "./model/usuarios.model";
import {UsuariosService} from "../services/usuarios.service";

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.scss']
})
export class UsuariosComponent implements OnInit {

  usuarios: Usuario[] = [];
  roles: any[] = [];

  usuarioDTO: UsuarioRequestDTO = {
    nombre: '',
    apellido: '',
    correo: '',
    password: '',
    estado: '',
    nombre_farmacia: '',
    idRol: 0 // o null si prefieres validar antes
  };

  usuarioEditando: Usuario | null = null;


  constructor(private usuarioService: UsuariosService) {}

  ngOnInit(): void {
    this.cargarUsuarios();
    this.cargarRoles();
  }

  cargarUsuarios(): void {
    this.usuarioService.obtenerTodas().subscribe({
      next: (data) => this.usuarios = data,
      error: (err) => console.error('Error al cargar usuarios:', err)
    });
  }

  cargarRoles(): void {
    this.usuarioService.obtenerRoles().subscribe({
      next: data => this.roles = data,
      error: err => console.error('Error al cargar roles:', err)
    });
  }

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
        this.cargarUsuarios();
      },
      error: err => {
        console.error(err);
        alert('Error al registrar usuario');
      }
    });
  }

  editarUsuario(usuario: any): void {
    const dto: UsuarioUpdateDTO = {
      id: usuario.id,
      estado: usuario.estado,
      idRol: usuario.rol.id // O usuario.rol si solo tienes el ID directo
    };

    this.usuarioService.actualizar(dto).subscribe({
      next: () => {
        console.log('Usuario actualizado');
        this.cargarUsuarios(); // Recarga la lista completa
      },
      error: err => {
        console.error('Error al actualizar usuario:', err);
      }
    });
  }

  actualizarUsuario(): void {
    if (this.usuarioEditando) {
      const dto = {
        id: this.usuarioEditando.id,
        estado: this.usuarioEditando.estado,
        idRol: this.usuarioEditando.rol.id,
      };

      this.usuarioService.actualizar(dto).subscribe({
        next: () => {
          this.cargarUsuarios();
          this.usuarioEditando = null;
        },
        error: err => console.error('Error al actualizar:', err)
      });
    }
  }


  eliminarUsuario(id: number) {
    console.log(id);
    if (confirm('¿Seguro que deseas eliminar este usuario?')) {
      this.usuarioService.eliminar(id).subscribe({
        next: () => this.cargarUsuarios(),
        error: err => console.error('Error al eliminar:', err)
      });
    }
  }

}
