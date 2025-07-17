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

  ngOnInit(): void {
    this.pacientesService.obtenerTodos().subscribe({
      next: (data) => this.pacientes = data,
      error: (err) => console.error('Error al cargar pacientes:', err)
    });
  }
}
