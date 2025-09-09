package com.shop.product;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JpaRepository is a Spring Data JPA interface that provides:
 * - CRUD operations (Create, Read, Update, Delete)
 * - Pagination and sorting capabilities
 * - Automatic implementation at runtime (no need to write SQL)
 * - Query method generation based on method names
 *
 * What JpaRepository provides automatically:
 * - save(entity) - Insert/Update
 * - findById(id) - Find by primary key
 * - findAll() - Get all records
 * - deleteById(id) - Delete by primary key
 * - count() - Count total records
 *
 * .NET Equivalent:
 * In .NET, the equivalent would be:
 * - Entity Framework's DbSet<T> with DbContext
 * - Repository pattern with IRepository<T> interface
 * - Example: IProductRepository : IRepository<Product> where IRepository provides
 * Add(), Update(), Delete(), GetById(), GetAll(), etc.
 */
public interface ProductRepository extends JpaRepository<Product, Long> { }