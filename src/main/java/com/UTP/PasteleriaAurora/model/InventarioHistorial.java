package com.UTP.PasteleriaAurora.model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "inventario_historial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioHistorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventario_id", nullable = false)
    private Inventario inventario;

    private Integer cantidadActualAnterior;
    private Integer cantidadMinimaAnterior;
    private LocalDateTime fechaCambio;

    private LocalDateTime fechaRegistro; // Fecha cuando se guardó este estado

    @Transient
    private String fechaCambioFormateada;

    @Transient
    private String fechaRegistroFormateada;

    public String getFechaCambioFormateada() {
    return fechaCambio != null ? fechaCambio.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) : "Sin fecha";
}

public String getFechaRegistroFormateada() {
    return fechaRegistro != null ? fechaRegistro.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) : "Sin fecha";
}

}
