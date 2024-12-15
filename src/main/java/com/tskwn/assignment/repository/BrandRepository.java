package com.tskwn.assignment.repository;

import com.tskwn.assignment.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * 브랜드 정보 Repository.
 */
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findOneByName(String name);

    @Query("SELECT s.brand as brand, SUM(s.min_price) as sum_price " +
            " FROM (SELECT p.brand as brand, p.category as category, MIN(p.price) as min_price " +
            "   FROM Product p " +
            "   GROUP BY p.brand, p.category " +
            " ) s GROUP BY s.brand ORDER BY sum_price ASC LIMIT 1")
    Optional<Brand> findBrandByLowestPrice();
}
