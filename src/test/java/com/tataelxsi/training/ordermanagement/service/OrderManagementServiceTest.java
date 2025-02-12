package com.tataelxsi.training.ordermanagement.service;

import com.tataelxsi.training.ordermanagement.entity.OrderManagementEntity;
import com.tataelxsi.training.ordermanagement.repository.OrderManagementRepository;
import com.tataelxsi.training.ordermanagement.service.order.OrderManagementServiceImpl;
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

class OrderManagementServiceTest {

    @Mock
    private OrderManagementRepository orderManagementRepository;

    @InjectMocks
    private OrderManagementServiceImpl orderManagementService;

    private OrderManagementEntity order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        order = new OrderManagementEntity(1L, "C101", "Peter", "MC123", "Mobile Charger", 2L, 1000L, 2000L);
    }

    @Test
    void testAddOrder() {
        when(orderManagementRepository.save(any(OrderManagementEntity.class))).thenReturn(order);

        orderManagementService.addOrder(order);

        verify(orderManagementRepository, times(1)).save(any(OrderManagementEntity.class));
    }

    @Test
    void testGetOrderDetails() {
        when(orderManagementRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderManagementEntity foundOrder = orderManagementService.getOrderDetails(1L);

        assertNotNull(foundOrder);
        assertEquals(1, foundOrder.getOrderId());
        assertEquals("C101", foundOrder.getCustomerId());
        assertEquals("Peter", foundOrder.getCustomerName());
        assertEquals("MC123", foundOrder.getProductId());
        assertEquals("Mobile Charger", foundOrder.getProductName());
        assertEquals(2, foundOrder.getProductQuantity());
        assertEquals(1000, foundOrder.getProductPrice());
        assertEquals(2000, foundOrder.getTotalPrice());

        verify(orderManagementRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllOrderDetails() {
        List<OrderManagementEntity> orders = Arrays.asList(order, new OrderManagementEntity(2L, "C201", "Alice", "PB452", "Power Bank", 1L, 1500L, 1500L));

        when(orderManagementRepository.findAll()).thenReturn(orders);

        List<OrderManagementEntity> allOrders = orderManagementService.getAllOrderDetails();

        assertEquals(2, allOrders.size());

        verify(orderManagementRepository, times(1)).findAll();
    }

    @Test
    void testUpdateOrder() {
        when(orderManagementRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderManagementRepository.save(any(OrderManagementEntity.class))).thenReturn(order);

        OrderManagementEntity updatedOrder = new OrderManagementEntity(1L, "C101", "Samuel", "MC567", "Charger", 3L, 1200L, 3600L);

        orderManagementService.updateOrder(1L, updatedOrder);

        assertEquals(1, updatedOrder.getOrderId());
        assertEquals("Samuel", updatedOrder.getCustomerName());
        assertEquals("MC567", updatedOrder.getProductId());
        assertEquals("Charger", updatedOrder.getProductName());
        assertEquals(3, updatedOrder.getProductQuantity());
        assertEquals(1200, updatedOrder.getProductPrice());
        assertEquals(3600, updatedOrder.getTotalPrice());

        verify(orderManagementRepository, times(1)).findById(1L);
        verify(orderManagementRepository, times(1)).save(any(OrderManagementEntity.class));
    }

    @Test
    void testDeleteOrder() {
        when(orderManagementRepository.findById(1L)).thenReturn(Optional.of(order));

        doNothing().when(orderManagementRepository).deleteById(1L);

        orderManagementService.deleteOrder(1L);

        verify(orderManagementRepository, times(1)).deleteById(1L);
    }
}
