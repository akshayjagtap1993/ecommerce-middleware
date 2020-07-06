package com.ecommerce.middleware.pojo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "before_order")
public class BeforeOrder {

    @Id
    @GeneratedValue
    private int beforeOrderId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
//    @ManyToOne
//    @JoinColumn(name = "before_order_type_id")
//    private BeforeOrderType beforeOrderType;
    private String beforeOrderType;
    private LocalDateTime orderDate;

    public int getBeforeOrderId() {
        return beforeOrderId;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getBeforeOrderType() {
        return beforeOrderType;
    }

    public void setBeforeOrderType(String beforeOrderType) {
        this.beforeOrderType = beforeOrderType;
    }
}
