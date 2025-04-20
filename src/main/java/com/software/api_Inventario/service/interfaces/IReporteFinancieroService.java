package com.software.api_Inventario.service.interfaces;

import java.time.LocalDateTime;

public interface IReporteFinancieroService {
    String generarReporteCostosProfit(LocalDateTime desde, LocalDateTime hasta) throws Exception;
}
