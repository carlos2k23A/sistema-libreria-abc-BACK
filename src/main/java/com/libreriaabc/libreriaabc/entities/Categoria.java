package com.libreriaabc.libreriaabc.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categorias")
@Getter
@Setter
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private Integer categoria_id;

    @Column(name = "nombre_categoria", nullable = false, length = 100)
    private String nombre_categoria;

    @Column(columnDefinition = "TEXT")
    private String descripcion;
}