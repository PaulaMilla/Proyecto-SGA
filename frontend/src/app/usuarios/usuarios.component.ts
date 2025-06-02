import { Component } from '@angular/core';
import { Usuario, UsuariosService } from '../services/usuarios.service';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrl: './usuarios.component.scss'
})
export class UsuariosComponent {
  usuarios: Usuario[] = [];
  
    constructor(private usuarioService: UsuariosService) {}
  
    ngOnInit() {
      this.usuarioService.obtenerTodas().subscribe(data => {
        this.usuarios = data;
      });
    }


}
