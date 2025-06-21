import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.scss']
})
export class UsuariosComponent implements OnInit {

  usuario: any = {
    id: null,
    nombre: '',
    apellido: '',
    correo: '',
    rol: ''
  };

  usuarios: any[] = [];
  mostrarFormulario = false;
  editando = false;

  private apiUrl = 'http://34.61.182.228/api/usuarios';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios() {
    this.http.get<any[]>(this.apiUrl).subscribe(data => {
      this.usuarios = data;
    });
  }

  guardarUsuario() {
    if (this.editando && this.usuario.id) {
      this.http.put(`${this.apiUrl}/${this.usuario.id}`, this.usuario).subscribe(() => {
        this.resetFormulario();
        this.cargarUsuarios();
      });
    } else {
      this.http.post(this.apiUrl, this.usuario).subscribe(() => {
        this.resetFormulario();
        this.cargarUsuarios();
      });
    }
  }

  editarUsuario(u: any) {
    this.usuario = { ...u };
    this.mostrarFormulario = true;
    this.editando = true;
  }

  eliminarUsuario(id: number) {
    if (confirm('¿Seguro que deseas eliminar este usuario?')) {
      this.http.delete(`${this.apiUrl}/${id}`).subscribe(() => {
        this.cargarUsuarios();
      });
    }
  }

  mostrarFormularioNuevo() {
    this.resetFormulario();
    this.mostrarFormulario = true;
    this.editando = false;
  }

  cancelar() {
    this.resetFormulario();
  }

  private resetFormulario() {
    this.usuario = { id: null, nombre: '', apellido: '', correo: '', rol: '' };
    this.mostrarFormulario = false;
    this.editando = false;
  }
}
