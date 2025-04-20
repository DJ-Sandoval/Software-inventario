package com.software.api_Inventario.service.interfaces;

import com.software.api_Inventario.persistence.entities.Categoria;
import com.software.api_Inventario.presentation.dto.CategoriaDTO;

import java.util.List;

public interface ICategoriaService {
    Categoria crearCategoria(CategoriaDTO dto);
    List<Categoria> listarCategorias();
    Categoria obtenerCategoriaPorId(Long id);
    void eliminarCategoria(Long id);
}
