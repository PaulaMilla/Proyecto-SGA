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
import { DispersarMedicamentoComponent } from './components/dispersar-medicamento/dispersar-medicamento.component';
import { UploadPreciosComponent } from './inventario/upload-precios/upload-precios.component';
import { ComprasComponent } from './components/compras/compras.component';
import { CajaComponent } from './components/caja/caja.component';
import { SeleccionarCajaComponent } from './components/caja/seleccionar-caja/seleccionar-caja.component';
import { EstadoCajaComponent } from './components/caja/estado-caja/estado-caja.component';
import { RegistrarPagoComponent } from './components/caja/registrar-pago/registrar-pago.component';
import { RegistrarMovimientoComponent } from './components/caja/registrar-movimiento/registrar-movimiento.component';
import { ListadoMovimientoComponent } from './components/caja/listado-movimiento/listado-movimiento.component';
import { CargaDocumentosCompraComponent } from './components/carga-documentos-compra/carga-documentos-compra.component';




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
    InformesComponent,
    DispersarMedicamentoComponent,
    UploadPreciosComponent,
    ComprasComponent,
    CajaComponent,
    SeleccionarCajaComponent,
    EstadoCajaComponent,
    RegistrarPagoComponent,
    RegistrarMovimientoComponent,
    ListadoMovimientoComponent,
    CargaDocumentosCompraComponent


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
