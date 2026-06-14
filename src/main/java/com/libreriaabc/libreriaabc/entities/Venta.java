package com.libreriaabc.libreriaabc.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Ventas")
@Getter
@Setter
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venta_id")
    private Integer venta_id;

    @Column(name = "fecha_venta", updatable = false)
    private LocalDateTime fecha_venta = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente_id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario_id;

    @Column(name = "tipo_documento", nullable = false, length = 20)
    private String tipo_documento;

    @Column(nullable = false, length = 4)
    private String serie;

    @Column(nullable = false, length = 8)
    private String numero;

    @Column(name = "forma_de_pago", nullable = false, length = 30)
    private String forma_de_pago;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal total;
}