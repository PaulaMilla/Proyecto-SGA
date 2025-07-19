import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HttpClientModule } from '@angular/common/http';
import { VentasComponent } from './ventas/ventas.component';
import { HeaderComponent } from './shared/header/header.component';
import { FooterComponent } from './shared/footer/footer.component';
import { UploadComponent as InventarioUploadComponent } from './inventario/upload/upload.component';
import { UploadComponent as PacientesUploadComponent } from './pacientes/upload/upload.component';

import { HomeComponent } from './components/home/home.component';
import { UsuariosComponent } from './usuarios/usuarios.component';
import { CargaDatosComponent } from './components/carga-datos/carga-datos.component';
import { LoginComponent } from './components/login/login.component';
import { InventarioInfoComponent } from './inventario/inventario-info/inventario-info.component';
import { MenuPrincipalComponent } from './components/menu-principal/menu-principal.component';
import { PublicHeaderComponent } from './public/public-header/public-header.component';
import { PublicFooterComponent } from './public/public-footer/public-footer.component';
import { ListadoComponent } from './pacientes/listado/listado.component';
import { PacientesInfoComponent } from './pacientes/pacientes-info/pacientes-info.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { FraccionamientoComponent } from './components/fraccionamiento/fraccionamiento.component';
import { InformesComponent } from './components/informes/informes.component';



@NgModule({
  declarations: [
    AppComponent,
    VentasComponent,
    HeaderComponent,
    FooterComponent,
    InventarioUploadComponent,
    PacientesUploadComponent,
    HomeComponent,
    UsuariosComponent,
    CargaDatosComponent,
    LoginComponent,
    InventarioInfoComponent,
    MenuPrincipalComponent,
    PublicHeaderComponent,
    PublicFooterComponent,
    ListadoComponent,
    PacientesInfoComponent,
    DashboardComponent,
    FraccionamientoComponent,
    InformesComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
