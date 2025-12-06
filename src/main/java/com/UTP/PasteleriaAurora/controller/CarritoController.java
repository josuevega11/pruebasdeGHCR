package com.UTP.PasteleriaAurora.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.UTP.PasteleriaAurora.model.CarritoItem;
import com.UTP.PasteleriaAurora.model.Producto;
import com.UTP.PasteleriaAurora.model.Usuario;
import com.UTP.PasteleriaAurora.service.CarritoItemService;
import com.UTP.PasteleriaAurora.service.InventarioService;
import com.UTP.PasteleriaAurora.service.ProductoService;
import com.UTP.PasteleriaAurora.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carrito")
public class CarritoController {

    private final CarritoItemService carritoItemService;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final InventarioService inventarioService;

    @GetMapping
    public String verCarrito(Model model, Principal principal) {
        Usuario usuario = usuarioService.buscarPorUsername(principal.getName()).orElseThrow();
        List<CarritoItem> items = carritoItemService.listarItemsPorUsuario(usuario.getId());
        model.addAttribute("items", items);
        return "usuario/carrito";
    }

    @PostMapping("/agregar")
    public String agregarAlCarrito(@RequestParam Long productoId, Principal principal) {
        Usuario usuario = usuarioService.buscarPorUsername(principal.getName()).orElseThrow();
        Producto producto = productoService.buscarPorId(productoId).orElseThrow();
        List<CarritoItem> items = carritoItemService.listarItemsPorUsuario(usuario.getId());
        CarritoItem existente = items.stream()
                .filter(i -> i.getProducto().getId().equals(productoId))
                .findFirst()
                .orElse(null);
        if (existente != null) {
            existente.setCantidad(existente.getCantidad() + 1);
            carritoItemService.guardarItem(existente);
        } else {
            CarritoItem item = new CarritoItem();
            item.setUsuario(usuario);
            item.setProducto(producto);
            item.setCantidad(1);
            carritoItemService.guardarItem(item);
        }
        return "redirect:/catalogo";
    }

    @PostMapping("/comprar")
    public String comprar(Principal principal, Model model, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            redirectAttributes.addFlashAttribute("mensaje", "Debe iniciar sesión para comprar.");
            return "redirect:/carrito";
        }
        Usuario usuario = usuarioService.buscarPorUsername(principal.getName()).orElse(null);
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("mensaje", "Usuario no encontrado.");
            return "redirect:/carrito";
        }
        List<CarritoItem> items = carritoItemService.listarItemsPorUsuario(usuario.getId());
        if (items == null || items.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensaje", "El carrito está vacío.");
            return "redirect:/carrito";
        }
        try {
            for (CarritoItem item : items) {
                inventarioService.reducirInventario(item.getProducto().getId(), item.getCantidad());
                carritoItemService.eliminarItem(item.getId());
            }
            redirectAttributes.addFlashAttribute("mensaje", "¡Compra realizada con éxito!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al procesar la compra: " + e.getMessage());
        }
        return "redirect:/carrito";
    }

    @PostMapping("/quitar/{id}")
    public String quitarDelCarrito(@PathVariable Long id, Principal principal) {
        carritoItemService.eliminarItem(id);
        return "redirect:/carrito";
    }

}