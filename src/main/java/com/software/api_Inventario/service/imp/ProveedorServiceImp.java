package com.software.api_Inventario.service.imp;

import com.software.api_Inventario.persistence.entities.Proveedor;
import com.software.api_Inventario.persistence.repository.ProveedorRepository;
import com.software.api_Inventario.presentation.dto.ProveedorDTO;
import com.software.api_Inventario.service.exception.ResourceNotFoundException;
import com.software.api_Inventario.service.interfaces.IProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImp implements IProveedorService {
    private final ProveedorRepository proveedorRepository;

    @Override
    public Proveedor crearProveedor(ProveedorDTO dto) {
        Proveedor proveedor = Proveedor.builder()
                .nombre(dto.getNombre())
                .telefono(dto.getTelefono())
                .correo(dto.getCorreo())
                .direccion(dto.getDireccion())
                .contacto(dto.getContacto())
                .estado(dto.isEstado())
                .build();
        return proveedorRepository.save(proveedor);
    }

    @Override
    public List<Proveedor> listarProveedores() {
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedor obtenerProveedorPorId(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));
    }

    @Override
    public void eliminarProveedor(Long id) {
        Proveedor proveedor = obtenerProveedorPorId(id);
        proveedorRepository.delete(proveedor);
    }
}
