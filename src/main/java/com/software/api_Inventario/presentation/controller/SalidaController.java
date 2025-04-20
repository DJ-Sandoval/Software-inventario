package com.software.api_Inventario.presentation.controller;

import com.software.api_Inventario.persistence.entities.Salida;
import com.software.api_Inventario.presentation.dto.SalidaDTO;
import com.software.api_Inventario.service.interfaces.ISalidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/salidas")
public class SalidaController {
    @Autowired
    private ISalidaService salidaService;

    @PostMapping
    public Salida registrarSalida(@RequestBody SalidaDTO dto) {
        return salidaService.registrarSalida(dto);
    }

    @GetMapping
    public List<Salida> listarSalidas() {
        return salidaService.listarSalidas();
    }

    @GetMapping("/fechas")
    public List<Salida> listarPorFechas(@RequestParam String desde, @RequestParam String hasta) {
        LocalDateTime fDesde = LocalDateTime.parse(desde);
        LocalDateTime fHasta = LocalDateTime.parse(hasta);
        return salidaService.listarSalidasPorFechas(fDesde, fHasta);
    }

}
