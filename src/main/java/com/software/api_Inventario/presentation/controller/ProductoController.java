package com.software.api_Inventario.presentation.controller;
import com.software.api_Inventario.persistence.entities.Producto;
import com.software.api_Inventario.presentation.dto.ProductoDTO;
import com.software.api_Inventario.service.interfaces.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    @PostMapping
    public Producto crearProducto(@RequestBody ProductoDTO dto) {
        return productoService.crearProducto(dto);
    }

    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    @GetMapping("/{id}")
    public Producto obtenerProducto(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }

    @GetMapping("/stock-bajo")
    public List<Producto> productosConStockBajo() {
        return productoService.listarProductosConStockBajo();
    }

}
