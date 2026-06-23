package com.libreriaabc.libreriaabc.controllers;

import com.libreriaabc.libreriaabc.entities.Cliente;
import com.libreriaabc.libreriaabc.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteRestController {

    @Autowired
    private ClienteRepository clienteRepository;

    // 1. LISTAR TODOS (GET)
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    // 2. REGISTRAR CLIENTE (POST)
    @PostMapping
    public ResponseEntity<?> guardarCliente(@RequestBody Cliente cliente) {

        Map<String, Object> response = new HashMap<>();

        try {

            Cliente clienteGuardado = clienteRepository.save(cliente);

            response.put("success", true);
            response.put("message", "Cliente registrado correctamente");
            response.put("cliente", clienteGuardado);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.put("success", false);
            response.put("message", "No se pudo registrar el cliente");
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // 3. ACTUALIZAR DATOS DE CLIENTE (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<?>  actualizarCliente(@PathVariable Integer id, @RequestBody Cliente nuevoCliente) {


        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNombre_completo(nuevoCliente.getNombre_completo());
                    cliente.setDocumento_identidad(nuevoCliente.getDocumento_identidad()    );
                    cliente.setTelefono(nuevoCliente.getTelefono());
                    cliente.setDireccion(nuevoCliente.getDireccion());
                  //  return clienteRepository.save(cliente);
                    Map<String, Object> response = new HashMap<>();
                    try {

                        Cliente clienteGuardado = clienteRepository.save(cliente);

                        response.put("success", true);
                        response.put("message", "Cliente actualizado correctamente");
                        response.put("cliente", clienteGuardado);

                        return ResponseEntity.ok(response);

                    } catch (Exception e) {

                        response.put("success", false);
                        response.put("message", "No se pudo actualizar el cliente");
                        response.put("error", e.getMessage());

                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                    }

                }).orElseGet(() -> {
                    return null; // Si el ID no existe, no hace nada
                });
    }

    // 4. ELIMINAR CLIENTE (DELETE)
    @DeleteMapping("/{id}")
    public String eliminarCliente(@PathVariable Integer id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return "Cliente eliminado con éxito de la base de datos.";
        }
        return "El cliente con ID " + id + " no existe.";
    }
}