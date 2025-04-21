package com.software.api_Inventario.service.reports.EXCELL;

import com.software.api_Inventario.persistence.entities.Producto;
import com.software.api_Inventario.service.interfaces.ReporteProductosExcell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class ReporteServiceImpExcell implements ReporteProductosExcell {
    private static final String REPORT_DIR = "C:/reportesExcell/";

    @Override
    public void generarReporteProductosExcel(List<Producto> productos) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Productos");

            // Crear carpeta si no existe
            File carpeta = new File(REPORT_DIR);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            // Estilo para el título
            CellStyle tituloStyle = workbook.createCellStyle();
            Font fontTitulo = workbook.createFont();
            fontTitulo.setFontHeightInPoints((short) 18);
            fontTitulo.setBold(true);
            tituloStyle.setFont(fontTitulo);

            // Título
            Row tituloRow = sheet.createRow(0);
            Cell tituloCell = tituloRow.createCell(0);
            tituloCell.setCellValue("Lista de Productos");
            tituloCell.setCellStyle(tituloStyle);
            // Combinar celdas para el título
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

            // Estilo para los encabezados (azul clarito)
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font fontHeader = workbook.createFont();
            fontHeader.setBold(true);
            fontHeader.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(fontHeader);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            // Encabezados
            String[] headers = {"Código", "Nombre", "Descripción", "P. Compra", "P. Venta", "Stock", "Stock Mínimo"};
            Row headerRow = sheet.createRow(2);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Datos
            int rowNum = 3;
            for (Producto p : productos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(p.getCodigo());
                row.createCell(1).setCellValue(p.getNombre());
                row.createCell(2).setCellValue(p.getDescripcion());
                row.createCell(3).setCellValue(p.getPrecioCompra());
                row.createCell(4).setCellValue(p.getPrecioVenta());
                row.createCell(5).setCellValue(p.getStock());
                row.createCell(6).setCellValue(p.getStockMinimo());
            }

            // Ajustar ancho de columnas
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Guardar archivo
            String rutaReporte = REPORT_DIR + "reporte_productos.xlsx";
            try (FileOutputStream fileOut = new FileOutputStream(rutaReporte)) {
                workbook.write(fileOut);
            }

            System.out.println("Reporte Excel generado correctamente en: " + rutaReporte);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
