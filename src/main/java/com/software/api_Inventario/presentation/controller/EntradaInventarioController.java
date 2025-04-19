package com.software.api_Inventario.presentation.controller;
import com.software.api_Inventario.persistence.entities.EntradaInventario;
import com.software.api_Inventario.service.interfaces.EntradaInventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/entradas")
@RequiredArgsConstructor
public class EntradaInventarioController {
    private final EntradaInventarioService entradaService;

    @PostMapping
    public ResponseEntity<EntradaInventario> registrarEntrada(@RequestBody EntradaInventario entrada) {
        EntradaInventario nuevaEntrada = entradaService.registrarEntrada(entrada);
        return new ResponseEntity<>(nuevaEntrada, HttpStatus.CREATED);
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<EntradaInventario>> obtenerEntradasPorProducto(@PathVariable Long productoId) {
        List<EntradaInventario> entradas = entradaService.obtenerEntradasPorProducto(productoId);
        return ResponseEntity.ok(entradas);
    }

    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<List<EntradaInventario>> obtenerEntradasPorProveedor(@PathVariable Long proveedorId) {
        List<EntradaInventario> entradas = entradaService.obtenerEntradasPorProveedor(proveedorId);
        return ResponseEntity.ok(entradas);
    }

    @GetMapping("/rango-fechas")
    public ResponseEntity<List<EntradaInventario>> obtenerEntradasPorRangoFechas(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fin) {
        List<EntradaInventario> entradas = entradaService.obtenerEntradasPorRangoFechas(inicio, fin);
        return ResponseEntity.ok(entradas);
    }
}
