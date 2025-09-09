package com.shop.order;

import com.shop.cart.CartItemRepository;
import com.shop.product.Product;
import com.shop.product.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public CheckoutServiceImpl(CartItemRepository cartItemRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Override
    public Order checkout(String username) {
        var cartItems = cartItemRepository.findByUsername(username);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        BigDecimal totalPrice = BigDecimal.ZERO;
        var order = new Order(username, BigDecimal.ZERO);

        for (var item : cartItems) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalStateException("Product not found: " + item.getProductId()));
            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalPrice = totalPrice.add(lineTotal);

            var orderItem = new OrderItem(product.getId(), product.getName(), product.getPrice(), item.getQuantity());

            order.addItem(orderItem);
        }

        var saved = orderRepository.save(new Order(username, totalPrice));
        for (var item : order.getItems()) {
            item.setOrder(saved);
        }
        saved.getItems().addAll(order.getItems());
        var finalorder = orderRepository.save(saved);

        cartItemRepository.deleteByUsername(username);

        return finalorder;
    }
}
