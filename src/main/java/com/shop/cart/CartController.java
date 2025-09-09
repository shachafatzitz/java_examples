package com.shop.cart;

import jakarta.validation.constraints.Min;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService service) {
        this.cartService = service;
    }

    private static String getUserName(UserDetails userDetails) {
        return userDetails.getUsername();
    }

    public record AddItem(Long productId, @Min(1) Integer quantity) { }

    @GetMapping
    public CartService.CartView view(@AuthenticationPrincipal UserDetails userDetails) {
        return cartService.getCart(getUserName(userDetails));
    }

    @PostMapping("/items")
    public void add(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AddItem req) {
        cartService.addOrUpdate(getUserName(userDetails), req.productId(), req.quantity());
    }

    @PutMapping("/items/{productId}")
    public void update(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long productId, @RequestParam @Min(1) Integer quantity) {
        cartService.setQuantity(getUserName(userDetails), productId, quantity);
    }

    @DeleteMapping("/items/{productId}")
    public void delete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long productId) {
        cartService.remove(getUserName(userDetails), productId);
    }

    @DeleteMapping
    public void clear(@AuthenticationPrincipal UserDetails userDetails) {
        cartService.clear(getUserName(userDetails));
    }
}
