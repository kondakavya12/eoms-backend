package com.tataelxsi.training.ordermanagement.controller;

import com.tataelxsi.training.ordermanagement.entity.OrderManagementEntity;
import com.tataelxsi.training.ordermanagement.exceptions.*;
import com.tataelxsi.training.ordermanagement.service.order.OrderManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "https://eoms-app.vercel.app/")  // Allow requests from the frontend
@RestController
@RequestMapping("/orders")
public class OrderManagementController {

    @Autowired
    private OrderManagementService orderManagementService;

//    @GetMapping("/message")
//	public String displayMessage() {
//	    return "Application working fine!";
//	}

    @PostMapping("/add")
    public String addOrder(@RequestBody OrderManagementEntity order) {
//        return orderManagementService.addOrder(order);
        if(order.getOrderId()==null || order.getOrderId()<=0)
            throw new InvalidOrderIdException("Enter valid Order ID");
        else if(order.getCustomerId()==null || order.getCustomerId().isEmpty())
            throw new InvalidCustomerIdException("Enter valid Customer ID");
        else if(order.getCustomerName()==null || order.getCustomerName().isEmpty())
            throw new InvalidCustomerNameException("Enter valid Customer Name");
        else if(order.getProductId()==null || order.getProductId().isEmpty())
            throw new InvalidProductIdException("Enter valid Product ID");
        else if(order.getProductName()==null || order.getProductName().isEmpty())
            throw new InvalidProductNameException("Enter valid Product Name");
        else if(order.getProductQuantity()==null || order.getProductQuantity()<=0)
            throw new InvalidProductQuantityException("Enter valid Product Quantity");
        else if(order.getProductPrice()==null || order.getProductPrice()<=0)
            throw new InvalidProductPriceException("Enter valid Product Price");
        else
        {
            orderManagementService.addOrder(order);
            return "Thank you for placing your order, " + order.getCustomerName() +"! Your order id: "+ order.getOrderId();
        }
    }

    @GetMapping("/get/{orderId}")
    public OrderManagementEntity getOrderDetails(@PathVariable Long orderId) {
//        return orderManagementService.getOrderDetails(orderId);
        if(orderManagementService.isPresent(orderId))
            return orderManagementService.getOrderDetails(orderId);
        else
            throw new OrderNotFoundException("Order not found with ID: "+ orderId);
    }

    @GetMapping("/all")
    public List<OrderManagementEntity> getAllOrderDetails(){
//        return orderManagementService.getAllOrderDetails();
        List<OrderManagementEntity> ref = orderManagementService.getAllOrderDetails();
        if(ref.isEmpty())
            throw new ListEmptyException("No Orders Found!");
        return orderManagementService.getAllOrderDetails();
    }

    @PutMapping("/update/{orderId}")
    public String updateOrder(@PathVariable Long orderId, @RequestBody OrderManagementEntity updatedOrder) {
//        return orderManagementService.updateOrder(orderId, updatedOrder);
        if(updatedOrder.getOrderId()==null || updatedOrder.getOrderId()<=0)
            throw new InvalidOrderIdException("Enter valid Order ID");
        else if(updatedOrder.getCustomerId()==null || updatedOrder.getCustomerId().isEmpty())
            throw new InvalidCustomerIdException("Enter valid Customer ID");
        else if(updatedOrder.getCustomerName()==null || updatedOrder.getCustomerName().isEmpty())
            throw new InvalidCustomerNameException("Enter valid Customer Name");
        else if(updatedOrder.getProductId()==null || updatedOrder.getProductId().isEmpty())
            throw new InvalidProductIdException("Enter valid Product ID");
        else if(updatedOrder.getProductName()==null || updatedOrder.getProductName().isEmpty())
            throw new InvalidProductNameException("Enter valid Product Name");
        else if(updatedOrder.getProductQuantity()==null || updatedOrder.getProductQuantity()<=0)
            throw new InvalidProductQuantityException("Enter valid Product Quantity");
        else if(updatedOrder.getProductPrice()==null || updatedOrder.getProductPrice()<=0)
            throw new InvalidProductPriceException("Enter valid Product Price");
        else {
            if(!orderManagementService.isPresent(orderId))
                throw new OrderNotFoundException("Order ID: "+ orderId +" doesn't exist!");
            orderManagementService.updateOrder(orderId, updatedOrder);
            return "Hey " + updatedOrder.getCustomerName() + "! Your order with id "+ orderId + " has been successfully updated.";
        }
    }

    @DeleteMapping("/delete/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
//        return orderManagementService.deleteOrder(orderId);
        if(orderManagementService.isPresent(orderId)) {
            orderManagementService.deleteOrder(orderId);
            return "Oh no! Your order with id: " + orderId +" has been deleted.";
        }
        else
            throw new OrderNotFoundException("Order ID: " + orderId + " doesn't exist!");
    }
}
