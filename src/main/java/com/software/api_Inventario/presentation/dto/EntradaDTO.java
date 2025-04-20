package com.software.api_Inventario.presentation.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntradaDTO {
    private Long productoId;
    private int cantidad;
    private double precioUnitario;
}
