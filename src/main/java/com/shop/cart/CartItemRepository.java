package com.shop.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUsername(String username);

    Optional<CartItem> findByUsernameAndProductId(String username, Long productId);

    void deleteByUsernameAndProductId(String username, Long productId);

    void deleteByUsername(String username);
}
