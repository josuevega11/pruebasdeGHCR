package com.UTP.PasteleriaAurora.repository;

import com.UTP.PasteleriaAurora.model.CarritoItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {
    List<CarritoItem> findByUsuarioId(Long usuarioId);
}