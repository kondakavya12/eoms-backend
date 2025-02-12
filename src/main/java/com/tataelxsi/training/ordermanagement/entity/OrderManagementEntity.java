package com.tataelxsi.training.ordermanagement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "ORDER_DETAILS")
public class OrderManagementEntity {

    @Id
    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "CUSTOMER_ID")
    private String customerId;
    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "PRODUCT_ID")
    private String productId;
    @Column(name = "PRODUCT_NAME")
    private String productName;
    @Column(name = "PRODUCT_QUANTITY")
    private Long productQuantity;
    @Column(name = "PRODUCT_PRICE")
    private Long productPrice;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Hide in input, show in output
    @Column(name = "TOTAL_PRICE")
    private Long totalPrice;

    //Empty constructor
    public OrderManagementEntity(){
    }

    //Constructor with parameters
    public OrderManagementEntity(Long orderId, String customerId, String customerName, String productId,
                                 String productName, Long productQuantity, Long productPrice, Long totalPrice){
        super();
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.productId = productId;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
    }

    //Getter and Setter for all parameters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Method to calculate total price
    public long calculateTotalPrice(){
        this.totalPrice = this.productPrice * this.productQuantity;
        return totalPrice;
    }
}
