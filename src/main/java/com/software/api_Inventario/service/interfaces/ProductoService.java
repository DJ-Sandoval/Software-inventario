package com.software.api_Inventario.service.interfaces;
import com.software.api_Inventario.persistence.entities.Producto;
import com.software.api_Inventario.presentation.dto.ProductoDTO;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductoService {
    Producto crearProducto(ProductoDTO dto);
    List<Producto> listarProductos();
    Producto actualizarProducto(Long id, ProductoDTO dto);
    Producto obtenerProductoPorId(Long id);
    void eliminarProducto(Long id);
    List<Producto> listarProductosConStockBajo();
}