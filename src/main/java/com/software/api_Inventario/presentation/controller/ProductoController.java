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
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO productoDTO) {
        Producto nuevoProducto = productoService.crearProducto(productoDTO);
        return new ResponseEntity<>(productoService.mapToDTO(nuevoProducto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizarProducto(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Producto producto = productoService.obtenerProductoPorId(id);
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Producto> obtenerProductoPorCodigo(@PathVariable String codigo) {
        Producto producto = productoService.obtenerProductoPorCodigo(codigo);
        return ResponseEntity.ok(producto);
    }

    @GetMapping
    public ResponseEntity<Page<ProductoDTO>> listarTodosLosProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ProductoDTO> productos = productoService.listarTodosLosProductos(PageRequest.of(page, size));
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> listarProductosPorCategoria(@PathVariable String categoria) {
        List<Producto> productos = productoService.listarProductosPorCategoria(categoria);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<List<Producto>> listarProductosConStockBajo() {
        List<Producto> productos = productoService.listarProductosConStockBajo();
        return ResponseEntity.ok(productos);
    }
}
