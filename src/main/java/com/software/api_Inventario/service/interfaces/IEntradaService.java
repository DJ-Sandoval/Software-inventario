package com.software.api_Inventario.service.interfaces;

import com.software.api_Inventario.persistence.entities.Entrada;
import com.software.api_Inventario.presentation.dto.EntradaDTO;
import java.time.LocalDateTime;

import java.util.List;

public interface IEntradaService {
    Entrada registrarEntrada(EntradaDTO dto);
    List<Entrada> listarEntradas();
    List<Entrada> listarEntradasPorFechas(LocalDateTime desde, LocalDateTime hasta);
}
