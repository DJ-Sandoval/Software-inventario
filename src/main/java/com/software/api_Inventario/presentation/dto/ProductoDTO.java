package com.software.api_Inventario.presentation.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precioCompra;
    private double precioVenta;
    private int stock;
    private int stockMinimo;
    private boolean estado;
    private Long proveedorId;
    private Long categoriaId;
}

