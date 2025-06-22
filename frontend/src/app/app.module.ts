import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HttpClientModule } from '@angular/common/http';
import { VentasComponent } from './ventas/ventas.component';
import { HeaderComponent } from './shared/header/header.component';
import { FooterComponent } from './shared/footer/footer.component';
import { UploadComponent } from './inventario/upload/upload.component';

import { HomeComponent } from './components/home/home.component';
import { UsuariosComponent } from './usuarios/usuarios.component';
import { CargaDatosComponent } from './components/carga-datos/carga-datos.component';
import { LoginComponent } from './components/login/login.component';
import { InventarioInfoComponent } from './inventario/inventario-info/inventario-info.component';

@NgModule({
  declarations: [
    AppComponent,
    VentasComponent,
    HeaderComponent,
    FooterComponent,
    UploadComponent,
    HomeComponent,
    UsuariosComponent,
    CargaDatosComponent,
    LoginComponent,
    InventarioInfoComponent
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
