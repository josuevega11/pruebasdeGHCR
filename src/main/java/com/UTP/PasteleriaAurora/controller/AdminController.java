package com.UTP.PasteleriaAurora.controller;


import com.UTP.PasteleriaAurora.model.PedidoPersonalizado;
import com.UTP.PasteleriaAurora.model.Inventario;
import com.UTP.PasteleriaAurora.model.InventarioHistorial;
import com.UTP.PasteleriaAurora.model.NotificacionPedido;
import com.UTP.PasteleriaAurora.service.PedidoPersonalizadoService;
import com.UTP.PasteleriaAurora.service.ProductoService;
import com.UTP.PasteleriaAurora.service.UsuarioService;
import com.UTP.PasteleriaAurora.service.InventarioHistorialService;
import com.UTP.PasteleriaAurora.service.InventarioService;
import com.UTP.PasteleriaAurora.service.NotificacionPedidoService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final PedidoPersonalizadoService pedidoService;
    private final NotificacionPedidoService notificacionPedidoService;
    private final InventarioService inventarioService;
    private final InventarioHistorialService inventarioHistorialService;

    @GetMapping("/productos")
    public String productos(Model model) {
        model.addAttribute("producto", new com.UTP.PasteleriaAurora.model.Producto());
        model.addAttribute("productos", productoService.listarProductos());
        return "admin/productos";
    }

    @PostMapping("/productos")
    public String guardarProducto(@ModelAttribute com.UTP.PasteleriaAurora.model.Producto producto) {
        productoService.guardarProducto(producto);
        return "redirect:/admin/productos";
    }

    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        return "admin/usuarios";
    }

    @GetMapping("/pedidos")
    public String pedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.listarPedidos());
        return "admin/pedidos";
    }

    @PostMapping("/pedidos/aceptar/{id}")
    public String aceptarPedido(@PathVariable Long id) {
        PedidoPersonalizado pedido = pedidoService.buscarPorId(id).orElseThrow();
        pedido.setEstado(PedidoPersonalizado.Estado.ACEPTADO);
        pedidoService.guardarPedido(pedido);

        NotificacionPedido noti = new NotificacionPedido();
        noti.setPedido(pedido);
        noti.setUsuario(pedido.getUsuario());
        noti.setEstado(NotificacionPedido.Estado.ACEPTADO);
        noti.setMensaje("¡Tu pedido personalizado fue ACEPTADO!");
        notificacionPedidoService.crearNotificacion(noti);

        return "redirect:/admin/pedidos";
    }

    @PostMapping("/pedidos/rechazar/{id}")
    public String rechazarPedido(@PathVariable Long id) {
        PedidoPersonalizado pedido = pedidoService.buscarPorId(id).orElseThrow();
        pedido.setEstado(PedidoPersonalizado.Estado.RECHAZADO);
        pedidoService.guardarPedido(pedido);

        NotificacionPedido noti = new NotificacionPedido();
        noti.setPedido(pedido);
        noti.setUsuario(pedido.getUsuario());
        noti.setEstado(NotificacionPedido.Estado.RECHAZADO);
        noti.setMensaje("Tu pedido personalizado fue RECHAZADO.");
        notificacionPedidoService.crearNotificacion(noti);

        return "redirect:/admin/pedidos";
    }

@GetMapping("/inventario")
public String inventario(Model model) {
    List<Inventario> inventario = inventarioService.listarInventarios();
    model.addAttribute("inventario", inventario);
    model.addAttribute("bajoStock", inventarioService.obtenerInventariosBajoStock());
    return "admin/inventario";
}

@PostMapping("/inventario/actualizar")
public String actualizarInventario(
        @RequestParam Long id,
        @RequestParam Integer cantidadActual,
        @RequestParam Integer cantidadMinima) {
    inventarioService.actualizarInventario(id, cantidadActual, cantidadMinima);
    return "redirect:/admin/inventario";
}

@GetMapping("/inventario/historial")
    public String seleccionarInventarioParaHistorial(Model model) {
        List<Inventario> inventarios = inventarioService.listarInventarios();
        model.addAttribute("inventarios", inventarios);
        return "admin/selectInventarioHistorial";
    }

    @PostMapping("/inventario/historial")
    public String mostrarHistorialSeleccionado(@RequestParam Long inventarioId, Model model) {
        List<InventarioHistorial> historial = inventarioHistorialService.listarPorInventarioId(inventarioId);

        // Formatear fechas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        for (InventarioHistorial h : historial) {
            h.setFechaCambioFormateada(
                h.getFechaCambio() != null ? h.getFechaCambio().format(formatter) : "Sin fecha");
            h.setFechaRegistroFormateada(
                h.getFechaRegistro() != null ? h.getFechaRegistro().format(formatter) : "Sin fecha");
        }

        model.addAttribute("historial", historial);
        model.addAttribute("inventarioId", inventarioId);
        return "admin/inventarioHistorial";
    }


}

