package com.software.api_Inventario.persistence.repository;

import com.software.api_Inventario.persistence.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
