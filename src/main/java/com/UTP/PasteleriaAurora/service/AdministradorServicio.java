package com.UTP.PasteleriaAurora.service;

import org.springframework.stereotype.Service;

@Service
public class AdministradorServicio {

    public String obtenerRol() {
        return "ADMIN";
    }

    public int sumarUsuarios(int a, int b) {
        return a + b;
    }
}