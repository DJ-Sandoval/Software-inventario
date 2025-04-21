package com.software.api_Inventario.presentation.web;

import com.software.api_Inventario.persistence.entities.Producto;
import com.software.api_Inventario.persistence.repository.ProductoRepository;
import com.software.api_Inventario.presentation.dto.ProductoDTO;
import com.software.api_Inventario.service.interfaces.ICategoriaService;
import com.software.api_Inventario.service.interfaces.IProveedorService;
import com.software.api_Inventario.service.interfaces.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/productos")
public class ProductoWebController {
    @Autowired
    private ProductoService productoService;

    @Autowired
    private IProveedorService proveedorService;

    @Autowired
    private ICategoriaService categoriaService;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/lista")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER', 'USER', 'INVITED') and hasAuthority('READ')")
    public String listarProductos() {
        return "Productos";  // donde "productos/lista" es la ruta de esta plantilla
    }



}
