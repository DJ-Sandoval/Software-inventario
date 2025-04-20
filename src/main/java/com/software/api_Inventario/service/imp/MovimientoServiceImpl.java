package com.software.api_Inventario.service.imp;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.software.api_Inventario.persistence.entities.Entrada;
import com.software.api_Inventario.persistence.entities.Producto;
import com.software.api_Inventario.persistence.entities.Salida;
import com.software.api_Inventario.persistence.repository.EntradaRepository;
import com.software.api_Inventario.persistence.repository.ProductoRepository;
import com.software.api_Inventario.persistence.repository.SalidaRepository;
import com.software.api_Inventario.service.interfaces.IMovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements IMovimientoService {
    private static final String REPORT_DIR = "C:/reportes/";
    private static final String IMAGE_PATH = "src/main/resources/static/finanzas.png";
    private final EntradaRepository entradaRepository;
    private final SalidaRepository salidaRepository;
    private final ProductoRepository productoRepository;

    @Override
    public String generarReporteMovimientosPorProducto(Long idProducto, LocalDateTime desde, LocalDateTime hasta) throws Exception {
        Files.createDirectories(Paths.get(REPORT_DIR));

        // Generar nombre de archivo único
        String fileName = "ReporteMovimientos_" + idProducto + "_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf";
        String fullPath = REPORT_DIR + fileName;

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        List<Entrada> entradas = entradaRepository.findByProductoIdAndFechaEntradaBetween(idProducto, desde, hasta);
        List<Salida> salidas = salidaRepository.findByProductoIdAndFechaSalidaBetween(idProducto, desde, hasta);

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fullPath));
        document.open();

        // Agregar logo
        addLogo(document);

        // Agregar título
        addTitle(document, producto, desde, hasta);

        // Agregar sección de entradas
        addEntradasSection(document, entradas);

        // Agregar sección de salidas
        addSalidasSection(document, salidas);

        // Agregar footer
        addFooter(document);

        document.close();

        return fullPath;
    }

    private void addLogo(Document document) throws DocumentException, IOException {
        Image logo = Image.getInstance(IMAGE_PATH);
        logo.scaleToFit(100, 100);
        logo.setAbsolutePosition(50, 750);
        document.add(logo);
    }

    private void addTitle(Document document, Producto producto, LocalDateTime desde, LocalDateTime hasta)
            throws DocumentException {
        Paragraph title = new Paragraph("Reporte de Movimientos",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.DARK_GRAY));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        Paragraph productInfo = new Paragraph("Producto: " + producto.getNombre(),
                FontFactory.getFont(FontFactory.HELVETICA, 14));
        productInfo.setSpacingAfter(10);
        document.add(productInfo);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        Paragraph period = new Paragraph("Período: " + desde.format(formatter) + " a " + hasta.format(formatter),
                FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.GRAY));
        period.setSpacingAfter(20);
        document.add(period);
    }

    private void addEntradasSection(Document document, List<Entrada> entradas) throws DocumentException {
        Paragraph sectionTitle = new Paragraph("Entradas:",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLUE));
        sectionTitle.setSpacingAfter(10);
        document.add(sectionTitle);

        if(entradas.isEmpty()) {
            document.add(new Paragraph("No hay entradas en este período",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, BaseColor.GRAY)));
        } else {
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(80);
            table.setSpacingBefore(10);
            table.setSpacingAfter(20);

            // Encabezados de tabla
            addTableHeader(table, "Fecha", "Cantidad");

            // Filas de datos
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            for(Entrada entrada : entradas) {
                table.addCell(entrada.getFechaEntrada().format(formatter));
                table.addCell(String.valueOf(entrada.getCantidad()));
            }

            document.add(table);
        }
    }

    private void addSalidasSection(Document document, List<Salida> salidas) throws DocumentException {
        Paragraph sectionTitle = new Paragraph("Salidas:",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.RED));
        sectionTitle.setSpacingAfter(10);
        document.add(sectionTitle);

        if(salidas.isEmpty()) {
            document.add(new Paragraph("No hay salidas en este período",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, BaseColor.GRAY)));
        } else {
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(80);
            table.setSpacingBefore(10);

            // Encabezados de tabla
            addTableHeader(table, "Fecha", "Cantidad");

            // Filas de datos
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            for(Salida salida : salidas) {
                table.addCell(salida.getFechaSalida().format(formatter));
                table.addCell(String.valueOf(salida.getCantidad()));
            }

            document.add(table);
        }
    }

    private void addTableHeader(PdfPTable table, String... headers) {
        Stream.of(headers)
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addFooter(Document document) throws DocumentException {
        Paragraph footer = new Paragraph("Generado el: " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.GRAY));
        footer.setAlignment(Element.ALIGN_RIGHT);
        footer.setSpacingBefore(30);
        document.add(footer);
    }
}
