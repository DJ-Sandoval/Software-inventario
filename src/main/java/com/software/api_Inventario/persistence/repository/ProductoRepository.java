package com.software.api_Inventario.persistence.repository;

import com.software.api_Inventario.persistence.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByCodigo(String codigo);

    List<Producto> findByCategoria(String categoria);

    List<Producto> findByStockActualLessThanEqual(Integer stockMinimo);

    @Query("SELECT p FROM Producto p WHERE p.activo = true")
    List<Producto> findAllActivos();
}
