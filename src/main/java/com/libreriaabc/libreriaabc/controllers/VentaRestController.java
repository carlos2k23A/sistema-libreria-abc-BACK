package com.libreriaabc.libreriaabc.controllers;

import com.libreriaabc.libreriaabc.entities.DetalleVenta;
import com.libreriaabc.libreriaabc.entities.Venta;
import com.libreriaabc.libreriaabc.services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaRestController {

    @Autowired
    private VentaService ventaService;

    // Estructura auxiliar para recibir la Venta junto con su Carrito de compras desde el Front/Postman
    public static class RequestVentaCompleta {
        public Venta venta;
        public List<DetalleVenta> detalles;
    }

    // Registrar una venta procesando y descontando stock en caliente (POST)
    @PostMapping("/procesar")
    public Venta procesarNuevaVenta(@RequestBody RequestVentaCompleta request) {
        return ventaService.procesarVenta(request.venta, request.detalles);
    }
}