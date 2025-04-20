package com.software.api_Inventario.service.interfaces;

import com.software.api_Inventario.persistence.entities.Proveedor;
import com.software.api_Inventario.presentation.dto.ProveedorDTO;

import java.util.List;

public interface IProveedorService {
    Proveedor crearProveedor(ProveedorDTO dto);
    List<Proveedor> listarProveedores();
    Proveedor obtenerProveedorPorId(Long id);
    void eliminarProveedor(Long id);
}
