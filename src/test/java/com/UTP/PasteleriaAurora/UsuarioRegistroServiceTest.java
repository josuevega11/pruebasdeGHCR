package com.UTP.PasteleriaAurora;

import com.UTP.PasteleriaAurora.model.Usuario;
import com.UTP.PasteleriaAurora.repository.UsuarioRepository;
import com.UTP.PasteleriaAurora.service.UsuarioService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioRegistroServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    public UsuarioRegistroServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarUsuario_debeGuardarCorrectamente() {

        // Usuario sin encriptar
        Usuario usuario = new Usuario();
        usuario.setUsername("gerardo");
        usuario.setPassword("123456");
        usuario.setNombreCompleto("Gerardo De Paz");
        usuario.setEmail("gerardo@gmail.com");
        usuario.setRol(Usuario.Rol.USUARIO);

        // Simular encriptación
        when(passwordEncoder.encode("123456")).thenReturn("encrypt-123456");

        // Simular guardado
        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Usuario resultado = usuarioService.guardarUsuario(usuario);

        assertNotNull(resultado);
        assertEquals("gerardo", resultado.getUsername());
        assertEquals("encrypt-123456", resultado.getPassword());
        assertEquals("Gerardo De Paz", resultado.getNombreCompleto());
        assertEquals("gerardo@gmail.com", resultado.getEmail());
        assertEquals(Usuario.Rol.USUARIO, resultado.getRol());

        verify(passwordEncoder, times(1)).encode("123456");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }
}
