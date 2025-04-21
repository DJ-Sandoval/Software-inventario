package com.software.api_Inventario.service.interfaces;

import com.software.api_Inventario.persistence.entities.Producto;

import java.util.List;

public interface ReporteProductosServicePdf {
    void generarReporteProductos(List<Producto> productos);
}
