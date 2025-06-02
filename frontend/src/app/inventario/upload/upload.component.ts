import { Component } from '@angular/core';
import { InventarioService } from '../services/inventario.service';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html'
})
export class UploadComponent {
  selectedFile: File | null = null;

  constructor(private inventarioService: InventarioService) {}

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  onUpload(): void {
    if (this.selectedFile) {
      this.inventarioService.subirArchivoInventario(this.selectedFile)
        .subscribe({
          next: res => alert('Archivo cargado con éxito'),
          error: err => alert('Error al subir archivo')
        });
    }
  }
}

