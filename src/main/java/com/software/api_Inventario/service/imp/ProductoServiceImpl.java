package com.software.api_Inventario.service.imp;

import com.software.api_Inventario.persistence.entities.Producto;
import com.software.api_Inventario.persistence.entities.Proveedor;
import com.software.api_Inventario.persistence.repository.ProductoRepository;
import com.software.api_Inventario.persistence.repository.ProveedorRepository;
import com.software.api_Inventario.presentation.dto.ProductoDTO;
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

    @Override
    @Transactional
    public Producto crearProducto(ProductoDTO productoDTO) {
        if (productoRepository.findByCodigo(productoDTO.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un producto con este código");
        }

        Proveedor proveedor = proveedorRepository.findById(productoDTO.getProveedorId())
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con id: " + productoDTO.getProveedorId()));

        Producto producto = Producto.builder()
                .codigo(productoDTO.getCodigo())
                .nombre(productoDTO.getNombre())
                .descripcion(productoDTO.getDescripcion())
                .categoria(productoDTO.getCategoria())
                .precioCompra(productoDTO.getPrecioCompra())
                .precioVenta(productoDTO.getPrecioVenta())
                .stockActual(productoDTO.getStockActual())
                .stockMinimo(productoDTO.getStockMinimo())
                .proveedor(proveedor)
                .activo(true)
                .build();

        return productoRepository.save(producto);
    }


    @Override
    @Transactional
    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        producto.setNombre(productoActualizado.getNombre());
        producto.setDescripcion(productoActualizado.getDescripcion());
        producto.setCategoria(productoActualizado.getCategoria());
        producto.setPrecioCompra(productoActualizado.getPrecioCompra());
        producto.setPrecioVenta(productoActualizado.getPrecioVenta());
        producto.setStockMinimo(productoActualizado.getStockMinimo());
        producto.setProveedor(productoActualizado.getProveedor());

        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void eliminarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        producto.setActivo(false);
        productoRepository.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Producto obtenerProductoPorCodigo(String codigo) {
        return productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con código: " + codigo));
    }

    @Override
    public Page<ProductoDTO> listarTodosLosProductos(Pageable pageable) {
        Page<Producto> productos = productoRepository.findAll(pageable);
        return productos.map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarProductosPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarProductosConStockBajo() {
        int stockMinimoGeneral = 10;
        return productoRepository.findByStockActualLessThanEqual(stockMinimoGeneral);
    }

    @Override
    @Transactional
    public Producto actualizarStock(Long productoId, Integer cantidad, String operacion) {
        Producto producto = obtenerProductoPorId(productoId);

        switch (operacion.toUpperCase()) {
            case "INCREMENTO":
                producto.setStockActual(producto.getStockActual() + cantidad);
                break;
            case "DECREMENTO":
                if (producto.getStockActual() < cantidad) {
                    throw new StockException("No hay suficiente stock para realizar esta operación");
                }
                producto.setStockActual(producto.getStockActual() - cantidad);
                break;
            default:
                throw new IllegalArgumentException("Operación no válida: " + operacion);
        }

        return productoRepository.save(producto);
    }

    @Override
    public ProductoDTO mapToDTO(Producto producto) {
        return new ProductoDTO(
          producto.getId(),
          producto.getCodigo(),
          producto.getNombre(),
          producto.getDescripcion(),
          producto.getCategoria(),
          producto.getPrecioCompra(),
          producto.getPrecioVenta(),
          producto.getStockActual(),
          producto.getStockMinimo(),
          producto.getProveedor() != null ? producto.getProveedor().getId() : null,
          producto.getActivo());
    }
}
