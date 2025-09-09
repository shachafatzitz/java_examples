package com.shop.config;

import com.shop.product.Product;
import com.shop.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

// This class loads initial data into the database
// when the application starts, if the product table is empty.
@Configuration
public class DataLoader {
    // @Bean annotation indicates that this method produces a bean
    // to be managed by the Spring container.
    // A Bean is an object that is instantiated, assembled, and otherwise managed by a Spring IoC container.
    // CommandLineRunner is a functional interface that indicates
    // a bean should run when it is contained within a SpringApplication.
    // The run method of the CommandLineRunner interface is invoked
    // with command line arguments when the application starts.
    @Bean
    CommandLineRunner seed(ProductRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Product("Coffe Beans", BigDecimal.valueOf(11.2), "some coffee"));
                repo.save(new Product("Espresso Cup", BigDecimal.valueOf(3.21), "cup"));
                repo.save(new Product("French Press", BigDecimal.valueOf(231.6), "french press"));
            }
        };
    }
}
