package org.kobra.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.kobra.adapter.dto.ProductRequest;
import org.kobra.application.usecase.CreateProductUseCase;
import org.kobra.application.usecase.FindProductUseCase;
import org.kobra.application.usecase.GetAllProductUseCase;
import org.kobra.domain.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateProductUseCase createProductUseCase;

    @MockBean
    private FindProductUseCase findProductUseCase;

    @MockBean
    private GetAllProductUseCase getAllProductUseCase;

    @Test
    void shouldCreateProductAndReturn200() throws Exception {
        UUID id = UUID.randomUUID();
        ProductRequest request = new ProductRequest("Notebook", new BigDecimal("3500.00"));
        Product created = new Product(id, "Notebook", new BigDecimal("3500.00"));

        when(createProductUseCase.execute(any(Product.class))).thenReturn(created);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Notebook"))
                .andExpect(jsonPath("$.price").value(3500.00));
    }

    @Test
    void shouldReturnBadRequestWhenNameIsBlank() throws Exception {
        ProductRequest request = new ProductRequest("", new BigDecimal("3500.00"));

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenPriceIsNegative() throws Exception {
        ProductRequest request = new ProductRequest("Notebook", new BigDecimal("-1.00"));

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldFindProductByIdAndReturn200() throws Exception {
        UUID id = UUID.randomUUID();
        Product product = new Product(id, "Notebook", new BigDecimal("3500.00"));

        when(findProductUseCase.execute(id)).thenReturn(product);

        mockMvc.perform(get("/products/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Notebook"));
    }

    @Test
    void shouldGetAllProductsAndReturn200() throws Exception {
        UUID id = UUID.randomUUID();
        List<Product> products = new ArrayList<>();

        products.add(new Product(id, "Notebook", new BigDecimal("3500.00")));

        when(getAllProductUseCase.execute()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(id.toString()))
                .andExpect(jsonPath("$.[0].name").value("Notebook"));
    }
}
