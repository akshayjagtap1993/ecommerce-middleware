package com.ecommerce.middleware.dto;

import com.ecommerce.middleware.pojo.User;

import java.time.LocalDateTime;

public class BeforeOrderDto {

    private int beforeOrderId;
    private User user;
    private ProductDto product;
    private String beforeOrderType;
    private LocalDateTime orderDate;

    public int getBeforeOrderId() {
        return beforeOrderId;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public void setBeforeOrderId(int beforeOrderId) {
        this.beforeOrderId = beforeOrderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBeforeOrderType() {
        return beforeOrderType;
    }

    public void setBeforeOrderType(String beforeOrderType) {
        this.beforeOrderType = beforeOrderType;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
