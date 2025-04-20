package com.software.api_Inventario.presentation.controller;

import com.software.api_Inventario.persistence.entities.Proveedor;
import com.software.api_Inventario.presentation.dto.ProveedorDTO;
import com.software.api_Inventario.service.interfaces.IProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {
    @Autowired
    private IProveedorService service;

    @GetMapping
    public List<Proveedor> listarProveedores() {
        return service.listarProveedores();
    }

    @GetMapping("/{id}")
    public Proveedor obtenerProveedor(@PathVariable Long id) {
        return service.obtenerProveedorPorId(id);
    }

    @PostMapping
    public Proveedor crearProveedor(@RequestBody ProveedorDTO dto) {
        return service.crearProveedor(dto);
    }

    @DeleteMapping("/{id}")
    public void eliminarProveedor(@PathVariable Long id) {
        service.eliminarProveedor(id);
    }
}
