package com.software.api_Inventario.presentation.controller;

import com.software.api_Inventario.persistence.entities.Entrada;
import com.software.api_Inventario.presentation.dto.EntradaDTO;
import com.software.api_Inventario.service.interfaces.IEntradaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/entradas")
public class EntradaController {
    @Autowired
    private IEntradaService entradaService;

    @PostMapping
    public Entrada registrarEntrada(@RequestBody EntradaDTO dto) {
        return entradaService.registrarEntrada(dto);
    }

    @GetMapping("/listar")
    public List<Entrada> listarEntradas() {
        return entradaService.listarEntradas();
    }

    @GetMapping("/fechas")
    public List<Entrada> listarPorFechas(@RequestParam String desde, @RequestParam String hasta) {
        LocalDateTime fDesde = LocalDateTime.parse(desde);
        LocalDateTime fHasta = LocalDateTime.parse(hasta);
        return entradaService.listarEntradasPorFechas(fDesde, fHasta);
    }

}
