package com.software.api_Inventario.service.interfaces;

import com.software.api_Inventario.persistence.entities.EntradaInventario;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface EntradaInventarioService {
    EntradaInventario registrarEntrada(EntradaInventario entrada);
    List<EntradaInventario> obtenerEntradasPorProducto(Long productoId);
    List<EntradaInventario> obtenerEntradasPorProveedor(Long proveedorId);
    List<EntradaInventario> obtenerEntradasPorRangoFechas(LocalDateTime inicio, LocalDateTime fin);
}
