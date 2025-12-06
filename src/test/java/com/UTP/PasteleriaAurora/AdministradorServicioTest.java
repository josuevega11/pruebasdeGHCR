package com.UTP.PasteleriaAurora;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.UTP.PasteleriaAurora.service.AdministradorServicio;

public class AdministradorServicioTest {

    private final AdministradorServicio servicio = new AdministradorServicio();

    @Test
    void testRolAdministrador() {
        assertEquals("ADMIN", servicio.obtenerRol());
    }

    @Test
    void testSumarUsuarios() {
        assertEquals(7, servicio.sumarUsuarios(3, 4));
    }

    @Test
    void testSumarUsuarios_conNegativos() {
        assertEquals(3, servicio.sumarUsuarios(5, -2));
    }
}