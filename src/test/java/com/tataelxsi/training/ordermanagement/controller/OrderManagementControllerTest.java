package com.tataelxsi.training.ordermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tataelxsi.training.ordermanagement.entity.OrderManagementEntity;
import com.tataelxsi.training.ordermanagement.service.order.OrderManagementServiceImpl;
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

class OrderManagementControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderManagementServiceImpl orderManagementService;

    @InjectMocks
    private OrderManagementController orderManagementController;

    private ObjectMapper objectMapper;
    private OrderManagementEntity order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderManagementController).build();
        objectMapper = new ObjectMapper();
        order = new OrderManagementEntity(1L, "C101", "Peter", "MC123", "Mobile Charger", 2L, 1000L, 2000L);
    }

    @Test
    void testAddOrder() throws Exception {
        when(orderManagementService.isPresent(1L)).thenReturn(false);
//        doNothing().when(orderManagementService).addOrder(any(OrderManagementEntity.class));

        mockMvc.perform(post("/orders/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Thank you for placing your order, Peter! Your order id: 1")));

        verify(orderManagementService, times(1)).addOrder(any(OrderManagementEntity.class));
    }

    @Test
    void testGetOrderDetails() throws Exception {
        when(orderManagementService.isPresent(1L)).thenReturn(true);
        when(orderManagementService.getOrderDetails(1L)).thenReturn(order);

        mockMvc.perform(get("/orders/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.customerId").value("C101"))
                .andExpect(jsonPath("$.customerName").value("Peter"))
                .andExpect(jsonPath("$.productId").value("MC123"))
                .andExpect(jsonPath("$.productName").value("Mobile Charger"))
                .andExpect(jsonPath("$.productQuantity").value(2))
                .andExpect(jsonPath("$.productPrice").value(1000))
                .andExpect(jsonPath("$.totalPrice").value(2000));

        verify(orderManagementService, times(1)).isPresent(1L);
        verify(orderManagementService, times(1)).getOrderDetails(1L);
    }

    @Test
    void testGetAllOrderDetails() throws Exception {
        List<OrderManagementEntity> orders = Arrays.asList(order, new OrderManagementEntity(2L, "C201", "Alice", "PB452", "Power Bank", 3L, 1500L, 4500L));

        when(orderManagementService.getAllOrderDetails()).thenReturn(orders);

        mockMvc.perform(get("/orders/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(orderManagementService, times(2)).getAllOrderDetails();
    }

    @Test
    void testUpdateOrder() throws Exception {
        OrderManagementEntity updatedOrder = new OrderManagementEntity(1L, "C101", "Samuel", "MC567",
                "Charger", 1L, 2000L, 2000L);

        when(orderManagementService.isPresent(1L)).thenReturn(true);

//        doNothing().when(orderManagementService).updateOrder(eq(1L), any(OrderManagementEntity.class));

        mockMvc.perform(put("/orders/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedOrder)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hey Samuel! Your order with id 1 has been successfully updated.")));

        verify(orderManagementService, times(1)).isPresent(1L);
        verify(orderManagementService, times(1)).updateOrder(eq(1L), any(OrderManagementEntity.class));
    }

    @Test
    void testDeleteOrder() throws Exception {
        when(orderManagementService.isPresent(1L)).thenReturn(true);

        doNothing().when(orderManagementService).deleteOrder(1L);

        mockMvc.perform(delete("/orders/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Oh no! Your order with id: 1 has been deleted.")));

        verify(orderManagementService, times(1)).isPresent(1L);
        verify(orderManagementService, times(1)).deleteOrder(1L);
    }
}
