package com.tataelxsi.training.ordermanagement.service.order;

import com.tataelxsi.training.ordermanagement.entity.OrderManagementEntity;
import java.util.List;

public interface OrderManagementService {

    void addOrder(OrderManagementEntity order);
//    OrderManagementEntity addOrder(OrderManagementEntity order);

    OrderManagementEntity getOrderDetails(Long orderId);

    List<OrderManagementEntity> getAllOrderDetails();

    void updateOrder(Long orderId, OrderManagementEntity updatedOrder);
//    OrderManagementEntity updateOrder(Long orderId, OrderManagementEntity updatedOrder);

    void deleteOrder(Long orderId);
    //    void deleteOrder(Long orderId);

    boolean isPresent(Long orderId);
}
