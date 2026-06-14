package com.libreriaabc.libreriaabc.controllers;

import com.libreriaabc.libreriaabc.entities.DetalleVenta;
import com.libreriaabc.libreriaabc.entities.Venta;
import com.libreriaabc.libreriaabc.services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class TestRestController {

    @Autowired
    private VentaService ventaService;

    @PostMapping("/procesar")
    public Venta procesarNuevaVenta(@RequestBody VentaRestController.RequestVentaCompleta request) {

        System.out.println("REQUEST RECIBIDO:");
        System.out.println("venta="+request.getVenta());
        System.out.println("detalle="+request.getDetalles());


        System.out.println("CLIENTE = " +
                request.getVenta().getCliente_id().getCliente_id());

        System.out.println("USUARIO = " +
                request.getVenta().getUsuario_id().getId_usuario());

        System.out.println("TIPO DOC = " +
                request.getVenta().getTipo_documento());

        for (DetalleVenta d : request.getDetalles()) {

            System.out.println("PRODUCTO = " +
                    d.getProducto_id().getProducto_id());

            System.out.println("CANTIDAD = " +
                    d.getCantidad());

            System.out.println("PRECIO = " +
                    d.getPrecio_unitario());
        }





        return null;
    }

}
