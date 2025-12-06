package com.UTP.PasteleriaAurora.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    private Integer cantidadActual = 0;

    private Integer cantidadMinima = 0;

    private LocalDateTime fechaActualizacion;

    @Transient
    private String fechaFormateada;
}