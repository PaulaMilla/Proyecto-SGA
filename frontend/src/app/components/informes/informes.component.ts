import { Component } from '@angular/core';
import {VentaConNombresYDetalles} from "../../ventas/model/venta-con-detalles-view.dto";
import FileSaver from "file-saver";
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-informes',
  templateUrl: './informes.component.html',
  styleUrl: './informes.component.scss'
})
export class InformesComponent {

  ventas: VentaConNombresYDetalles[] = [];

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

