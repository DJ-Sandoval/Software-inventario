package com.software.api_Inventario.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "salidas_inventario")
public class SalidaInventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "fecha_salida", nullable = false)
    private LocalDateTime fechaSalida = LocalDateTime.now();

    private String motivo;

    @Column(nullable = false)
    private String tipo; // "VENTA" o "CONSUMO_INTERNO"
}
