package com.software.api_Inventario.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "productos")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String codigo;
    @Column(nullable = false)
    private String nombre;
    private String descripcion;
    @Column(name = "precio_compra", nullable = false)
    private double precioCompra;
    @Column(name = "precio_venta", nullable = false)
    private double precioVenta;
    private int stock;
    private int stockMinimo;
    @Column(nullable = false)
    private boolean estado;

    @ManyToOne
    private Proveedor proveedor;

    @ManyToOne
    private Categoria categoria;

}
