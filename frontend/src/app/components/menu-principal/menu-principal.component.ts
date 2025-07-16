import { Component, OnInit } from '@angular/core';
import { InventarioService } from '../../inventario/services/inventario.service';



@Component({
  selector: 'app-menu-principal',
  templateUrl: './menu-principal.component.html',
  styleUrls: ['./menu-principal.component.scss']
})
export class MenuPrincipalComponent implements OnInit {

  productos: any[] = [];

  constructor(private inventarioService: InventarioService) {}

  ngOnInit(): void {
    this.inventarioService.getProductosPublicos().subscribe({
      next: (data) => this.productos = data,
      error: (err) => console.error('Error al cargar productos públicos:', err)
    });
  }
}
