package com.tataelxsi.training.ordermanagement.service;

import com.tataelxsi.training.ordermanagement.entity.ProductEntity;
import com.tataelxsi.training.ordermanagement.repository.ProductRepository;
import com.tataelxsi.training.ordermanagement.service.product.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductEntity product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new ProductEntity(1, "MC123", "Mobile Charger", 1000L);
    }

    @Test
    void testAddProduct() {
        when(productRepository.save(any(ProductEntity.class))).thenReturn(product);

        productService.addProduct(product);

        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void testGetProduct() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        ProductEntity foundProduct = productService.getProduct(1);

        assertNotNull(foundProduct);
        assertEquals(1, foundProduct.getSerialNum());
        assertEquals("MC123", foundProduct.getProdId());
        assertEquals("Mobile Charger", foundProduct.getProdName());
        assertEquals(1000, foundProduct.getProdPrice());

        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void testGetAllProducts() {
        List<ProductEntity> products = Arrays.asList(product, new ProductEntity(2,"PB452", "Power Bank",1500L));

        when(productRepository.findAll()).thenReturn(products);

        List<ProductEntity> allProducts = productService.getAllProducts();

        assertEquals(2, allProducts.size());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testUpdateProduct() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(product);

        ProductEntity updatedProduct = new ProductEntity(1, "MC567", "Charger", 1200L);

        productService.updateProduct(1, updatedProduct);

        assertEquals(1, updatedProduct.getSerialNum());
        assertEquals("MC567", updatedProduct.getProdId());
        assertEquals("Charger", updatedProduct.getProdName());
        assertEquals(1200, updatedProduct.getProdPrice());

        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        doNothing().when(productRepository).deleteById(1);

        productService.deleteProduct(1);

        verify(productRepository, times(1)).deleteById(1);
    }
}
