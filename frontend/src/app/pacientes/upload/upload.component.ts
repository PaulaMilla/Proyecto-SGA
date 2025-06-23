import { Component } from '@angular/core';
import {PacientesService} from "../services/pacientes.service";

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrl: './upload.component.scss'
})

export class UploadComponent {
  file: File | null = null;
  loading: boolean = false;
  message: string = '';
  messageType: 'success' | 'error' | 'info' = 'info';

  constructor(private pacientesService: PacientesService) {}

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
    this.pacientesService.subirArchivoPacientes(this.file)
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
    this.pacientesService.testUpload(this.file)
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

  private showMessage(message: string, type: 'success' | 'error' | 'info'): void {
    this.message = message;
    this.messageType = type;
  }

}
