package com.shop.product;

import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
/**
 * @RequestMapping defines the URL path (and optionally HTTP method) for a controller or method.
 * Its .NET equivalent is [Route] (often combined with [HttpGet], [HttpPost], etc.) to map endpoints to controller actions.
 */
@RequestMapping("/api/products")
/**
 * @RestController marks a class as a REST API controller.
 * Its .NET equivalent is [ApiController] on a class inheriting ControllerBase, which exposes REST endpoints.
 */
public class ProductController {
    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    /**
     * @GetMapping maps HTTP GET requests to specific handler methods.
     * Its .NET equivalent is [HttpGet] on a controller action method.
     */
    @GetMapping
    public List<Product> all() {
        return repo.findAll();
    }
}