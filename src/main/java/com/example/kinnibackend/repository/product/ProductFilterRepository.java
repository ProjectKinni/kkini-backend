package com.example.kinnibackend.repository.product;

import com.example.kinnibackend.entity.ProductFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductFilterRepository extends JpaRepository<ProductFilter, Long> {
    @Query("SELECT p FROM ProductFilter p WHERE "
            + "(p.isGreen = :isGreen) AND "
            + "(p.categoryName  = :categoryName) AND "
            + "(p.isLowCalorie  = :isLowCalorie) AND "
            + "(p.isSugarFree  = :isSugarFree) AND "
            + "(p.isLowSugar  = :isLowSugar) AND "
            + "(p.isLowCarb  = :isLowCarb) AND "
            + "(p.isKeto  = :isKeto) AND "
            + "(p.isTransFat  = :isTransFat) AND "
            + "(p.isHighProtein  = :isHighProtein) AND "
            + "(p.isLowSodium  = :isLowSodium) AND "
            + "(p.isCholesterol  = :isCholesterol) AND "
            + "(p.isSaturatedFat  = :isSaturatedFat) AND "
            + "(p.isSaturatedFat  = :isSaturatedFat) AND"
            + "(p.isLowFat  = :isLowFat)"
    )
    List<ProductFilter> filterProductResponse(
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
            @Param("isLowFat") Boolean isLowFat
    );
}
