package com.UTP.PasteleriaAurora;

import com.UTP.PasteleriaAurora.model.Inventario;
import com.UTP.PasteleriaAurora.model.Producto;
import com.UTP.PasteleriaAurora.repository.InventarioHistorialRepository;
import com.UTP.PasteleriaAurora.repository.InventarioRepository;
import com.UTP.PasteleriaAurora.repository.ProductoRepository;
import com.UTP.PasteleriaAurora.service.InventarioService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private InventarioHistorialRepository inventarioHistorialRepository;

    @InjectMocks
    private InventarioService inventarioService;

    @Test
    void crearInventarioProducto_exitoso() {
        Producto prod = new Producto();
        prod.setId(5L);
        prod.setNombre("Muffin");

        when(productoRepository.findById(5L)).thenReturn(Optional.of(prod));

        Inventario saved = new Inventario();
        saved.setId(10L);
        saved.setProducto(prod);

        when(inventarioRepository.save(any(Inventario.class))).thenReturn(saved);

        Inventario resultado = inventarioService.crearInventarioProducto(5L, 2, 1);

        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals(prod.getNombre(), resultado.getProducto().getNombre());
        verify(productoRepository, times(1)).findById(5L);
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    @Test
    void reducirInventario_insuficiente_lanzaExcepcion() {
        Producto prod = new Producto();
        prod.setId(6L);
        prod.setNombre("Cookie");

        Inventario inv = new Inventario();
        inv.setId(20L);
        inv.setProducto(prod);
        inv.setCantidadActual(1);
        inv.setCantidadMinima(1);

        when(inventarioRepository.findByProductoId(6L)).thenReturn(Optional.of(inv));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> inventarioService.reducirInventario(6L, 5));
        assertTrue(ex.getMessage().contains("Stock insuficiente"));
        verify(inventarioRepository, times(1)).findByProductoId(6L);
    }

    @Test
    void obtenerInventariosBajoStock_devuelveLista() {
        Inventario inv1 = new Inventario();
        inv1.setId(30L);
        inv1.setCantidadActual(2);
        inv1.setCantidadMinima(5);

        Inventario inv2 = new Inventario();
        inv2.setId(31L);
        inv2.setCantidadActual(10);
        inv2.setCantidadMinima(5);

        when(inventarioRepository.findAll()).thenReturn(List.of(inv1, inv2));

        var resultado = inventarioService.obtenerInventariosBajoStock();

        assertEquals(1, resultado.size());
        assertEquals(30L, resultado.get(0).getId());
        verify(inventarioRepository, times(1)).findAll();
    }
}
