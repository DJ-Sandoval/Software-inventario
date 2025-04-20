package com.software.api_Inventario.service.imp;

import com.software.api_Inventario.persistence.entities.Entrada;
import com.software.api_Inventario.persistence.entities.Producto;
import com.software.api_Inventario.persistence.repository.EntradaRepository;
import com.software.api_Inventario.persistence.repository.ProductoRepository;
import com.software.api_Inventario.presentation.dto.EntradaDTO;
import com.software.api_Inventario.service.exception.ResourceNotFoundException;
import com.software.api_Inventario.service.interfaces.IEntradaService;
import com.software.api_Inventario.service.interfaces.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntradaServiceImp implements IEntradaService {
    private final EntradaRepository entradaRepository;
    private final ProductoRepository productoRepository;


    @Override
    public Entrada registrarEntrada(EntradaDTO dto) {
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        producto.setStock(producto.getStock() + dto.getCantidad());
        productoRepository.save(producto);

        Entrada entrada = Entrada.builder()
                .producto(producto)
                .cantidad(dto.getCantidad())
                .precioUnitario(dto.getPrecioUnitario())
                .fechaEntrada(LocalDateTime.now())
                .build();

        return entradaRepository.save(entrada);
    }

    @Override
    public List<Entrada> listarEntradas() {
        return entradaRepository.findAll();
    }

    @Override
    public List<Entrada> listarEntradasPorFechas(LocalDateTime desde, LocalDateTime hasta) {
        return entradaRepository.findByFechaEntradaBetween(desde, hasta);
    }
}
