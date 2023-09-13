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

    // 상품 ID로 상품 찾기
    Product findByProductId(Long productId);

    @Query("SELECT p FROM Product p WHERE "
            + "(:isGreen IS NULL OR p.isGreen = :isGreen) AND "
            + "(:categoryName IS NULL OR p.categoryName = :categoryName) AND "
            + "(:searchTerm IS NULL OR p.productName LIKE %:searchTerm% OR p.categoryName LIKE %:searchTerm%) AND "
            + "(:isLowCalorie IS NULL OR (p.categoryName = '음료' AND p.kcal < 20) OR " +
                "(p.categoryName != '음료' AND p.kcal < 40)) AND "
            + "(:isSugarFree IS NULL OR p.sugar <= 1) AND "
            + "(:isLowSugar IS NULL OR p.sugar <= p.servingSize * 0.05) AND "
            + "(:isLowCarb IS NULL OR (p.carbohydrate >= p.servingSize * 0.11 AND "
                +"p.carbohydrate <= p.servingSize * 0.20)) AND "
            + "(:isKeto IS NULL OR p.carbohydrate <= p.servingSize * 0.10) AND "
            + "(:isTransFat IS NULL OR p.transFat <= 1) AND "
            + "(:isSaturatedFat IS NULL OR p.saturatedFat <= p.servingSize * 0.02) AND "
            + "(:isLowFat IS NULL OR ((p.categoryName = '음료' AND p.fat <= 1.5) OR "
                + "(p.categoryName != '음료' AND p.fat <= 3.0))) AND "
            + "(:isHighProtein IS NULL OR p.protein >= p.servingSize * 0.20) AND "
            + "(:isLowSodium IS NULL OR p.sodium <= p.servingSize * 2) AND "
            + "(:isCholesterol IS NULL OR p.cholesterol < 300)")
    List<Product> filterProducts(
            @Param("isGreen") Boolean isGreen,
            @Param("searchTerm") String searchTerm,
            @Param("categoryName") String categoryName,
            @Param("isLowCalorie") Boolean isLowCalorie,
            @Param("isSugarFree") Boolean isSugarFree,
            @Param("isLowSugar") Boolean isLowSugar,
            @Param("isLowCarb") Boolean isLowCarb,
            @Param("isKeto") Boolean isKeto,
            @Param("isTransFat") Boolean isTransFat,
            @Param("isHighProtein") Boolean isHighProtein,
            @Param("isLowSodium") Boolean isLowSodium,
            @Param("isCholesterol") Boolean isCholesterol,
            @Param("isSaturatedFat") Boolean isSaturatedFat,
            @Param("isLowFat") Boolean isLowFat
    );
}
