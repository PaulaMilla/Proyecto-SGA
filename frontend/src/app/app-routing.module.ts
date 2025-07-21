import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VentasComponent } from './ventas/ventas.component';
import { UploadComponent as InventarioUploadComponent } from './inventario/upload/upload.component';
import { UploadComponent as PacientesUploadComponent } from './pacientes/upload/upload.component';
import { UploadPreciosComponent} from "./inventario/upload-precios/upload-precios.component";
import { UsuariosComponent } from './usuarios/usuarios.component';
import { HomeComponent } from './components/home/home.component';
import { authGuard } from './guards/auth.guard';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LoginComponent } from './components/login/login.component';
import { InventarioInfoComponent } from './inventario/inventario-info/inventario-info.component';
import { CargaDatosComponent } from './components/carga-datos/carga-datos.component';
import { MenuPrincipalComponent } from './components/menu-principal/menu-principal.component';
import { ListadoComponent } from './pacientes/listado/listado.component';
import { PacientesInfoComponent } from './pacientes/pacientes-info/pacientes-info.component';
import { FraccionamientoComponent } from './components/fraccionamiento/fraccionamiento.component';
import {DispersarMedicamentoComponent} from "./components/dispersar-medicamento/dispersar-medicamento.component";
import { CajaComponent } from './components/caja/caja.component';
import { ComprasComponent } from './components/compras/compras.component';

const routes: Routes = [
  { path: '', component: MenuPrincipalComponent }, // CAMBIADO: ahora el inicio es público
  { path: 'login', component: LoginComponent },
  { path: 'ventas', component: VentasComponent, canActivate: [authGuard] },
  { path: 'usuarios', component: UsuariosComponent, canActivate: [authGuard] },
  { path: 'carga-datos', component: CargaDatosComponent, canActivate: [authGuard] },
  { path: 'inventario-info', component: InventarioInfoComponent, canActivate: [authGuard] },
  { path: 'inventario/upload', component: InventarioUploadComponent, canActivate: [authGuard] },
  { path: 'pacientes/upload', component: PacientesUploadComponent, canActivate: [authGuard] },
  { path: 'inventario/upload-precios', component: UploadPreciosComponent, canActivate: [authGuard] },
  { path: 'home', component: HomeComponent, canActivate: [authGuard] },
  { path: 'pacientes/info', component: PacientesInfoComponent, canActivate: [authGuard] },
  { path: 'pacientes', component: ListadoComponent, canActivate: [authGuard] },
  { path: 'dispersar', component: DispersarMedicamentoComponent },
  { path: 'fraccionamiento', component: FraccionamientoComponent, canActivate: [authGuard] },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'pacientes/listado', component: ListadoComponent, canActivate: [authGuard] },
  {path: 'caja', component: CajaComponent, canActivate: [authGuard]},
  {path: 'compras', component: ComprasComponent, canActivate: [authGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
