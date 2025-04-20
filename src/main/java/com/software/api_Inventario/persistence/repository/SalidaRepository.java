package com.software.api_Inventario.persistence.repository;

import com.software.api_Inventario.persistence.entities.Salida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalidaRepository extends JpaRepository<Salida, Long> {
    List<Salida> findByFechaSalidaBetween(LocalDateTime desde, LocalDateTime hasta);
    List<Salida> findByProductoIdAndFechaSalidaBetween(Long productoId, LocalDateTime desde, LocalDateTime hasta);
}
