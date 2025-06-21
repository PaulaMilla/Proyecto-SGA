import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VentasComponent } from './ventas/ventas.component';
import { UploadComponent } from './inventario/upload/upload.component';
import { UsuariosComponent } from './usuarios/usuarios.component';
import { HomeComponent } from './components/home/home.component';

const routes: Routes = [
  {path: 'ventas', component: VentasComponent},
  { path: 'usuarios', component: UsuariosComponent},
  { path: 'usuarios', loadChildren: () => import('./usuarios/usuarios.module').then(m => m.UsuariosModule)},
  {path: 'upload', component: UploadComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
