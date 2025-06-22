import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VentasComponent } from './ventas/ventas.component';
import { UploadComponent } from './inventario/upload/upload.component';
import { UsuariosComponent } from './usuarios/usuarios.component';
import { HomeComponent } from './components/home/home.component';
import { authGuard } from './guards/auth.guard';
import { LoginComponent } from './components/login/login.component';
import {InventarioInfoComponent} from "./inventario/inventario-info/inventario-info.component";

const routes: Routes = [
  { path: 'ventas', component: VentasComponent, canActivate: [authGuard] },
  { path: 'usuarios', component: UsuariosComponent, canActivate: [authGuard] },
  { path: 'inventario-info', component: InventarioInfoComponent, canActivate: [authGuard]},
  { path: 'upload', component: UploadComponent, canActivate: [authGuard] },
  { path: 'home', component: HomeComponent, canActivate: [authGuard]},
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
