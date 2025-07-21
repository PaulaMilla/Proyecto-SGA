import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Rol, Usuario, UsuarioRequestDTO, UsuarioUpdateDTO} from "./model/usuarios.model";
import {UsuariosService} from "../services/usuarios.service";

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.scss']
})
export class UsuariosComponent implements OnInit {

  usuarios: Usuario[] = [];
  roles: Rol[] = [];

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
  farmacias: string[] = [];


  constructor(private usuarioService: UsuariosService) {}

  ngOnInit(): void {
    this.cargarUsuarios();
    this.cargarRoles();
    this.cargarFarmacias();
  }

  cargarFarmacias(): void {
    this.usuarioService.obtenerNombresFarmacias().subscribe({
      next: data => this.farmacias = data,
      error: err => console.error('Error al cargar farmacias:', err)
    });
  }

  cargarUsuarios(): void {
    this.usuarioService.obtenerUsuarios().subscribe({
      next: (data) => this.usuarios = data,
      error: (err) => console.error('Error al cargar usuarios:', err)
    });
  }

  cargarRoles(): void {
    this.usuarioService.obtenerRoles().subscribe({
      next: (data) => this.roles = data,
      error: (err) => console.error('Error al cargar roles:', err)
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

  eliminarUsuario(id: number): void {
    if (confirm('¿Estás seguro de eliminar este usuario?')) {
      this.usuarioService.eliminar(id).subscribe({
        next: () => {
          alert('Usuario eliminado correctamente');
          this.cargarUsuarios(); // Recargar lista después de eliminar
        },
        error: (err) => {
          console.error('Error al eliminar usuario:', err);
          alert('No se pudo eliminar el usuario');
        }
      });
    }
  }

}
