import { Component, OnInit } from '@angular/core';
import { PacientesService } from '../services/pacientes.service';

@Component({
  selector: 'app-pacientes-info',
  templateUrl: './pacientes-info.component.html',
  styleUrls: ['./pacientes-info.component.scss']
})
export class PacientesInfoComponent implements OnInit {

  pacientes: any[] = [];

  constructor(private pacientesService: PacientesService) {}

  ngOnInit(): void {
    this.pacientesService.obtenerPacientes().subscribe({
      next: (data) => this.pacientes = data,
      error: (err) => console.error('Error al cargar pacientes:', err)
    });
  }
}
