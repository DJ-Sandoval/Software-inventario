package com.software.api_Inventario.service.imp;

import com.software.api_Inventario.persistence.entities.Producto;
import com.software.api_Inventario.persistence.entities.Salida;
import com.software.api_Inventario.persistence.repository.ProductoRepository;
import com.software.api_Inventario.persistence.repository.SalidaRepository;
import com.software.api_Inventario.presentation.dto.SalidaDTO;
import com.software.api_Inventario.service.exception.ResourceNotFoundException;
import com.software.api_Inventario.service.interfaces.ISalidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalidaServiceImp implements ISalidaService {
    private final SalidaRepository salidaRepository;
    private final ProductoRepository productoRepository;

    @Override
    public Salida registrarSalida(SalidaDTO dto) {
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        if (producto.getStock() < dto.getCantidad()) {
            throw new RuntimeException("Stock insuficiente");
        }

        producto.setStock(producto.getStock() - dto.getCantidad());
        productoRepository.save(producto);

        Salida salida = Salida.builder()
                .producto(producto)
                .cantidad(dto.getCantidad())
                .precioUnitario(dto.getPrecioUnitario())
                .tipoSalida(dto.getTipoSalida())
                .fechaSalida(LocalDateTime.now())
                .build();

        return salidaRepository.save(salida);
    }

    @Override
    public List<Salida> listarSalidas() {
        return salidaRepository.findAll();
    }

    @Override
    public List<Salida> listarSalidasPorFechas(LocalDateTime desde, LocalDateTime hasta) {
        return salidaRepository.findByFechaSalidaBetween(desde, hasta);
    }
}
