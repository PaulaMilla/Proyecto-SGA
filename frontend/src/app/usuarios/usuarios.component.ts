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
    rol: ''
  };

  usuarios: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios() {
    this.http.get<any[]>('https://TUDOMINIO/api/usuarios').subscribe(data => {
      this.usuarios = data;
    });
  }

  guardarUsuario() {
    if (this.usuario.id) {
      this.http.put(`https://TUDOMINIO/api/usuarios/${this.usuario.id}`, this.usuario).subscribe(() => {
        this.usuario = { nombre: '', apellido: '', correo: '', rol: '' };
        this.cargarUsuarios();
      });
    } else {
      this.http.post('https://TUDOMINIO/api/usuarios', this.usuario).subscribe(() => {
        this.usuario = { nombre: '', apellido: '', correo: '', rol: '' };
        this.cargarUsuarios();
      });
    }
  }

  editarUsuario(u: any) {
    this.usuario = { ...u };
  }

  eliminarUsuario(id: number) {
    this.http.delete(`https://TUDOMINIO/api/usuarios/${id}`).subscribe(() => {
      this.cargarUsuarios();
    });
  }
}
