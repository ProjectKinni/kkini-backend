package com.example.kinnibackend.repository.product;

import com.example.kinnibackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 띄어 쓰기 제외한 상품명 검색
    @Query("SELECT p FROM Product p WHERE REPLACE(p.productName, ' ', '') LIKE %?1%")
    List<Product> findByProductName(String productName);

    // 띄어 쓰기 제외한 카테고리명 검색
    @Query("SELECT p FROM Product p WHERE REPLACE(p.categoryName, ' ', '') LIKE %?1%")
    List<Product> findByCategoryName(String categoryName);

    // 끼니 그린 체크리스트와 상품명
    @Query("SELECT p FROM Product p WHERE p.isGreen = true AND REPLACE(p.productName, ' ', '') LIKE %:searchTerm%")
    List<Product> findGreenByProductName(@Param("searchTerm") String productName);

    // 끼니 그린 체크리스트와 카테고리명
    @Query("SELECT p FROM Product p WHERE p.isGreen = true AND REPLACE(p.categoryName, ' ', '') LIKE %:searchTerm%")
    List<Product> findGreenByCategoryName(@Param("searchTerm") String categoryName);

    // 카테고리 체크리스트
    List<Product> findByCategoryNameAndProductNameContains(String categoryName, String searchTerm);

    // 끼니 체크리스트와 카테고리 체크리스트 중복  체크 가능
    @Query("SELECT p FROM Product p WHERE p.isGreen = true AND REPLACE(p.productName, ' ', '') " +
            "LIKE %:searchTerm% AND p.categoryName = :categoryName")
    List<Product> findGreenByProductNameAndCategory
            (@Param("searchTerm") String productName, @Param("categoryName") String categoryName);
}
