package com.example.kinnibackend.repository.product;

import com.example.kinnibackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductJPARepository extends JpaRepository<Product, Long> {

    List<Product> findByProductNameContainingOrderByAverageRatingDesc(String productName);

    List<Product> findByCategoryNameOrderByAverageRatingDesc(@Param("categoryName") String categoryName);

    List<Product> findByCategoryNameContaining(String categoryName);

    List<Product> findByIsKkiniTrueOrIsKkiniFalse();

    List<Product> findByIsKkiniFalse();

    List<Product> findByCategoryName(String categoryName);
}
