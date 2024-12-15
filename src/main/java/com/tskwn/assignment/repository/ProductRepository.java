package com.tskwn.assignment.repository;

import com.tskwn.assignment.entity.Brand;
import com.tskwn.assignment.entity.Category;
import com.tskwn.assignment.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * 상품정보 Repository.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findFirstByCategoryOrderByPriceAsc(Category category);

    @Query("SELECT " +
            " new com.tskwn.assignment.entity.Product(null, p.brand, p.category, MIN(p.price)) " +
            " FROM Product p " +
            " WHERE p.brand = :brand " +
            " GROUP BY p.brand, p.category ")
    List<Product> findLowestPriceProductByBrand(@Param("brand") Brand brand);

    @Query("SELECT " +
            " new com.tskwn.assignment.entity.Product(null, p.brand, p.category, p.price) " +
            " FROM Product p " +
            " WHERE p.category = :category " +
            " ORDER BY p.price ASC LIMIT 1 ")
    Optional<Product> findLowestPriceByCategory(Category category);

    @Query("SELECT " +
            " new com.tskwn.assignment.entity.Product(null, p.brand, p.category, p.price) " +
            " FROM Product p " +
            " WHERE p.category = :category " +
            " ORDER BY p.price DESC LIMIT 1 ")
    Optional<Product> findHighestPriceByCategory(Category category);
}
