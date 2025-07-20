import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-dispersar-medicamento',
  templateUrl: './dispersar-medicamento.component.html',
  styleUrls: ['./dispersar-medicamento.component.scss']
})
export class DispersarMedicamentoComponent implements OnInit {
  form: FormGroup;
  inventario: any[] = [];
  productos: any[] = [];
  dispensaciones: any[] = []
  stockDisponible: number | null = null;
  cantidadInvalida: boolean = false;

  mensaje: string = '';
  error: string = '';

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.form = this.fb.group({
      idProducto: ['', Validators.required],
      cantidad: ['', [Validators.required, Validators.min(1)]],
      rutPaciente: ['', Validators.required],
      nombrePaciente: ['', Validators.required],
      emailUsuario: ['', [Validators.required, Validators.email]]
    });
  }

  ngOnInit(): void {
    this.cargarInventario();
    this.cargarProductos();
    this.cargarDispensaciones();


    this.form.get('idProducto')?.valueChanges.subscribe(idProducto => {
      const productoInventario = this.inventario.find(item => item.producto.id == idProducto);
      this.stockDisponible = productoInventario ? productoInventario.cantidad_disponible : null;
      this.verificarCantidad();
    });

    this.form.get('cantidad')?.valueChanges.subscribe(() => {
      this.verificarCantidad();
    });
  }

  verificarCantidad() {
    const cantidad = this.form.get('cantidad')?.value;
    if (this.stockDisponible !== null && cantidad > this.stockDisponible) {
      this.cantidadInvalida = true;
    } else {
      this.cantidadInvalida = false;
    }
  }

  cargarDispensaciones() {
    this.http.get<any[]>('http://34.61.182.228/api/inventario/dispensaciones')
      .subscribe(data => {
        this.dispensaciones = data;
      }, error => {
        console.error('Error al obtener dispensaciones:', error);
      });
  }


  cargarInventario() {
    this.http.get<any[]>('http://34.61.182.228/api/inventario/listar-con-producto')
      .subscribe(data => {
        this.inventario = data;
      }, error => {
        console.error('Error al obtener inventario:', error);
      });
  }

  cargarProductos() {
    this.http.get<any[]>('http://34.61.182.228/api/productos')
      .subscribe(data => {
        this.productos = data;
      }, error => {
        console.error('Error al obtener productos:', error);
      });
  }

  dispersar() {
    if (this.form.invalid || this.cantidadInvalida) return;

    const formData = this.form.value;

    this.http.post('http://34.61.182.228/api/inventario/dispersar', {
      idProducto: formData.idProducto,
      cantidad: formData.cantidad,
      rutPaciente: formData.rutPaciente,
      nombrePaciente: formData.nombrePaciente,
      emailUsuario: formData.emailUsuario
    }).subscribe(() => {
      this.mensaje = 'Medicamento dispersado correctamente';
      this.error = '';
      this.form.reset();
      this.stockDisponible = null;
      this.cantidadInvalida = false;
      this.cargarInventario();
    }, error => {
      console.error('Error al dispersar:', error);
      this.error = 'No se pudo dispersar el medicamento';
      this.mensaje = '';
    });
  }
}
