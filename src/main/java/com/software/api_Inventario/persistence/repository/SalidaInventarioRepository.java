package com.software.api_Inventario.persistence.repository;

import com.software.api_Inventario.persistence.entities.Producto;
import com.software.api_Inventario.persistence.entities.SalidaInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalidaInventarioRepository extends JpaRepository<SalidaInventario, Long> {
    List<SalidaInventario> findByProducto(Producto producto);

    List<SalidaInventario> findByFechaSalidaBetween(LocalDateTime inicio, LocalDateTime fin);

    List<SalidaInventario> findByTipo(String tipo);
}
