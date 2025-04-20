package com.software.api_Inventario.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Salida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Producto producto;

    private int cantidad;
    private double precioUnitario;
    private String tipoSalida;
    private LocalDateTime fechaSalida;


}
