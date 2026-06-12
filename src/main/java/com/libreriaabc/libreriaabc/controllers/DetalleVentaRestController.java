package com.libreriaabc.libreriaabc.controllers;

import com.libreriaabc.libreriaabc.entities.DetalleVenta;
import com.libreriaabc.libreriaabc.repositories.DetalleVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles-ventas")
public class DetalleVentaRestController {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    // 1. LISTAR (GET)
    @GetMapping
    public List<DetalleVenta> listarDetalles() {
        return detalleVentaRepository.findAll();
    }

    // 2. CREAR / AGREGAR ITEM AL CARRITO (POST)
    @PostMapping
    public DetalleVenta guardarDetalle(@RequestBody DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }

    // 3. ACTUALIZAR CANTIDAD O PRECIO DEL DETALLE (PUT)
    @PutMapping("/{id}")
    public DetalleVenta actualizarDetalle(@PathVariable Integer id, @RequestBody DetalleVenta nuevoDetalle) {
        return detalleVentaRepository.findById(id)
                .map(detalle -> {
                    detalle.setCantidad(nuevoDetalle.getCantidad());
                    detalle.setPrecioUnitario(nuevoDetalle.getPrecioUnitario());
                    // Si tu entidad mapea los objetos Venta o Producto completos, se actualizarían aquí
                    return detalleVentaRepository.save(detalle);
                }).orElseGet(() -> {
                    return null; // Si el ID no existe, no hace nada
                });
    }

    // 4. ELIMINAR UN ITEM DEL DETALLE (DELETE)
    @DeleteMapping("/{id}")
    public String eliminarDetalle(@PathVariable Integer id) {
        if (detalleVentaRepository.existsById(id)) {
            detalleVentaRepository.deleteById(id);
            return "Item del detalle eliminado con éxito.";
        }
        return "El detalle con ID " + id + " no existe.";
    }
}