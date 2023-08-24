package com.example.kinnibackend.product.repository;

import com.example.kinnibackend.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductJPARepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE " +
            "(LOCATE(:name, p.productName) > 0 AND LENGTH(:name) >= 2 OR :name IS NULL) AND " +
            "p.categoryName = :categoryName ORDER BY p.averageRating DESC")
    List<Product> findByNameAndCategoryName(@Param("name") String name,
                                            @Param("categoryName") String categoryName);
    List<Product> findByProductNameContainingOrderByAverageRatingDesc(String name);

    @Query("SELECT p FROM Product p WHERE p.categoryName = :categoryName ORDER BY p.averageRating DESC")
    List<Product> findByCategoryNameOrderByAverageRatingDesc(@Param("categoryName") String categoryName);

    List<Product> findByCategoryNameContaining(String categoryName);
}
