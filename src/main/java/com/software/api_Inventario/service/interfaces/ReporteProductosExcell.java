package com.software.api_Inventario.service.interfaces;

import com.software.api_Inventario.persistence.entities.Producto;

import java.util.List;

public interface ReporteProductosExcell {
    void generarReporteProductosExcel(List<Producto> productos);
}
