package com.ecommerce.middleware.dto;

import com.ecommerce.middleware.pojo.User;
import com.ecommerce.middleware.utils.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

public class UserOrderDto {

    private int userOrderId;
    private User user;
    private ProductDto product;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime orderDate;
    private String status;

    public int getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(int userOrderId) {
        this.userOrderId = userOrderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
