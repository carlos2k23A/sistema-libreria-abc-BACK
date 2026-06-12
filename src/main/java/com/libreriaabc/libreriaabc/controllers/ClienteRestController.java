package com.libreriaabc.libreriaabc.controllers;

import com.libreriaabc.libreriaabc.entities.Cliente;
import com.libreriaabc.libreriaabc.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
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
    public Cliente guardarCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // 3. ACTUALIZAR DATOS DE CLIENTE (PUT)
    @PutMapping("/{id}")
    public Cliente actualizarCliente(@PathVariable Integer id, @RequestBody Cliente nuevoCliente) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNombreCompleto(nuevoCliente.getNombreCompleto());
                    cliente.setDocumentoIdentidad(nuevoCliente.getDocumentoIdentidad());
                    cliente.setTelefono(nuevoCliente.getTelefono());
                    cliente.setDireccion(nuevoCliente.getDireccion());
                    return clienteRepository.save(cliente);
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