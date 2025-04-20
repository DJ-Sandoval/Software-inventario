package com.software.api_Inventario.persistence.repository;

import com.software.api_Inventario.persistence.entities.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    List<Entrada> findByFechaEntradaBetween(LocalDateTime desde, LocalDateTime hasta);

    List<Entrada> findByProductoIdAndFechaEntradaBetween(Long productoId, LocalDateTime desde, LocalDateTime hasta);

}
