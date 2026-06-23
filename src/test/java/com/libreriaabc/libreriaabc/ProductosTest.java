package com.libreriaabc.libreriaabc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libreriaabc.libreriaabc.entities.Categoria;
import com.libreriaabc.libreriaabc.entities.Producto;
import com.libreriaabc.libreriaabc.repositories.CategoriaRepository;
import com.libreriaabc.libreriaabc.repositories.ProductoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@Transactional


public class ProductosTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Convierte objetos Java a JSON

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    private Categoria crearCategoriaBase() {
        Categoria cat = new Categoria();
        cat.setNombre_categoria("Libros Académicos"); // Ajusta según los campos reales de tu entidad Categoria
        return categoriaRepository.save(cat);
    }


    @Test
    public void testInsertarProductoExitoso() throws Exception {
        // 1. Preparar los datos (necesitamos una categoría primero porque es obligatoria)
        Categoria categoria = crearCategoriaBase();

        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre("Cálculo Vectorial");
        nuevoProducto.setDescripcion("Libro de matemáticas avanzadas");
        nuevoProducto.setPrecio(new BigDecimal("89.90"));
        nuevoProducto.setStock_actual(20);
        nuevoProducto.setStock_minimo(5);
        nuevoProducto.setCategoria_id(categoria); // Relación ManyToOne


        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevoProducto)))

                // 3. Validar las respuestas esperadas
                .andExpect(status().isCreated()) // Esperamos un HTTP 201 Created
                .andExpect(jsonPath("$.producto_id").exists()) // Que nos devuelva el ID autogenerado
                .andExpect(jsonPath("$.nombre").value("Cálculo Vectorial"));
    }

    @Test
    public void testBuscarProductoPorId() throws Exception {
        // 1. Preparar: Insertamos directamente un producto en la base de datos de pruebas para poder buscarlo
        Categoria categoria = crearCategoriaBase();
        Producto prod = new Producto();
        prod.setNombre("Álgebra Lineal");
        prod.setPrecio(new BigDecimal("75.00"));
        prod.setStock_actual(10);
        prod.setCategoria_id(categoria);
        prod = productoRepository.save(prod); // Guardado directo

        // 2. Ejecutar la acción simulando un GET al endpoint por ID
        mockMvc.perform(get("/api/productos/" + prod.getProducto_id())
                        .accept(MediaType.APPLICATION_JSON))

                // 3. Validar que lo encuentre correctamente
                .andExpect(status().isOk()) // Esperamos un HTTP 200 OK
                .andExpect(jsonPath("$.nombre").value("Álgebra Lineal"))
                .andExpect(jsonPath("$.precio").value(75.00));
    }



}
