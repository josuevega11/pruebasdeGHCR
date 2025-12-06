package com.UTP.PasteleriaAurora.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos_personalizados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoPersonalizado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descripcionPersonalizacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado = Estado.PENDIENTE;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;

    public enum Estado {
        PENDIENTE,
        ACEPTADO,
        RECHAZADO
    }
}
