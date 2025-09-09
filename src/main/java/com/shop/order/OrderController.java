package com.shop.order;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CheckoutService checkoutService;
    private final OrderRepository orderRepository;

    public OrderController(CheckoutService checkoutService, OrderRepository orderRepository) {
        this.checkoutService = checkoutService;
        this.orderRepository = orderRepository;
    }

    private static String getUserName(UserDetails userDetails) {
        return userDetails.getUsername();
    }

    @PostMapping("/checkout")
    public Order place(@AuthenticationPrincipal UserDetails userDetails) {
        return checkoutService.checkout(getUserName(userDetails));
    }

    @GetMapping()
    public List<Order> mine(@AuthenticationPrincipal UserDetails userDetails) {
        return orderRepository.findByUsernameOrderByCreatedAtDesc(getUserName(userDetails));
    }
}
