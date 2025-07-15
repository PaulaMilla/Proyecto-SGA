import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VentasComponent } from './ventas/ventas.component';
import { UploadComponent as InventarioUploadComponent } from './inventario/upload/upload.component';
import { UploadComponent as PacientesUploadComponent } from './pacientes/upload/upload.component';
import { UsuariosComponent } from './usuarios/usuarios.component';
import { HomeComponent } from './components/home/home.component';
import { authGuard } from './guards/auth.guard';
import { LoginComponent } from './components/login/login.component';
import { InventarioInfoComponent } from './inventario/inventario-info/inventario-info.component';
import { CargaDatosComponent } from './components/carga-datos/carga-datos.component';
import { MenuPrincipalComponent } from './components/menu-principal/menu-principal.component'; // 👈 AGREGADO

const routes: Routes = [
  { path: '', component: MenuPrincipalComponent }, // 👈 CAMBIADO: ahora el inicio es público
  { path: 'login', component: LoginComponent },
  { path: 'ventas', component: VentasComponent, canActivate: [authGuard] },
  { path: 'usuarios', component: UsuariosComponent, canActivate: [authGuard] },
  { path: 'carga-datos', component: CargaDatosComponent, canActivate: [authGuard] },
  { path: 'inventario-info', component: InventarioInfoComponent, canActivate: [authGuard] },
  { path: 'inventario/upload', component: InventarioUploadComponent, canActivate: [authGuard] },
  { path: 'pacientes/upload', component: PacientesUploadComponent, canActivate: [authGuard] },
  { path: 'home', component: HomeComponent, canActivate: [authGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
