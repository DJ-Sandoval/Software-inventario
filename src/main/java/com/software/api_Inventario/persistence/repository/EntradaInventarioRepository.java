package com.software.api_Inventario.persistence.repository;

import com.software.api_Inventario.persistence.entities.EntradaInventario;
import com.software.api_Inventario.persistence.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EntradaInventarioRepository extends JpaRepository<EntradaInventario, Long> {
    List<EntradaInventario> findByProducto(Producto producto);

    List<EntradaInventario> findByFechaEntradaBetween(LocalDateTime inicio, LocalDateTime fin);

    List<EntradaInventario> findByProveedorId(Long proveedorId);
}
