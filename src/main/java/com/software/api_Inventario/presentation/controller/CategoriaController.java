package com.software.api_Inventario.presentation.controller;

import com.software.api_Inventario.persistence.entities.Categoria;
import com.software.api_Inventario.presentation.dto.CategoriaDTO;
import com.software.api_Inventario.service.interfaces.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    @Autowired
    private ICategoriaService service;

    @GetMapping
    public List<Categoria> listarCategorias() {
        return service.listarCategorias();
    }

    @GetMapping("/{id}")
    public Categoria obtenerCategoriaPorId(@PathVariable Long id) {
        return service.obtenerCategoriaPorId(id);
    }

    @PostMapping
    public Categoria crearCategoria(@RequestBody CategoriaDTO dto) {
        return service.crearCategoria(dto);
    }

    @DeleteMapping
    public void eliminarCategoria(Long id) {
        service.eliminarCategoria(id);
    }
}
