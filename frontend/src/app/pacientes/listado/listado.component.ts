import { Component, OnInit } from '@angular/core';
import { PacientesService } from '../services/pacientes.service';

@Component({
  selector: 'app-listado',
  templateUrl: './listado.component.html',
  styleUrls: ['./listado.component.scss']
})
export class ListadoComponent implements OnInit {

  pacientes: any[] = [];

  constructor(private pacientesService: PacientesService) {}

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
