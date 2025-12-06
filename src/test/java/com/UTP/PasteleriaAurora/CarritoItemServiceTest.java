package com.UTP.PasteleriaAurora;

import com.UTP.PasteleriaAurora.model.CarritoItem;
import com.UTP.PasteleriaAurora.model.Producto;
import com.UTP.PasteleriaAurora.model.Usuario;
import com.UTP.PasteleriaAurora.repository.CarritoItemRepository;
import com.UTP.PasteleriaAurora.service.CarritoItemService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarritoItemServiceTest {

    @Mock
    private CarritoItemRepository carritoItemRepository;

    @InjectMocks
    private CarritoItemService carritoItemService;

    public CarritoItemServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardarItem_debeGuardarCorrectamente() {

        // Usuario
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        // Producto
        Producto producto = new Producto();
        producto.setId(10L);

        // Item
        CarritoItem item = new CarritoItem();
        item.setUsuario(usuario);
        item.setProducto(producto);
        item.setCantidad(2);

        // Simular repository
        when(carritoItemRepository.save(any(CarritoItem.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CarritoItem resultado = carritoItemService.guardarItem(item);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getUsuario().getId());
        assertEquals(10L, resultado.getProducto().getId());
        assertEquals(2, resultado.getCantidad());

        verify(carritoItemRepository, times(1)).save(any(CarritoItem.class));
    }
}
