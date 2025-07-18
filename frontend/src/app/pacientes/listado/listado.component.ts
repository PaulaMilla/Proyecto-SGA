import { Component, OnInit } from '@angular/core';
import { PacientesService } from '../services/pacientes.service';
import * as XLSX from 'xlsx';
import * as FileSaver from 'file-saver';


@Component({
  selector: 'app-listado',
  templateUrl: './listado.component.html',
  styleUrls: ['./listado.component.scss']
})
export class ListadoComponent implements OnInit {

  pacientes: any[] = [];

  constructor(private pacientesService: PacientesService) {}

  exportarAExcel(): void {
    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(this.pacientes);
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Pacientes');

    const excelBuffer: any = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
    const blobData: Blob = new Blob([excelBuffer], { type: 'application/octet-stream' });
    FileSaver.saveAs(blobData, 'pacientes.xlsx');
  }

  eliminar(id: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar este paciente?')) {
      this.pacientesService.eliminarPaciente(id).subscribe({
        next: () => {
          // Quitar el paciente eliminado del array
          this.pacientes = this.pacientes.filter(p => p.id_paciente !== id);
        },
        error: err => console.error('Error al eliminar paciente:', err)
      });
    }
  }

  ngOnInit(): void {
    this.pacientesService.obtenerTodos().subscribe({
      next: (data) => this.pacientes = data,
      error: (err) => console.error('Error al cargar pacientes:', err)
    });

  }

}
