package com.libreriaabc.libreriaabc.repositories;

import com.libreriaabc.libreriaabc.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}