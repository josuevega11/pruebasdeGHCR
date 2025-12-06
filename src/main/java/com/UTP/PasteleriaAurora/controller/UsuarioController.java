package com.UTP.PasteleriaAurora.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.UTP.PasteleriaAurora.model.NotificacionPedido;
import com.UTP.PasteleriaAurora.model.PedidoPersonalizado;
import com.UTP.PasteleriaAurora.model.Producto;
import com.UTP.PasteleriaAurora.model.Usuario;
import com.UTP.PasteleriaAurora.service.NotificacionPedidoService;
import com.UTP.PasteleriaAurora.service.PedidoPersonalizadoService;
import com.UTP.PasteleriaAurora.service.ProductoService;
import com.UTP.PasteleriaAurora.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ProductoService productoService;
    private final PedidoPersonalizadoService pedidoPersonalizadoService;
    private final NotificacionPedidoService notificacionPedidoService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/inicio")
    public String inicio(Model model, Principal principal) {
        if (principal != null) {
            Usuario usuario = usuarioService.buscarPorUsername(principal.getName()).orElse(null);
            if (usuario != null) {
                List<NotificacionPedido> notificaciones = notificacionPedidoService.obtenerNoLeidas(usuario);
                model.addAttribute("notificaciones", notificaciones);
            }
        }
        return "usuario/inicio";
    }

    @GetMapping("/catalogo")
    public String catalogo(Model model) {
        List<Producto> productos = productoService.listarProductos();
        model.addAttribute("productos", productos);
        return "usuario/catalogo";
    }

    @GetMapping("/personalizado")
    public String personalizadoForm(Model model) {
        model.addAttribute("pedidoPersonalizado", new PedidoPersonalizado());
        return "usuario/personalizado";
    }

    @PostMapping("/personalizado")
    public String enviarPedidoPersonalizado(@ModelAttribute PedidoPersonalizado pedido,
            Principal principal) {
        String username = principal.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username).orElseThrow();

        pedido.setUsuario(usuario);
        pedido.setEstado(PedidoPersonalizado.Estado.PENDIENTE);
        pedidoPersonalizadoService.guardarPedido(pedido);

        return "redirect:/inicio";
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "usuario/nosotros";
    }

     @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/registro")
    public String registroForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol(Usuario.Rol.USUARIO);
        usuarioService.guardarUsuario(usuario);
        return "redirect:/login";
    }
    @PostMapping("/notificacion/{id}/leer")
    @ResponseBody
    public String marcarNotificacionLeida(@PathVariable Long id) {
        notificacionPedidoService.marcarComoLeida(id);
        return "ok";
    }
}
