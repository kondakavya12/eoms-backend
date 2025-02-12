package com.tataelxsi.training.ordermanagement.service.order;

import com.tataelxsi.training.ordermanagement.entity.OrderManagementEntity;
import com.tataelxsi.training.ordermanagement.repository.OrderManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderManagementServiceImpl implements OrderManagementService {

    @Autowired
    private OrderManagementRepository orderManagementRepository;

    @Override
    public void addOrder(OrderManagementEntity order) {
        order.calculateTotalPrice();  // Calculate total price before saving
        orderManagementRepository.save(order);
    }

    @Override
    public OrderManagementEntity getOrderDetails(Long orderId) {
        return orderManagementRepository.findById(orderId).get();
    }

    @Override
    public List<OrderManagementEntity> getAllOrderDetails() {
        return orderManagementRepository.findAll();
    }

    @Override
    public void updateOrder(Long orderId, OrderManagementEntity updatedOrder) {
        OrderManagementEntity ref = getOrderDetails(orderId);
        ref.setOrderId(updatedOrder.getOrderId());
        ref.setCustomerId(updatedOrder.getCustomerId());
        ref.setCustomerName(updatedOrder.getCustomerName());
        ref.setProductId(updatedOrder.getProductId());
        ref.setProductName(updatedOrder.getProductName());
        ref.setProductQuantity(updatedOrder.getProductQuantity());
        ref.setProductPrice(updatedOrder.getProductPrice());
        ref.setTotalPrice(updatedOrder.calculateTotalPrice()); //ref.calculateTotalPrice();
        orderManagementRepository.save(ref);
//        orderManagementRepository.save(updatedOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderManagementRepository.deleteById(orderId);
    }

    public boolean isPresent(Long orderId) {
        if(orderManagementRepository.existsById(orderId))
            return true;
        else
            return false;
    }
}
