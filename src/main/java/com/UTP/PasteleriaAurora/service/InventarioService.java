package com.UTP.PasteleriaAurora.service;

import com.UTP.PasteleriaAurora.model.Inventario;
import com.UTP.PasteleriaAurora.model.InventarioHistorial;
import com.UTP.PasteleriaAurora.model.Producto;
import com.UTP.PasteleriaAurora.repository.InventarioHistorialRepository;
import com.UTP.PasteleriaAurora.repository.InventarioRepository;
import com.UTP.PasteleriaAurora.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private InventarioHistorialRepository inventarioHistorialRepository;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public List<Inventario> listarInventarios() {
        List<Inventario> inventarios = inventarioRepository.findAll();

        for (Inventario inv : inventarios) {
            if (inv.getFechaActualizacion() != null) {
                inv.setFechaFormateada(inv.getFechaActualizacion().format(FORMATTER));
            } else {
                inv.setFechaFormateada("Sin fecha");
            }
        }

        return inventarios;
    }

    public Optional<Inventario> buscarPorId(Long id) {
        return inventarioRepository.findById(id);
    }

    public Optional<Inventario> buscarPorProductoId(Long productoId) {
        return inventarioRepository.findByProductoId(productoId);
    }

    public Inventario guardarInventario(Inventario inventario) {
        inventario.setFechaActualizacion(LocalDateTime.now());
        return inventarioRepository.save(inventario);
    }

    public Inventario crearInventarioProducto(Long productoId, int cantidadInicial, int cantidadMinima) {
        Optional<Producto> productoOpt = productoRepository.findById(productoId);
        if (productoOpt.isEmpty()) {
            throw new RuntimeException("Producto no encontrado para inventario");
        }
        Inventario inventario = new Inventario();
        inventario.setProducto(productoOpt.get());
        inventario.setCantidadActual(cantidadInicial);
        inventario.setCantidadMinima(cantidadMinima);
        inventario.setFechaActualizacion(LocalDateTime.now());
        return inventarioRepository.save(inventario);
    }

    @Transactional
    public Inventario actualizarInventario(Long inventarioId, Integer cantidadActual, Integer cantidadMinima) {
        Inventario inventario = inventarioRepository.findById(inventarioId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        // Guardar historial con valores anteriores
        InventarioHistorial historial = new InventarioHistorial();
        historial.setInventario(inventario);
        historial.setCantidadActualAnterior(inventario.getCantidadActual());
        historial.setCantidadMinimaAnterior(inventario.getCantidadMinima());
        historial.setFechaCambio(inventario.getFechaActualizacion());
        historial.setFechaRegistro(LocalDateTime.now());

        inventarioHistorialRepository.save(historial);

        // Actualizar inventario
        inventario.setCantidadActual(cantidadActual);
        inventario.setCantidadMinima(cantidadMinima);
        inventario.setFechaActualizacion(LocalDateTime.now());

        return inventarioRepository.save(inventario);
    }

    public void eliminarInventario(Long id) {
        inventarioRepository.deleteById(id);
    }


    public void reducirInventario(Long productoId, int cantidad) {
        Inventario inventario = buscarPorProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        if (inventario.getCantidadActual() < cantidad) {
            throw new RuntimeException("Stock insuficiente para el producto: " + inventario.getProducto().getNombre());
        }
        actualizarInventario(inventario.getId(), inventario.getCantidadActual() - cantidad,
                inventario.getCantidadMinima());
    }

    
    public List<Inventario> obtenerInventariosBajoStock() {
        return inventarioRepository.findAll().stream()
                .filter(inv -> inv.getCantidadActual() < inv.getCantidadMinima())
                .toList();
    }
}
