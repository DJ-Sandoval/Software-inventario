package com.software.api_Inventario.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "proveedores")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String direccion;

    @Column(nullable = false, unique = true)
    private String telefono;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private Boolean activo = true;

    @OneToMany(mappedBy = "proveedor", fetch = FetchType.LAZY)
    private List<Producto> productos;
}
