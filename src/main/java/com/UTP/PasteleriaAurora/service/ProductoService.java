package com.UTP.PasteleriaAurora.service;

import com.UTP.PasteleriaAurora.model.Producto;
import com.UTP.PasteleriaAurora.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private InventarioService inventarioService;

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto guardarProducto(Producto producto) {
        Producto productoGuardado = productoRepository.save(producto);

        // Crear inventario inicial para el producto si no existe
        inventarioService.buscarPorProductoId(productoGuardado.getId())
            .orElseGet(() -> inventarioService.crearInventarioProducto(productoGuardado.getId(), 0, 5));

        return productoGuardado;
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
