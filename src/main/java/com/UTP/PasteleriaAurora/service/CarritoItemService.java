package com.UTP.PasteleriaAurora.service;

import com.UTP.PasteleriaAurora.model.CarritoItem;
import com.UTP.PasteleriaAurora.repository.CarritoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoItemService {

    @Autowired
    private CarritoItemRepository carritoItemRepository;

    public List<CarritoItem> listarItemsPorUsuario(Long usuarioId) {
        return carritoItemRepository.findByUsuarioId(usuarioId);
    }

    public Optional<CarritoItem> buscarPorId(Long id) {
        return carritoItemRepository.findById(id);
    }

    public CarritoItem guardarItem(CarritoItem item) {
        return carritoItemRepository.save(item);
    }

    public void eliminarItem(Long id) {
        carritoItemRepository.deleteById(id);
    }
}
