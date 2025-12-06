package com.UTP.PasteleriaAurora.repository;

import com.UTP.PasteleriaAurora.model.NotificacionPedido;
import com.UTP.PasteleriaAurora.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificacionPedidoRepository extends JpaRepository<NotificacionPedido, Long> {
    List<NotificacionPedido> findByUsuarioAndLeidoFalse(Usuario usuario);
}