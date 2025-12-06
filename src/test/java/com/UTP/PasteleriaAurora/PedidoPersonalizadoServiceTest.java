package com.UTP.PasteleriaAurora;

import com.UTP.PasteleriaAurora.model.PedidoPersonalizado;
import com.UTP.PasteleriaAurora.model.Usuario;
import com.UTP.PasteleriaAurora.repository.PedidoPersonalizadoRepository;
import com.UTP.PasteleriaAurora.service.PedidoPersonalizadoService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoPersonalizadoServiceTest {

    @Mock
    private PedidoPersonalizadoRepository pedidoRepository;

    @InjectMocks
    private PedidoPersonalizadoService service;

    public PedidoPersonalizadoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardarPedido_debeGuardarCorrectamente() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        PedidoPersonalizado pedido = new PedidoPersonalizado();
        pedido.setUsuario(usuario);
        pedido.setDescripcionPersonalizacion("Pastel de chocolate");

        // Simulación del repository
        when(pedidoRepository.save(any(PedidoPersonalizado.class))).thenAnswer(invocation -> {
            PedidoPersonalizado p = invocation.getArgument(0);
            return p; // devuelve el mismo objeto que el servicio envía
        });

        PedidoPersonalizado resultado = service.guardarPedido(pedido);

        assertNotNull(resultado);
        assertNotNull(resultado.getFechaCreacion());
        assertNotNull(resultado.getFechaActualizacion());
        assertEquals("Pastel de chocolate", resultado.getDescripcionPersonalizacion());

        verify(pedidoRepository, times(1)).save(any(PedidoPersonalizado.class));
    }
}
