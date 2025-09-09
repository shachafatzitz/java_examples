package com.shop.cart;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"username", "productId"}))
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Long productId;

    @Min(1)
    private int quantity = 1;

    protected CartItem() {}
    public CartItem(String username, Long productId, int quantity) {
        this.username = username;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
