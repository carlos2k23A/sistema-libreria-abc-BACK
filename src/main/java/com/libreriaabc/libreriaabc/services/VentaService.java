package com.libreriaabc.libreriaabc.services;

import com.libreriaabc.libreriaabc.entities.DetalleVenta;
import com.libreriaabc.libreriaabc.entities.Producto;
import com.libreriaabc.libreriaabc.entities.Venta;
import com.libreriaabc.libreriaabc.repositories.DetalleVentaRepository;
import com.libreriaabc.libreriaabc.repositories.ProductoRepository;
import com.libreriaabc.libreriaabc.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    // Registrar una venta procesando el stock automáticamente
    @Transactional
    public Venta procesarVenta(Venta venta, List<DetalleVenta> detalles) {
        // 1. Guardar la cabecera de la venta
        Venta ventaGuardada = ventaRepository.save(venta);

        // 2. Procesar cada artículo del carrito
        for (DetalleVenta detalle : detalles) {
            // Buscar el producto en la base de datos para actualizar su stock
            Producto producto = productoRepository.findById(detalle.getProducto().getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Validar si hay stock suficiente
            if (producto.getStockActual() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // Restar el stock en caliente
            producto.setStockActual(producto.getStockActual() - detalle.getCantidad());
            productoRepository.save(producto);

            // Vincular el detalle con la venta que acabamos de crear
            detalle.setVenta(ventaGuardada);
            detalleVentaRepository.save(detalle);
        }

        return ventaGuardada;
    }
}