import { Component } from '@angular/core';
import {InventarioService} from "../services/inventario.service";

@Component({
  selector: 'app-upload-precios',
  templateUrl: './upload-precios.component.html',
  styleUrl: './upload-precios.component.scss'
})
export class UploadPreciosComponent {
  file: File | null = null;
  productosInfo: string = '';
  loading: boolean = false;
  message: string = '';
  messageType: 'success' | 'error' | 'info' = 'info';

  constructor(private inventarioService: InventarioService) {}

  onFileSelected(event: any): void {
    this.file = event.target.files[0];
    this.message = `Archivo seleccionado: ${this.file?.name}`;
    this.messageType = 'info';
  }

  onUpload(): void {
    if (!this.file) {
      this.showMessage('Por favor selecciona un archivo', 'error');
      return;
    }

    this.loading = true;

    const formData = new FormData();
    formData.append('file', this.file, this.file.name);
    formData.append('emailUsuario', localStorage.getItem('email') || '')

    this.inventarioService.subirArchivoInventario(formData)
      .subscribe({
        next: (res) => {
          // Si res tiene una propiedad 'message', muéstrala
          const msg = res && res.message ? res.message : JSON.stringify(res);
          this.showMessage('Archivo cargado con éxito: ' + msg, 'success');
          this.loading = false;
        },
        error: (err) => {
          console.error('Error al subir archivo:', err);
          this.showMessage('Error al subir archivo: ' + (err.error?.message || err.error || err.message || 'Error desconocido'), 'error');
          this.loading = false;
        }
      });
  }

  onTestUpload(): void {
    if (!this.file) {
      this.showMessage('Por favor selecciona un archivo', 'error');
      return;
    }

    this.loading = true;
    this.inventarioService.testUpload(this.file)
      .subscribe({
        next: (res) => {
          const msg = res && res.message ? res.message : JSON.stringify(res);
          this.showMessage('Prueba exitosa: ' + msg, 'success');
          this.loading = false;
        },
        error: (err) => {
          console.error('Error en prueba:', err);
          this.showMessage('Error en prueba: ' + (err.error?.message || err.error || err.message || 'Error desconocido'), 'error');
          this.loading = false;
        }
      });
  }

  getProductosInfo(): void {
    this.loading = true;
    this.inventarioService.getProductosInfo()
      .subscribe({
        next: (res) => {
          this.productosInfo = res;
          this.showMessage('Información de productos obtenida', 'success');
          this.loading = false;
        },
        error: (err) => {
          console.error('Error al obtener productos:', err);
          this.showMessage('Error al obtener productos: ' + (err.error || err.message || 'Error desconocido'), 'error');
          this.loading = false;
        }
      });
  }

  createTestProducts(): void {
    this.loading = true;
    this.inventarioService.createTestProducts()
      .subscribe({
        next: (res) => {
          this.showMessage('Productos de prueba creados: ' + res, 'success');
          this.loading = false;
        },
        error: (err) => {
          console.error('Error al crear productos:', err);
          this.showMessage('Error al crear productos: ' + (err.error || err.message || 'Error desconocido'), 'error');
          this.loading = false;
        }
      });
  }

  private showMessage(message: string, type: 'success' | 'error' | 'info'): void {
    this.message = message;
    this.messageType = type;
  }

}
