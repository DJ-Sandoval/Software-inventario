package com.software.api_Inventario.persistence.repository;

import com.software.api_Inventario.persistence.entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    Optional<Proveedor> findByTelefono(String telefono);

    Optional<Proveedor> findByEmail(String email);

    List<Proveedor> findByActivoTrue();
}
