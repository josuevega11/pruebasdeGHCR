package com.UTP.PasteleriaAurora.service;

import com.UTP.PasteleriaAurora.model.InventarioHistorial;
import com.UTP.PasteleriaAurora.repository.InventarioHistorialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventarioHistorialService {

    private final InventarioHistorialRepository inventarioHistorialRepository;

    public List<InventarioHistorial> listarPorInventarioId(Long inventarioId) {
        return inventarioHistorialRepository.findByInventarioIdOrderByFechaRegistroDesc(inventarioId);
    }

    public InventarioHistorial guardar(InventarioHistorial historial) {
        return inventarioHistorialRepository.save(historial);
    }
}