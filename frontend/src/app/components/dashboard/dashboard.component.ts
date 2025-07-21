import { Component } from '@angular/core';
import { Router } from '@angular/router';

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
      descripcion: 'Total de unidades de medicamentos en bodega (fraccionado + sin fraccionar)',
      ruta: '/inventario-info'
    },
    {
      icon: '⚠️',
      titulo: 'Productos por vencer',
      descripcion: 'Número de medicamentos con vencimiento en los próximos 30/60 días',
      ruta: '' // no implementado aún
    },
    {
      icon: '📦',
      titulo: 'Fraccionamientos hoy',
      descripcion: 'Cuántos fraccionamientos se realizaron en el día actual',
      ruta: '/fraccionamiento'
    },
    {
      icon: '💉',
      titulo: 'Dispersión de medicamentos',
      descripcion: 'Formulario para entregar medicamentos a pacientes',
      ruta: '/dispersar'
    },
    {
      icon: '🪙',
      titulo: 'Ventas / Copagos del día',
      descripcion: 'Total de ingresos por medicamentos no gratuitos',
      ruta: '/ventas'
    },
    {
      icon: '🧍‍♂️',
      titulo: 'Pacientes atendidos hoy',
      descripcion: 'Número de pacientes distintos atendidos en el día',
      ruta: '/pacientes/info'
    }
  ];

  constructor(private router: Router) {}
  irAFraccionamiento() {
    this.router.navigate(['/fraccionamiento']);
  }
  irAKPI(kpi: any) {
    if (kpi.ruta) {
      this.router.navigate([kpi.ruta]);
    } else {
      alert(`KPI aún no disponible: ${kpi.titulo}`);
    }
  }
}
