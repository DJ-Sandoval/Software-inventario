package com.software.api_Inventario.service.reports.PDF;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.software.api_Inventario.persistence.entities.Producto;
import com.software.api_Inventario.service.interfaces.ReporteProductosServicePdf;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ReporteProductosServiceImp implements ReporteProductosServicePdf {
    private static final String REPORT_DIR = "C:/reportesInventario/";
    @Override
    public void generarReporteProductos(List<Producto> productos) {
        Document document = new Document();
        try {
            String rutaReporte = REPORT_DIR + "reporte_productos.pdf";
            // Crear carpeta si no existe
            File carpeta = new File(REPORT_DIR);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            PdfWriter.getInstance(document, new FileOutputStream(rutaReporte));
            document.open();

            // Fuente general
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font fontFecha = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            Font fontTablaHeader = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
            Font fontTablaCuerpo = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

            // Cabecera con imagen + fecha + título
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new int[]{1, 3});

            // Imagen
            Image logo = Image.getInstance("src/main/resources/static/reporte.png");
            logo.scaleAbsolute(80, 80);
            PdfPCell imageCell = new PdfPCell(logo, false);
            imageCell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(imageCell);

            // Fecha + título
            PdfPCell textCell = new PdfPCell();
            textCell.setBorder(Rectangle.NO_BORDER);
            textCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
            Paragraph fechaP = new Paragraph("Fecha: " + fecha, fontFecha);
            Paragraph titulo = new Paragraph("Lista de Productos", fontTitulo);
            titulo.setSpacingBefore(10);
            textCell.addElement(fechaP);
            textCell.addElement(titulo);
            headerTable.addCell(textCell);

            document.add(headerTable);
            document.add(Chunk.NEWLINE);

            // Tabla de productos
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{2, 3, 3, 2, 2, 2, 2});

            // Encabezados
            String[] headers = {"Código", "Nombre", "Descripción", "P. Compra", "P. Venta", "Stock", "Stock Mínimo"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, fontTablaHeader));
                cell.setBackgroundColor(BaseColor.BLUE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                table.addCell(cell);
            }

            // Filas de datos
            for (Producto p : productos) {
                table.addCell(new PdfPCell(new Phrase(p.getCodigo(), fontTablaCuerpo)));
                table.addCell(new PdfPCell(new Phrase(p.getNombre(), fontTablaCuerpo)));
                table.addCell(new PdfPCell(new Phrase(p.getDescripcion(), fontTablaCuerpo)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getPrecioCompra()), fontTablaCuerpo)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getPrecioVenta()), fontTablaCuerpo)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getStock()), fontTablaCuerpo)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getStockMinimo()), fontTablaCuerpo)));
            }

            document.add(table);
            document.close();

            System.out.println("Reporte generado correctamente en: " + rutaReporte);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
