package com.shop.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * The @Entity annotation marks this class as a JPA entity, which means:
 * - It will be mapped to a database table (table name defaults to class name in lowercase)
 * - Each instance represents a row in the database table
 * - JPA will automatically handle persistence operations
 * - The class becomes part of the persistence context
 *
 * In .NET Entity Framework, the equivalent approaches are:
 * 1. Code First with DbSet (most common)
 * - Example: public DbSet<Product> Products { get; set; }
 *
 * 2. Explicit mapping with [Table] attribute
 * - Example: [Table("products")] - explicitly specify table name
 *
 * 3. Fluent API configuration:
 * - Configure mapping in OnModelCreating() method in DbContext
 * - Example: modelBuilder.Entity<Product>().ToTable("products")
 */
@Entity
public class Product {
    /**
     * annotations are used to map Java classes and their fields to database tables and columns.
     * For example, the @Entity annotation marks a class as a persistent entity, meaning it represents a table in the database.
     * Other annotations like @Id, @Column, or @OneToMany further define how fields map to primary keys, columns, and relationships.
     *
     * In the .NET world this will be conceptually similar to EF Core attributes,
     * where attributes such as [Table], [Key], or [ForeignKey] are applied to classes and properties to define their mapping to the database.
     * In both Java and .NET, annotations/attributes provide a declarative way to configure persistence without writing explicit mapping files,
     * making the code more concise and readable.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;

    // Protected no-arg constructor for JPA
    protected Product() {}
    public Product(String name, BigDecimal price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }
}