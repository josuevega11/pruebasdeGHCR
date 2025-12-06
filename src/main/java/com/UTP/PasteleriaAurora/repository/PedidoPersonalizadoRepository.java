package com.UTP.PasteleriaAurora.repository;

import com.UTP.PasteleriaAurora.model.PedidoPersonalizado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoPersonalizadoRepository extends JpaRepository<PedidoPersonalizado, Long> {
    List<PedidoPersonalizado> findByUsuarioId(Long usuarioId);
}

