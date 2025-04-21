package com.software.api_Inventario.service.imp;

import com.software.api_Inventario.persistence.entities.Categoria;
import com.software.api_Inventario.persistence.entities.Producto;
import com.software.api_Inventario.persistence.entities.Proveedor;
import com.software.api_Inventario.persistence.repository.CategoriaRepository;
import com.software.api_Inventario.persistence.repository.ProductoRepository;
import com.software.api_Inventario.persistence.repository.ProveedorRepository;
import com.software.api_Inventario.presentation.dto.ProductoDTO;
import com.software.api_Inventario.service.exception.ProductoNotFoundException;
import com.software.api_Inventario.service.exception.ResourceNotFoundException;
import com.software.api_Inventario.service.exception.StockException;
import com.software.api_Inventario.service.interfaces.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final CategoriaRepository categoriaRepository;


    @Override
    public Producto crearProducto(ProductoDTO dto) {
        Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        Producto producto = Producto.builder()
                .codigo(dto.getCodigo())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .precioCompra(dto.getPrecioCompra())
                .precioVenta(dto.getPrecioVenta())
                .stock(dto.getStock())
                .stockMinimo(dto.getStockMinimo())
                .estado(dto.isEstado())
                .proveedor(proveedor)
                .categoria(categoria)
                .build();

        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Override
    @Transactional
    public Producto actualizarProducto(Long id, ProductoDTO dto) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        // Verificamos y actualizamos proveedor si es diferente
        if (!productoExistente.getProveedor().getId().equals(dto.getProveedorId())) {
            Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));
            productoExistente.setProveedor(proveedor);
        }

        // Verificamos y actualizamos categoría si es diferente
        if (!productoExistente.getCategoria().getId().equals(dto.getCategoriaId())) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
            productoExistente.setCategoria(categoria);
        }

        // Actualizamos los demás campos
        productoExistente.setCodigo(dto.getCodigo());
        productoExistente.setNombre(dto.getNombre());
        productoExistente.setDescripcion(dto.getDescripcion());
        productoExistente.setPrecioCompra(dto.getPrecioCompra());
        productoExistente.setPrecioVenta(dto.getPrecioVenta());
        productoExistente.setStock(dto.getStock());
        productoExistente.setStockMinimo(dto.getStockMinimo());
        productoExistente.setEstado(dto.isEstado());

        return productoRepository.save(productoExistente);
    }

    @Override
    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
    }

    @Override
    public void eliminarProducto(Long id) {
        Producto producto = obtenerProductoPorId(id);
        productoRepository.delete(producto);
    }

    @Override
    public List<Producto> listarProductosConStockBajo() {
        return productoRepository.findByStockLessThanEqual(10);
    }
}
