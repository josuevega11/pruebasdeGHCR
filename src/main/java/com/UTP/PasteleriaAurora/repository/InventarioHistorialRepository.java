package com.UTP.PasteleriaAurora.repository;

import com.UTP.PasteleriaAurora.model.InventarioHistorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioHistorialRepository extends JpaRepository<InventarioHistorial, Long> {
    List<InventarioHistorial> findByInventarioIdOrderByFechaRegistroDesc(Long inventarioId);
}
