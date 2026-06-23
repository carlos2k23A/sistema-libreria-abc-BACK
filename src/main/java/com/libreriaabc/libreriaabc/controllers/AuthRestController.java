package com.libreriaabc.libreriaabc.controllers;

import com.libreriaabc.libreriaabc.dto.loginRequest;
import com.libreriaabc.libreriaabc.dto.loginResponse;
import com.libreriaabc.libreriaabc.entities.Usuario;
import com.libreriaabc.libreriaabc.repositories.UsuarioRepository;
import com.libreriaabc.libreriaabc.util.EncriptaCod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthRestController
{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody loginRequest loginRequest)
    {
        System.out.println("Iniciando login"+loginRequest);

        Map<String, Object> response = new HashMap<>();

        Optional<Usuario> usuarioOpt = Optional.ofNullable(usuarioRepository.findByUsername(loginRequest.getUsername()));
        System.out.println("usuario:"+usuarioOpt);

        if (usuarioOpt.isEmpty()) {

            response.put("success", false);
            response.put("message", "Usuario no encontrado");

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }

        Usuario usuario = usuarioOpt.get();


        if (!usuario.getPassword_hash()
                .equals(loginRequest.getClave())) {

            response.put("success", false);
            response.put("message", "Contraseña incorrecta");

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }

        response.put("success", true);
        response.put("message", "Acceso correcto");



        String idEncriptado = EncriptaCod.encrypt(String.valueOf(usuario.getId_usuario()));
        System.out.println(idEncriptado);


        response.put("usuario", Map.of(
                "id_usuario", idEncriptado,
                "username", usuario.getUsername(),
                "rol", usuario.getRol()
        ));

        return ResponseEntity.ok(response);

    }


}

