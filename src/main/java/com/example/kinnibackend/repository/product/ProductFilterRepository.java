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
            + "(:isHighCalorie IS NULL OR p.isHighCalorie = :isHighCalorie) AND "
            + "(:isSugarFree IS NULL OR p.isSugarFree = :isSugarFree) AND "
            + "(:isLowSugar IS NULL OR p.isLowSugar = :isLowSugar) AND "
            + "(:isLowCarb IS NULL OR p.isLowCarb = :isLowCarb) AND "
            + "(:isHighCarb IS NULL OR p.isHighCarb = :isHighCarb) AND "
            + "(:isKeto IS NULL OR p.isKeto = :isKeto) AND "
            + "(:isLowTransFat IS NULL OR p.isLowTransFat = :isTransFat) AND "
            + "(:isHighProtein IS NULL OR p.isHighProtein = :isHighProtein) AND "
            + "(:isLowSodium IS NULL OR p.isLowSodium = :isLowSodium) AND "
            + "(:isLowCholesterol IS NULL OR p.isLowCholesterol = :isCholesterol) AND "
            + "(:isLowSaturatedFat IS NULL OR p.isLowSaturatedFat = :isSaturatedFat) AND "
            + "(:isLowFat IS NULL OR p.isLowFat = :isLowFat) AND "
            + "(:isHighFat IS NULL OR p.isHighFat = :isHighFat)"
    )
    Page<ProductFilter> filterKkiniPickProducts(
            @Param("isGreen") Boolean isGreen,
            @Param("categoryName") String categoryName,
            @Param("isLowCalorie") Boolean isLowCalorie,
            @Param("isHighCalorie") Boolean isHighCalorie,
            @Param("isSugarFree") Boolean isSugarFree,
            @Param("isLowSugar") Boolean isLowSugar,
            @Param("isLowCarb") Boolean isLowCarb,
            @Param("isHighCarb") Boolean isHighCarb,
            @Param("isKeto") Boolean isKeto,
            @Param("isLowTransFat") Boolean isLowTransFat,
            @Param("isHighProtein") Boolean isHighProtein,
            @Param("isLowSodium") Boolean isLowSodium,
            @Param("isLowCholesterol") Boolean isLowCholesterol,
            @Param("isLowSaturatedFat") Boolean isLowSaturatedFat,
            @Param("isLowFat") Boolean isLowFat,
            @Param("isHighFat") Boolean isHighFat,
            Pageable pageable
    );
}
