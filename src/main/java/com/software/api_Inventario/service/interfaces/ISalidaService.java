package com.software.api_Inventario.service.interfaces;

import com.software.api_Inventario.persistence.entities.Salida;
import com.software.api_Inventario.presentation.dto.SalidaDTO;
import java.time.LocalDateTime;
import java.util.List;

public interface ISalidaService {
    Salida registrarSalida(SalidaDTO dto);
    List<Salida> listarSalidas();
    List<Salida> listarSalidasPorFechas(LocalDateTime desde, LocalDateTime hasta);
}
