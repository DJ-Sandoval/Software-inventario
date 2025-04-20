package com.software.api_Inventario.service.imp;

import com.software.api_Inventario.persistence.entities.Categoria;
import com.software.api_Inventario.persistence.repository.CategoriaRepository;
import com.software.api_Inventario.persistence.repository.ProductoRepository;
import com.software.api_Inventario.presentation.dto.CategoriaDTO;
import com.software.api_Inventario.service.exception.ResourceNotFoundException;
import com.software.api_Inventario.service.interfaces.ICategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImp implements ICategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    @Override
    public Categoria crearCategoria(CategoriaDTO dto) {
        Categoria categoria = Categoria.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .estado(dto.isEstado())
                .build();
        return categoriaRepository.save(categoria);
    }

    @Override
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"));
    }

    @Override
    public void eliminarCategoria(Long id) {
        Categoria categoria = obtenerCategoriaPorId(id);
        categoriaRepository.delete(categoria);
    }
}
