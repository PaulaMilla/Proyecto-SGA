import { Component } from '@angular/core';
import { ComprasService } from '../compras/compras.service';

@Component({
  selector: 'app-carga-documentos-compra',
  templateUrl: './carga-documentos-compra.component.html',
  styleUrl: './carga-documentos-compra.component.scss'
})
export class CargaDocumentosCompraComponent {
  compras: any[] = [];
  selectedCompraId: number | null = null;
  selectedFile: File | null = null;

  constructor(private comprasService: ComprasService) {}

  ngOnInit(): void {
    this.cargarCompras();
  }

  cargarCompras(): void {
    this.comprasService.getCompras().subscribe(data => this.compras = data);
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  subirDocumento(): void {
    if (!this.selectedCompraId || !this.selectedFile) {
      alert('Debe seleccionar una compra y un archivo.');
      return;
    }

    this.comprasService.subirDocumento(this.selectedCompraId, this.selectedFile).subscribe({
      next: () => {
        alert('Documento cargado exitosamente');
        this.selectedFile = null;
      },
      error: () => {
        alert('Error al subir el archivo');
      }
    });
  }
}
