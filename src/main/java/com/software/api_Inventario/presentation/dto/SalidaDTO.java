package com.software.api_Inventario.presentation.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalidaDTO {
    private Long productoId;
    private int cantidad;
    private double precioUnitario;
    private String tipoSalida;
}
