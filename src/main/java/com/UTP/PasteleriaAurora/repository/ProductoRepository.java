package com.UTP.PasteleriaAurora.repository;

import com.UTP.PasteleriaAurora.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
