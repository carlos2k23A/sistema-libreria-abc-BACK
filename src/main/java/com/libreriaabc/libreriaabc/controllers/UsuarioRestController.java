package com.libreriaabc.libreriaabc.controllers;

import com.libreriaabc.libreriaabc.entities.Usuario;
import com.libreriaabc.libreriaabc.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 1. LISTAR (GET)
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // 2. CREAR (POST)
    @PostMapping
    public Usuario guardarUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // 3. ACTUALIZAR CONTRASEÑA O ROL (PUT)
    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario nuevoUsuario) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setUsername(nuevoUsuario.getUsername());
                    usuario.setPasswordHash(nuevoUsuario.getPasswordHash()); // Aquí se guardaría la nueva clave
                    usuario.setRol(nuevoUsuario.getRol());
                    return usuarioRepository.save(usuario);
                }).orElseGet(() -> {
                    return null; // Si el ID no existe, no hace nada
                });
    }

    // 4. ELIMINAR ACCESO (DELETE)
    @DeleteMapping("/{id}")
    public String eliminarUsuario(@PathVariable Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return "Usuario eliminado con éxito del sistema.";
        }
        return "El usuario con ID " + id + " no existe.";
    }
}