package com.libreriaabc.libreriaabc.controllers;

import com.libreriaabc.libreriaabc.entities.Categoria;
import com.libreriaabc.libreriaabc.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaRestController {

@Autowired
    private CategoriaRepository categoriaRepository;

@GetMapping
    public List<Categoria> getCategorias(){return  categoriaRepository.findAll();}

}
