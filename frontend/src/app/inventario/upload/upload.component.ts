import { Component } from '@angular/core';
import { InventarioService } from '../services/inventario.service';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html'
})
export class UploadComponent {
  file: File | null = null;

  constructor(private inventarioService: InventarioService) {}

  onFileSelected(event: any): void {
    this.file = event.target.files[0];
  }

  onUpload(): void {
    if (this.file) {
      this.inventarioService.subirArchivoInventario(this.file)
        .subscribe({
          next: res => alert('Archivo cargado con éxito'),
          error: err => alert('Error al subir archivo')
        });
    }
  }
}

