import { Component } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  kpis = [
    {
      icon: '💊',
      titulo: 'Stock total disponible',
      descripcion: 'Total de unidades de medicamentos en bodega (fraccionado + sin fraccionar)'
    },
    {
      icon: '⚠️',
      titulo: 'Productos por vencer',
      descripcion: 'Número de medicamentos con vencimiento en los próximos 30/60 días'
    },
    {
      icon: '📦',
      titulo: 'Fraccionamientos hoy',
      descripcion: 'Cuántos fraccionamientos se realizaron en el día actual'
    },
    {
      icon: '🧾',
      titulo: 'Dispensaciones hoy',
      descripcion: 'Cuántas recetas se atendieron hoy'
    },
    {
      icon: '🪙',
      titulo: 'Ventas / Copagos del día',
      descripcion: 'Total de ingresos por medicamentos no gratuitos'
    },
    {
      icon: '🧍‍♂️',
      titulo: 'Pacientes atendidos hoy',
      descripcion: 'Número de pacientes distintos atendidos en el día'
    }
  ];
}
