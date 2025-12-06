package com.UTP.PasteleriaAurora.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String nombreCompleto;

    private String email;

    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<PedidoPersonalizado> pedidosPersonalizados;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<CarritoItem> carritoItems;

    public enum Rol {
        USUARIO,
        ADMIN
    }
}

