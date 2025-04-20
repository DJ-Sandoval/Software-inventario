package com.software.api_Inventario.service.interfaces;

import java.time.LocalDateTime;

public interface IMovimientoService {
    String generarReporteMovimientosPorProducto(Long idProducto, LocalDateTime desde, LocalDateTime hasta) throws Exception;
}
