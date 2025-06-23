import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.scss']
})
export class UsuariosComponent implements OnInit {

  usuario: any = {
    nombre: '',
    apellido: '',
    correo: '',
    rol: '',
    estado: '',
    fechaUltimoAcceso: ''
  };

  usuarios: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios() {
    this.http.get<any[]>('http://34.61.182.228/api/usuarios').subscribe(data => {
      this.usuarios = data;
    });
  }

  guardarUsuario() {
    if (this.usuario.id) {
      this.http.put(`http://34.61.182.228/api/usuarios/${this.usuario.id}`, this.usuario).subscribe(() => {
        this.resetFormulario();
        this.cargarUsuarios();
      });
    } else {
      this.http.post('http://34.61.182.228/api/usuarios', this.usuario).subscribe(() => {
        this.resetFormulario();
        this.cargarUsuarios();
      });
    }
  }

  editarUsuario(u: any) {
    this.usuario = { ...u };
  }

  eliminarUsuario(id: number) {
eliminarUsuario(id: number) {
  console.log(id);
  if (confirm('¿Seguro que deseas eliminar este usuario?')) {
    this.http.delete(`http://34.61.182.228/api/usuarios/${id}`).subscribe(() => {
      this.cargarUsuarios();
    });
  }
}

  }

  resetFormulario() {
    this.usuario = {
      nombre: '',
      apellido: '',
      correo: '',
      rol: '',
      estado: '',
      fechaUltimoAcceso: ''
    };
  }
}
