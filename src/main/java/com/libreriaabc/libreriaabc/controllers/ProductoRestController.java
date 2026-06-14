package com.libreriaabc.libreriaabc.controllers;

import com.libreriaabc.libreriaabc.entities.Cliente;
import com.libreriaabc.libreriaabc.entities.Producto;
import com.libreriaabc.libreriaabc.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoRestController {

    @Autowired
    private ProductoRepository productoRepository;

    // 1. READ (Listar todos) -> GET
    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // 2. CREATE (Insertar nuevo) -> POST
    /*@PostMapping
    public Producto guardarProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }*/


    @PostMapping
    public ResponseEntity<?> guardarProducto(@RequestBody Producto producto) {

        Map<String, Object> response = new HashMap<>();

        try {

            Producto productGuardado = productoRepository.save(producto);

            response.put("success", true);
            response.put("message", "Producto registrado correctamente");
            response.put("cliente", productGuardado);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.put("success", false);
            response.put("message", "No se pudo registrar el producto");
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // 3. UPDATE (Actualizar uno existente) -> PUT
    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable Integer id, @RequestBody Producto nuevoProducto) {
        return productoRepository.findById(id)
                .map(producto -> {
                    producto.setNombre(nuevoProducto.getNombre());
                    producto.setDescripcion(nuevoProducto.getDescripcion());
                    producto.setPrecio(nuevoProducto.getPrecio());
                    producto.setStock_actual(nuevoProducto.getStock_actual());
                    return productoRepository.save(producto);
                }).orElseGet(() -> {
                    return null; // Si no existe el ID, no hace nada
                });
    }

    // 4. DELETE (Eliminar por ID) -> DELETE
    @DeleteMapping("/{id}")
    public String eliminarProducto(@PathVariable Integer id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return "Producto eliminado con éxito de MySQL.";
        }
        return "El producto con ID " + id + " no existe.";
    }
}