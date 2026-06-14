package com.libreriaabc.libreriaabc.controllers;

import com.libreriaabc.libreriaabc.entities.DetalleVenta;
import com.libreriaabc.libreriaabc.entities.Usuario;
import com.libreriaabc.libreriaabc.entities.Venta;
import com.libreriaabc.libreriaabc.services.VentaService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaRestController {

    @Autowired
    private VentaService ventaService;

    // Estructura auxiliar para recibir la Venta junto con su Carrito de compras desde el Front/Postman
    @Data
    public static class RequestVentaCompleta {
        public Venta venta;
        public List<DetalleVenta> detalles;
    }

    // Registrar una venta procesando y descontando stock en caliente (POST)
    /*@PostMapping("/procesar")
    public Venta procesarNuevaVenta(@RequestBody RequestVentaCompleta request) {
        return ventaService.procesarVenta(request.venta, request.detalles);
    }*/


    @PostMapping("/procesar")
    public ResponseEntity<?> procesarNuevaVenta(
            @RequestBody RequestVentaCompleta request) {

        Map<String, Object> response = new HashMap<>();

        try {

            Venta venta = ventaService.procesarVenta(
                    request.getVenta(),
                    request.getDetalles()
            );

            response.put("success", true);
            response.put("message", "Venta registrada correctamente");
            response.put("venta", venta);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.put("success", false);
            response.put("message", "Error al registrar la venta");
            response.put("error", e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }
}