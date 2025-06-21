import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VentasComponent } from './ventas/ventas.component';
import { UploadComponent } from './inventario/upload/upload.component';
import { UsuariosComponent } from './usuarios/usuarios.component';
import { HomeComponent } from './components/home/home.component';

const routes: Routes = [
  {path: 'ventas', component: VentasComponent},
  { path: 'usuarios', component: UsuariosComponent},
  {path: 'upload', component: UploadComponent},
  {path: '', component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
