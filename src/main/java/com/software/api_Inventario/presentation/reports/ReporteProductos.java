package com.software.api_Inventario.presentation.reports;

import com.software.api_Inventario.persistence.entities.Producto;
import com.software.api_Inventario.persistence.repository.ProductoRepository;
import com.software.api_Inventario.service.interfaces.ProductoService;
import com.software.api_Inventario.service.interfaces.ReporteProductosExcell;
import com.software.api_Inventario.service.interfaces.ReporteProductosServicePdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@RestController
@RequestMapping("/reporte/productos")
public class ReporteProductos {
    @Autowired
    private ReporteProductosServicePdf reporteProductosService;
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ReporteProductosExcell reporteProductosExcellService;

    @GetMapping("/pdf")
    public ResponseEntity<String> generarReporte() {
        List<Producto> productos = productoRepository.findAll();
        reporteProductosService.generarReporteProductos(productos);
        return ResponseEntity.ok("Reporte generado con éxito.");
    }

    @GetMapping("/excel")
    public ResponseEntity<String> descargarReporteExcel() {
        List<Producto> productos = productoRepository.findAll();
        reporteProductosExcellService.generarReporteProductosExcel(productos);
        return ResponseEntity.ok("Reporte Excel generado correctamente.");
    }
}
