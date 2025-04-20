package com.software.api_Inventario.presentation.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProveedorDTO {
    private String nombre;
    private String telefono;
    private String correo;
    private String direccion;
    private String contacto;
    private boolean estado;
}
