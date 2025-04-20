package com.software.api_Inventario.presentation.reports;

import com.software.api_Inventario.service.interfaces.IMovimientoService;
import com.software.api_Inventario.service.interfaces.IReporteFinancieroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {
    @Autowired
    private IMovimientoService movimientoService;

    @Autowired
    private IReporteFinancieroService reporteFinancieroService;

    @GetMapping("/movimientos")
    public ResponseEntity<?> generarReporteMovimientos(
            @RequestParam Long idProducto,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {

        try {
            String filePath = movimientoService.generarReporteMovimientosPorProducto(idProducto, desde, hasta);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of(
                            "message", "Reporte generado exitosamente",
                            "path", filePath));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error generando reporte: " + e.getMessage()));
        }
    }

    @GetMapping("/finanzas")
    public ResponseEntity<?> generarReporteFinanzas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {

        try {
            String filePath = reporteFinancieroService.generarReporteCostosProfit(desde, hasta);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of(
                            "message", "Reporte financiero generado exitosamente",
                            "path", filePath));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error generando reporte: " + e.getMessage()));
        }
    }

}
