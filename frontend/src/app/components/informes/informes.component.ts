import {Component, OnInit} from '@angular/core';
import {DetalleVentaConNombre, VentaConNombresYDetalles} from "../../ventas/model/venta-con-detalles-view.dto";
import FileSaver from "file-saver";
import * as XLSX from 'xlsx';
import {VentasService} from "../../ventas/services/ventas.service";

interface ProductoResumen {
  productoId: number;
  nombreProducto: string;
  precioUnitario: number;
  cantidadVendida: number;
}

@Component({
  selector: 'app-informes',
  templateUrl: './informes.component.html',
  styleUrl: './informes.component.scss'
})

export class InformesComponent implements OnInit{

  ventas: VentaConNombresYDetalles[] = [];

  constructor(private ventasService: VentasService) {}

  ngOnInit(): void {
    this.cargarVentas(); // También podrías mover esto al botón si prefieres
  }

  exportarProductosMasVendidos(): void {
    this.ventasService.obtenerTodas().subscribe((ventas: VentaConNombresYDetalles[]) => {
      const resumen: { [id: number]: ProductoResumen } = {};

      for (const venta of ventas) {
        if (!Array.isArray(venta.detalles)) continue;
        for (const detalle of venta.detalles) {
          const id = detalle.productoId;
          if (!resumen[id]) {
            resumen[id] = {
              productoId: id,
              nombreProducto: detalle.nombreProducto,
              precioUnitario: detalle.precioUnitario,
              cantidadVendida: 0
            };
          }
          resumen[id].cantidadVendida += detalle.cantidad;
        }
      }

      // Convertir a array y ordenar de mayor a menor
      const productosOrdenados = Object.values(resumen).sort(
        (a, b) => b.cantidadVendida - a.cantidadVendida
      );

      // Exportar a Excel
      const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(productosOrdenados);
      const wb: XLSX.WorkBook = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb, ws, 'Mayor Movimiento');
      XLSX.writeFile(wb, 'productos_mas_vendidos.xlsx');
    });
  }

  cargarVentas(): void {
    this.ventasService.obtenerTodas().subscribe((ventas: any[]) => {
      const ventasConTodo: VentaConNombresYDetalles[] = [];

      ventas.forEach((v) => {
        const ventaExtendida: VentaConNombresYDetalles = {
          id: v.id,
          pacienteId: v.pacienteId,
          idUsuarioVendedor: v.idUsuarioVendedor,
          fechaVenta: v.fechaVenta,
          total: v.total,
          nombrePaciente: '',
          nombreUsuario: '',
          apellidoUsuario: '',
          detalles: []
        };
        // Obtener nombres por ID
        this.ventasService.getPacientePorId(v.pacienteId).subscribe((p) => {
          ventaExtendida.nombrePaciente = p.nombre;
        });

        this.ventasService.getUsuarioPorId(v.idUsuarioVendedor).subscribe((u) => {
          ventaExtendida.nombreUsuario = u.nombre;
          ventaExtendida.apellidoUsuario = u.apellido;
        });

        // Obtener detalles de la venta
        this.ventasService.getDetallesPorVentaId(v.id).subscribe((detalles: any[]) => {
          detalles.forEach((d) => {
            const detalleExtendido: DetalleVentaConNombre = {
              productoId: d.productoId,
              cantidad: d.cantidad,
              precioUnitario: d.precioUnitario,
              subtotal: d.subtotal,
              nombreProducto: ''
            };

            this.ventasService.getProductoPorId(d.productoId).subscribe((prod) => {
              detalleExtendido.nombreProducto = prod.nombre;
            });

            ventaExtendida.detalles.push(detalleExtendido);
          });
        });

        ventasConTodo.push(ventaExtendida);
      });

      this.ventas = ventasConTodo;
    });
  }

  exportarVentasExcel(): void {
    const dataParaExcel: any[] = [];

    this.ventas.forEach(v => {
      v.detalles.forEach(d => {
        dataParaExcel.push({
          'ID Venta': v.id,
          'Fecha Venta': v.fechaVenta,
          'Paciente': v.nombrePaciente,
          'Vendedor': `${v.nombreUsuario} ${v.apellidoUsuario}`,
          'Producto': d.nombreProducto,
          'Cantidad': d.cantidad,
          'Precio Unitario': d.precioUnitario,
          'Subtotal': d.subtotal,
          'Total Venta': v.total
        });
      });
    });

    const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(dataParaExcel);
    const workbook: XLSX.WorkBook = { Sheets: { 'Ventas': worksheet }, SheetNames: ['Ventas'] };

    const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
    const blob: Blob = new Blob([excelBuffer], { type: 'application/octet-stream' });
    FileSaver.saveAs(blob, 'ventas_historicas.xlsx');
  }
}

