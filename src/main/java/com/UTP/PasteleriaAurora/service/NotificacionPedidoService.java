package com.UTP.PasteleriaAurora.service;

import com.UTP.PasteleriaAurora.model.NotificacionPedido;
import com.UTP.PasteleriaAurora.model.Usuario;
import com.UTP.PasteleriaAurora.repository.NotificacionPedidoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificacionPedidoService {
    private final NotificacionPedidoRepository repo;

    public NotificacionPedidoService(NotificacionPedidoRepository repo) {
        this.repo = repo;
    }

    public void crearNotificacion(NotificacionPedido notificacion) {
        repo.save(notificacion);
    }

    public List<NotificacionPedido> obtenerNoLeidas(Usuario usuario) {
        return repo.findByUsuarioAndLeidoFalse(usuario);
    }

    public void marcarComoLeida(Long id) {
        repo.findById(id).ifPresent(n -> { n.setLeido(true); repo.save(n); });
    }
}