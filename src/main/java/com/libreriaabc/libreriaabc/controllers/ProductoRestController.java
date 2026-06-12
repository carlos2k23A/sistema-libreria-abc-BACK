package com.libreriaabc.libreriaabc.controllers;

import com.libreriaabc.libreriaabc.entities.Producto;
import com.libreriaabc.libreriaabc.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

    @Autowired
    private ProductoRepository productoRepository;

    // 1. READ (Listar todos) -> GET
    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // 2. CREATE (Insertar nuevo) -> POST
    @PostMapping
    public Producto guardarProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    // 3. UPDATE (Actualizar uno existente) -> PUT
    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable Integer id, @RequestBody Producto nuevoProducto) {
        return productoRepository.findById(id)
                .map(producto -> {
                    producto.setNombre(nuevoProducto.getNombre());
                    producto.setDescripcion(nuevoProducto.getDescripcion());
                    producto.setPrecio(nuevoProducto.getPrecio());
                    producto.setStockActual(nuevoProducto.getStockActual());
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