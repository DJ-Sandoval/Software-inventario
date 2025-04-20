package com.software.api_Inventario.service.exception;

public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException(Long id) {
        super("producto no encontrado con el ID: " + id);
    }
}
