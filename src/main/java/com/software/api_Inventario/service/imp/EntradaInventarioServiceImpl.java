package com.software.api_Inventario.service.imp;

import com.software.api_Inventario.persistence.entities.EntradaInventario;
import com.software.api_Inventario.persistence.entities.Producto;
import com.software.api_Inventario.persistence.repository.EntradaInventarioRepository;
import com.software.api_Inventario.service.interfaces.EntradaInventarioService;
import com.software.api_Inventario.service.interfaces.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntradaInventarioServiceImpl implements EntradaInventarioService {
    private final EntradaInventarioRepository entradaRepository;
    private final ProductoService productoService;

    @Override
    @Transactional
    public EntradaInventario registrarEntrada(EntradaInventario entrada) {
        // Actualizar el stock del producto
        productoService.actualizarStock(entrada.getProducto().getId(),
                entrada.getCantidad(),
                "INCREMENTO");

        return entradaRepository.save(entrada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntradaInventario> obtenerEntradasPorProducto(Long productoId) {
        Producto producto = productoService.obtenerProductoPorId(productoId);
        return entradaRepository.findByProducto(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntradaInventario> obtenerEntradasPorProveedor(Long proveedorId) {
        return entradaRepository.findByProveedorId(proveedorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntradaInventario> obtenerEntradasPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return entradaRepository.findByFechaEntradaBetween(inicio, fin);
    }
}
