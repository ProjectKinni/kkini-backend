package com.example.kinnibackend.repository.product;

import com.example.kinnibackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE REPLACE(p.productName, ' ', '') LIKE %?1%")
    List<Product> findByProductName(String productName);

    @Query("SELECT p FROM Product p WHERE REPLACE(p.categoryName, ' ', '') LIKE %?1%")
    List<Product> findByCategoryName(String categoryName);
}
