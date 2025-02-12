package com.tataelxsi.training.ordermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tataelxsi.training.ordermanagement.entity.ProductEntity;
import com.tataelxsi.training.ordermanagement.service.product.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductServiceImpl productService;

    @InjectMocks
    private ProductController productController;

    private ObjectMapper objectMapper;
    private ProductEntity product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();
        product = new ProductEntity(1, "MC123", "Mobile Charger", 1000L);
    }

    @Test
    void testAddProduct() throws Exception {
        when(productService.isPresent(1)).thenReturn(false);
//        doNothing().when(productService).addProduct(any(ProductEntity.class));

        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Product Mobile Charger has been added successfully.")));

        verify(productService, times(1)).addProduct(any(ProductEntity.class));
    }

    @Test
    void testGetProduct() throws Exception {
        when(productService.isPresent(1)).thenReturn(true);
        when(productService.getProduct(1)).thenReturn(product);

        mockMvc.perform(get("/products/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serialNum").value(1))
                .andExpect(jsonPath("$.prodId").value("MC123"))
                .andExpect(jsonPath("$.prodName").value("Mobile Charger"))
                .andExpect(jsonPath("$.prodPrice").value(1000));

        verify(productService, times(1)).isPresent(1);
        verify(productService, times(1)).getProduct(1);
    }

    @Test
    void testGetAllProducts() throws Exception {
        List<ProductEntity> products = Arrays.asList(product, new ProductEntity(2, "PB452", "Power Bank", 1500L));

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/products/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(productService, times(2)).getAllProducts();
    }

    @Test
    void testUpdateProduct() throws Exception {
        ProductEntity updatedProduct = new ProductEntity(1, "MC567", "Charger", 2000L);

        when(productService.isPresent(1)).thenReturn(true);

//        doNothing().when(productService).updateProduct(eq(1L), any(ProductEntity.class));

        mockMvc.perform(put("/products/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Product Charger has been updated successfully")));

        verify(productService, times(1)).isPresent(1);
        verify(productService, times(1)).updateProduct(eq(1), any(ProductEntity.class));
    }

    @Test
    void testDeleteProduct() throws Exception {
        when(productService.isPresent(1)).thenReturn(true);

        doNothing().when(productService).deleteProduct(1);

        mockMvc.perform(delete("/products/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Product with serial number 1 has been deleted successfully")));

        verify(productService, times(1)).isPresent(1);
        verify(productService, times(1)).deleteProduct(1);
    }
}
