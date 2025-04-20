package com.software.api_Inventario.service.reports;

import com.software.api_Inventario.persistence.entities.Entrada;
import com.software.api_Inventario.persistence.entities.Producto;
import com.software.api_Inventario.persistence.entities.Salida;
import com.software.api_Inventario.persistence.repository.EntradaRepository;
import com.software.api_Inventario.persistence.repository.ProductoRepository;
import com.software.api_Inventario.persistence.repository.SalidaRepository;
import com.software.api_Inventario.service.interfaces.IReporteFinancieroService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;


@Service
public class ReporteFinancieroServiceImp implements IReporteFinancieroService {
    private static final String REPORT_DIR = "C:/financiero/";

    @Autowired
    private EntradaRepository entradaRepository;
    @Autowired
    private SalidaRepository salidaRepository;

    @Override
    public String generarReporteCostosProfit(LocalDateTime desde, LocalDateTime hasta) throws Exception {
        // Crear directorio si no existe
        Files.createDirectories(Paths.get(REPORT_DIR));

        // Generar nombre de archivo único
        String fileName = "ReporteFinanciero_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx";
        String fullPath = REPORT_DIR + fileName;

        List<Entrada> entradas = entradaRepository.findByFechaEntradaBetween(desde, hasta);
        List<Salida> salidas = salidaRepository.findByFechaSalidaBetween(desde, hasta);

        try (Workbook workbook = new XSSFWorkbook()) {
            // Hoja de resumen
            Sheet summarySheet = workbook.createSheet("Resumen Financiero");
            createSummarySheet(summarySheet, entradas, salidas, desde, hasta);

            // Hoja de detalle de compras
            Sheet comprasSheet = workbook.createSheet("Detalle Compras");
            createComprasSheet(comprasSheet, entradas);

            // Hoja de detalle de ventas
            Sheet ventasSheet = workbook.createSheet("Detalle Ventas");
            createVentasSheet(ventasSheet, salidas);

            // Escribir el archivo
            try (FileOutputStream fileOut = new FileOutputStream(fullPath)) {
                workbook.write(fileOut);
            }
        }

        return fullPath;
    }

    private void createSummarySheet(Sheet sheet, List<Entrada> entradas, List<Salida> salidas,
                                    LocalDateTime desde, LocalDateTime hasta) {
        // Estilos
        CellStyle headerStyle = createHeaderStyle(sheet.getWorkbook());
        CellStyle currencyStyle = createCurrencyStyle(sheet.getWorkbook());
        CellStyle titleStyle = createTitleStyle(sheet.getWorkbook());

        // Configurar anchos de columnas
        sheet.setColumnWidth(0, 5000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 5000);

        // Título del reporte
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Reporte Financiero - Costos y Profit");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));

        // Período
        Row periodRow = sheet.createRow(1);
        periodRow.createCell(0).setCellValue("Período:");
        periodRow.createCell(1).setCellValue(desde.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                " - " +
                hasta.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));

        // Encabezados
        Row headerRow = sheet.createRow(3);
        headerRow.createCell(0).setCellValue("Concepto");
        headerRow.createCell(1).setCellValue("Monto");
        headerRow.createCell(2).setCellValue("Notas");

        for (int i = 0; i < 3; i++) {
            headerRow.getCell(i).setCellStyle(headerStyle);
        }

        // Datos
        double totalCompras = entradas.stream()
                .mapToDouble(e -> e.getCantidad() * e.getPrecioUnitario())
                .sum();

        double totalVentas = salidas.stream()
                .filter(s -> "VENTA".equals(s.getTipoSalida()))
                .mapToDouble(s -> s.getCantidad() * s.getPrecioUnitario())
                .sum();

        double totalMermas = salidas.stream()
                .filter(s -> "MERMA".equals(s.getTipoSalida()))
                .mapToDouble(s -> s.getCantidad() * s.getPrecioUnitario())
                .sum();

        double profit = totalVentas - totalCompras;
        double margen = totalCompras > 0 ? (profit / totalCompras) * 100 : 0;

        int rowNum = 4;
        rowNum = addSummaryRow(sheet, rowNum, "Total Compras", totalCompras, currencyStyle);
        rowNum = addSummaryRow(sheet, rowNum, "Total Ventas", totalVentas, currencyStyle);
        rowNum = addSummaryRow(sheet, rowNum, "Total Mermas", totalMermas, currencyStyle);
        rowNum = addSummaryRow(sheet, rowNum, "Profit", profit, currencyStyle);
        rowNum = addSummaryRow(sheet, rowNum, "Margen (%)", margen, currencyStyle);

        createChart(sheet, 4, rowNum -1);
        // Fecha generación
        Row footerRow = sheet.createRow(rowNum + 1);
        footerRow.createCell(0).setCellValue("Generado el: " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        sheet.addMergedRegion(new CellRangeAddress(rowNum + 1, rowNum + 1, 0, 2));
    }

    private void createComprasSheet(Sheet sheet, List<Entrada> entradas) {
        // Estilos
        CellStyle headerStyle = createHeaderStyle(sheet.getWorkbook());
        CellStyle currencyStyle = createCurrencyStyle(sheet.getWorkbook());
        CellStyle dateStyle = createDateStyle(sheet.getWorkbook());

        // Configurar anchos de columnas
        sheet.setColumnWidth(0, 4000);  // Fecha
        sheet.setColumnWidth(1, 6000);  // Producto
        sheet.setColumnWidth(2, 3000);  // Cantidad
        sheet.setColumnWidth(3, 4000);  // Precio Unitario
        sheet.setColumnWidth(4, 4000);  // Total

        // Encabezados
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Fecha", "Producto", "Cantidad", "Precio Unitario", "Total"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Datos
        int rowNum = 1;
        for (Entrada entrada : entradas) {
            Row row = sheet.createRow(rowNum++);

            // Fecha
            Cell fechaCell = row.createCell(0);
            fechaCell.setCellValue(entrada.getFechaEntrada());
            fechaCell.setCellStyle(dateStyle);

            // Producto
            row.createCell(1).setCellValue(entrada.getProducto().getNombre());

            // Cantidad
            row.createCell(2).setCellValue(entrada.getCantidad());

            // Precio Unitario
            Cell precioCell = row.createCell(3);
            precioCell.setCellValue(entrada.getPrecioUnitario());
            precioCell.setCellStyle(currencyStyle);

            // Total
            Cell totalCell = row.createCell(4);
            totalCell.setCellValue(entrada.getCantidad() * entrada.getPrecioUnitario());
            totalCell.setCellStyle(currencyStyle);
        }

        // Total general
        if (!entradas.isEmpty()) {
            Row totalRow = sheet.createRow(rowNum);
            totalRow.createCell(3).setCellValue("TOTAL:");

            Cell totalCell = totalRow.createCell(4);
            totalCell.setCellFormula(String.format("SUM(E2:E%d)", rowNum));
            totalCell.setCellStyle(currencyStyle);
        }
    }

    private void createVentasSheet(Sheet sheet, List<Salida> salidas) {
        // Estilos
        CellStyle headerStyle = createHeaderStyle(sheet.getWorkbook());
        CellStyle currencyStyle = createCurrencyStyle(sheet.getWorkbook());
        CellStyle dateStyle = createDateStyle(sheet.getWorkbook());

        // Configurar anchos de columnas
        sheet.setColumnWidth(0, 4000);  // Fecha
        sheet.setColumnWidth(1, 6000);  // Producto
        sheet.setColumnWidth(2, 3000);  // Tipo
        sheet.setColumnWidth(3, 3000);  // Cantidad
        sheet.setColumnWidth(4, 4000);  // Precio Unitario
        sheet.setColumnWidth(5, 4000);  // Total

        // Encabezados
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Fecha", "Producto", "Tipo", "Cantidad", "Precio Unitario", "Total"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Datos
        int rowNum = 1;
        for (Salida salida : salidas) {
            Row row = sheet.createRow(rowNum++);

            // Fecha
            Cell fechaCell = row.createCell(0);
            fechaCell.setCellValue(salida.getFechaSalida());
            fechaCell.setCellStyle(dateStyle);

            // Producto
            row.createCell(1).setCellValue(salida.getProducto().getNombre());

            // Tipo
            row.createCell(2).setCellValue(salida.getTipoSalida());

            // Cantidad
            row.createCell(3).setCellValue(salida.getCantidad());

            // Precio Unitario
            Cell precioCell = row.createCell(4);
            precioCell.setCellValue(salida.getPrecioUnitario());
            precioCell.setCellStyle(currencyStyle);

            // Total
            Cell totalCell = row.createCell(5);
            totalCell.setCellValue(salida.getCantidad() * salida.getPrecioUnitario());
            totalCell.setCellStyle(currencyStyle);
        }

        // Totales por tipo
        if (!salidas.isEmpty()) {
            Row totalVentasRow = sheet.createRow(rowNum++);
            totalVentasRow.createCell(4).setCellValue("TOTAL VENTAS:");

            Cell totalVentasCell = totalVentasRow.createCell(5);
            totalVentasCell.setCellFormula(String.format("SUMIF(C2:C%d,\"VENTA\",F2:F%d)", rowNum-1, rowNum-1));
            totalVentasCell.setCellStyle(currencyStyle);

            Row totalMermasRow = sheet.createRow(rowNum++);
            totalMermasRow.createCell(4).setCellValue("TOTAL MERMAS:");

            Cell totalMermasCell = totalMermasRow.createCell(5);
            totalMermasCell.setCellFormula(String.format("SUMIF(C2:C%d,\"MERMA\",F2:F%d)", rowNum-2, rowNum-2));
            totalMermasCell.setCellStyle(currencyStyle);

            Row totalGeneralRow = sheet.createRow(rowNum);
            totalGeneralRow.createCell(4).setCellValue("TOTAL GENERAL:");

            Cell totalGeneralCell = totalGeneralRow.createCell(5);
            totalGeneralCell.setCellFormula(String.format("SUM(F2:F%d)", rowNum-2));
            totalGeneralCell.setCellStyle(currencyStyle);
        }
    }

    // Métodos auxiliares para estilos y filas
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private CellStyle createCurrencyStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("$#,##0.00"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        return style;
    }

    private CellStyle createDateStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("dd/mm/yyyy hh:mm"));
        return style;
    }

    private CellStyle createTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        font.setColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private int addSummaryRow(Sheet sheet, int rowNum, String label, double value, CellStyle currencyStyle) {
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(label);

        Cell valueCell = row.createCell(1);
        valueCell.setCellValue(value);
        valueCell.setCellStyle(currencyStyle);

        return rowNum;
    }

    private void createChart(Sheet sheet, int dataStartRow, int dataEndRow) {
        XSSFSheet xssfSheet = (XSSFSheet) sheet;
        XSSFDrawing drawing = xssfSheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 4, 3, 10, 15);

        XSSFChart chart = drawing.createChart(anchor);
        chart.setTitleText("Resumen Financiero");
        chart.setTitleOverlay(false);

        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.BOTTOM);

        // Ejes
        XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        bottomAxis.setTitle("Concepto");
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
        leftAxis.setTitle("Monto");

        // Datos
        XDDFDataSource<String> conceptos = XDDFDataSourcesFactory.fromStringCellRange(
                xssfSheet, new CellRangeAddress(dataStartRow, dataEndRow, 0, 0));

        XDDFNumericalDataSource<Double> montos = XDDFDataSourcesFactory.fromNumericCellRange(
                xssfSheet, new CellRangeAddress(dataStartRow, dataEndRow, 1, 1));

        XDDFChartData data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
        XDDFChartData.Series series = data.addSeries(conceptos, montos);
        series.setTitle("Montos", null);
        chart.plot(data);
    }




}

