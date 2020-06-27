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
    private Product productId;
    @ManyToOne
    @JoinColumn(name = "before_order_type_id")
    private BeforeOrderType beforeOrderType;
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

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public BeforeOrderType getBeforeOrderType() {
        return beforeOrderType;
    }

    public void setBeforeOrderType(BeforeOrderType beforeOrderType) {
        this.beforeOrderType = beforeOrderType;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
