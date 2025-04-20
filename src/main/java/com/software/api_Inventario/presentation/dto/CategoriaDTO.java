package com.software.api_Inventario.presentation.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    private String nombre;
    private String descripcion;
    private boolean estado;
}
