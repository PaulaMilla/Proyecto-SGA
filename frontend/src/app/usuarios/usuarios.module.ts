import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // <-- Importa FormsModule aquí

import { UsuariosRoutingModule } from './usuarios-routing.module';
import { UsuariosComponent } from './usuarios.component';

@NgModule({
  declarations: [
    UsuariosComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    UsuariosRoutingModule
  ],
  exports: [
    UsuariosComponent
  ]
})
export class UsuariosModule { }
