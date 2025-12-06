package com.UTP.PasteleriaAurora.service;

import com.UTP.PasteleriaAurora.model.PedidoPersonalizado;
import com.UTP.PasteleriaAurora.repository.PedidoPersonalizadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoPersonalizadoService {

    @Autowired
    private PedidoPersonalizadoRepository pedidoRepository;

    public List<PedidoPersonalizado> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public List<PedidoPersonalizado> listarPedidosPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }

    public Optional<PedidoPersonalizado> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public PedidoPersonalizado guardarPedido(PedidoPersonalizado pedido) {
        if (pedido.getFechaCreacion() == null) {
            pedido.setFechaCreacion(LocalDateTime.now());
        }
        pedido.setFechaActualizacion(LocalDateTime.now());
        return pedidoRepository.save(pedido);
    }

    public void eliminarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}
