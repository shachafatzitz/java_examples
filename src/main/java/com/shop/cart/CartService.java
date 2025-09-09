package com.shop.cart;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    record CartLine(Long productId, String name, BigDecimal price, Integer quantity, BigDecimal lineTotal) {}
    record CartView(List<CartLine> items, BigDecimal total) {}

    CartView getCart(String username);

    void addOrUpdate(String username, Long productId, int quantity);

    void setQuantity(String username, Long productId, int quantity);

    void remove(String username, Long productId);

    void clear(String username);

}