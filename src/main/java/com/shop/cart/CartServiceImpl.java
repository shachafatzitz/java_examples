package com.shop.cart;

import com.shop.product.Product;
import com.shop.product.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Service marks a class as a service-layer bean (business logic component) and registers it in the Spring context for dependency injection.
 * Its .NET equivalent is simply registering a service with the DI container in Program.cs, e.g. builder.Services.AddScoped<UserService>(); (or IUserService with its implementation).
 */
@Service
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    /**
     * @Override marks a method as an implementation of a method from a superclass or interface,
     * letting the compiler catch signature mistakes and improving readability.
     * In .NET (C#), the equivalent is the override keyword when overriding a virtual/abstract method,
     * and interface implementations are enforced automatically by the compiler without needing an attribute.
     */
    @Override
    public CartView getCart(String username) {
        List<CartItem> items = cartItemRepository.findByUsername(username);
        List<CartLine> lines = items.stream().map(item -> {
            Product product = productRepository.findById(item.getProductId()).orElseThrow(() -> new IllegalStateException("Product not found: " + item.getProductId()));
            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            return new CartLine(product.getId(), product.getName(), product.getPrice(), item.getQuantity(), lineTotal);
        }).toList();
        BigDecimal total = lines.stream()
                .map(CartLine::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new CartView(lines, total);
    }

    /**
     * @Transactional marks a method or class to run inside a database transaction.
     * Spring opens the transaction before the method, commits on success, and rolls back on failure.
     * Its .NET equivalent is using an EF Core DbContext transaction (await context.Database.BeginTransactionAsync()).
     */
    @Transactional
    @Override
    public void addOrUpdate(String username, Long productId, int quantity) {
        CartItem item = cartItemRepository.findByUsernameAndProductId(username, productId)
                .orElse(new CartItem(username, productId, 0));
        item.setQuantity(item.getQuantity() + quantity);
        cartItemRepository.save(item);
    }

    @Transactional
    @Override
    public void setQuantity(String username, Long productId, int quantity) {
        CartItem item = cartItemRepository.findByUsernameAndProductId(username, productId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }

    @Transactional
    @Override
    public void remove(String username, Long productId) {
        cartItemRepository.deleteByUsernameAndProductId(username, productId);
    }

    @Transactional
    @Override
    public void clear(String username) {
        cartItemRepository.deleteByUsername(username);
    }
}
