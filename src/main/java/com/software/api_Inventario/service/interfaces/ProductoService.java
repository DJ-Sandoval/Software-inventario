package com.software.api_Inventario.service.interfaces;
import com.software.api_Inventario.persistence.entities.Producto;
import com.software.api_Inventario.presentation.dto.ProductoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductoService {
    Producto crearProducto(ProductoDTO producto);
    Producto actualizarProducto(Long id, Producto producto);
    void eliminarProducto(Long id);
    Producto obtenerProductoPorId(Long id);
    Producto obtenerProductoPorCodigo(String codigo);
    Page<ProductoDTO> listarTodosLosProductos(Pageable pageable);
    List<Producto> listarProductosPorCategoria(String categoria);
    List<Producto> listarProductosConStockBajo();
    Producto actualizarStock(Long productoId, Integer cantidad, String operacion);
    ProductoDTO mapToDTO(Producto producto);
}