package com.libreriaabc.libreriaabc.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter
@Setter
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Integer cliente_id;

    @Column(name = "nombre_completo", nullable = false, length = 200)
    private String nombre_completo;

    @Column(name = "documento_identidad", nullable = false, unique = true, length = 20)
    private String documento_identidad;

    @Column(length = 20)
    private String telefono;

    @Column(length = 255)
    private String direccion;
}