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

    @Query("SELECT p FROM Product p WHERE " +
            "(p.isGreen = :isGreen OR :isGreen IS NULL) " +
            "AND (REPLACE(p.productName, ' ', '') LIKE %:searchTerm% OR :searchTerm IS NULL) " +
            "AND (p.categoryName = :categoryName OR :categoryName IS NULL) " +
            "AND (p.calorie <= p.totalAmount * 2 OR :isLowCalorie = false) " +
            "AND (p.sugar <= 1 OR :isSugarFree = false) " +
            "AND (p.sugar <= p.totalAmount * 0.05 OR :isLowSugar = false) " +
            "AND (p.carb >= p.totalAmount * 0.11 AND p.carb <= p.totalAmount * 0.20 OR :isLowCarb = false) " +
            "AND (p.carb <= p.totalAmount * 0.10 OR :isKeto = false) " +
            "AND (p.transFat <= 1 OR :isTransFat = false) " +
            "AND (p.protein >= p.totalAmount * 0.20 OR :isHighProtein = false) " +
            "AND (p.sodium <= p.totalAmount * 2 OR :isLowSodium = false) " +
            "AND (p.cholesterol < 300 OR :isCholesterol = false) " +
            "AND (p.saturatedFat <= p.totalAmount * 0.02 OR :isSaturatedFat = false) " +
            "AND (p.fat <= p.totalAmount * 0.04 OR :isLowFat = false) "
    )
    List<Product> searchByCriteriaWithFilters(
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
// : 의 의미
    // :~~~ 는 메서드의 매개변수로 전달된 ~~~값에 대응된다.
    // ex. :isGreen은 isGreen 값으로 대체
}
