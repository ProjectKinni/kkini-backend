package com.example.kinnibackend.repository.product;

import com.example.kinnibackend.entity.ProductFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


@Repository
public interface ProductFilterRepository extends JpaRepository<ProductFilter, Long> {
    @Query("SELECT p FROM ProductFilter p WHERE "
            + "(:isGreen IS NULL OR p.isGreen = :isGreen) AND "
            + "(:categoryName IS NULL OR p.categoryName = :categoryName) AND "
            + "(:isLowCalorie IS NULL OR p.isLowCalorie = :isLowCalorie) AND "
            + "(:isSugarFree IS NULL OR p.isSugarFree = :isSugarFree) AND "
            + "(:isLowSugar IS NULL OR p.isLowSugar = :isLowSugar) AND "
            + "(:isLowCarb IS NULL OR p.isLowCarb = :isLowCarb) AND "
            + "(:isKeto IS NULL OR p.isKeto = :isKeto) AND "
            + "(:isTransFat IS NULL OR p.isTransFat = :isTransFat) AND "
            + "(:isHighProtein IS NULL OR p.isHighProtein = :isHighProtein) AND "
            + "(:isLowSodium IS NULL OR p.isLowSodium = :isLowSodium) AND "
            + "(:isCholesterol IS NULL OR p.isCholesterol = :isCholesterol) AND "
            + "(:isSaturatedFat IS NULL OR p.isSaturatedFat = :isSaturatedFat) AND "
            + "(:isLowFat IS NULL OR p.isLowFat = :isLowFat)"
    )
    Page<ProductFilter> filterKkiniPickProducts(
            @Param("isGreen") Boolean isGreen,
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
            @Param("isLowFat") Boolean isLowFat,
            Pageable pageable
    );
}
