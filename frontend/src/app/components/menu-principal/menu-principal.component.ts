import { Component, OnInit } from '@angular/core';
import { InventarioService } from '../../inventario/services/inventario.service';
import { InventarioProducto } from '../../inventario/model/inventarioproducto.model';

@Component({
  selector: 'app-menu-principal',
  templateUrl: './menu-principal.component.html',
  styleUrls: ['./menu-principal.component.scss']
})
export class MenuPrincipalComponent implements OnInit {
  productos: InventarioProducto[] = [];

  constructor(private inventarioService: InventarioService) {}

  ngOnInit(): void {
    const email = 'publico@farmacia.cl';
    this.inventarioService.getInventarioConProducto(email).subscribe(
      (data: InventarioProducto[]) => this.productos = data,
      (error: any) => console.error('Error al obtener productos:', error)
    );
  }
}
