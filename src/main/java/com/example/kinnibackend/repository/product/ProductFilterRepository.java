package com.example.kinnibackend.repository.product;

import com.example.kinnibackend.dto.product.ProductFilteringResponseDTO;
import com.example.kinnibackend.entity.ProductFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public interface ProductFilterRepository extends JpaRepository<ProductFilter, Long> {
    static final Logger logger = LoggerFactory.getLogger(ProductFilteringResponseDTO.class);

    @Query("SELECT p FROM ProductFilter p WHERE "
            + "(:isGreen IS NULL OR p.isGreen = :isGreen) AND "
            + "(:searchTerm is null or lower(p.productName) like lower(concat('%',:searchTerm,'%')) escape '!') AND"
            + "(:category is null or p.category = :category) AND"
            + "(:isLowCalorie IS NULL OR p.isLowCalorie = :isLowCalorie) AND "
            + "(:isHighCalorie IS NULL OR p.isHighCalorie = :isHighCalorie) AND "
            + "(:isSugarFree IS NULL OR p.isSugarFree = :isSugarFree) AND "
            + "(:isLowSugar IS NULL OR p.isLowSugar = :isLowSugar) AND "
            + "(:isLowCarb IS NULL OR p.isLowCarb = :isLowCarb) AND "
            + "(:isHighCarb IS NULL OR p.isHighCarb = :isHighCarb) AND "
            + "(:isKeto IS NULL OR p.isKeto = :isKeto) AND "
            + "(:isLowTransFat IS NULL OR p.isLowTransFat = :isLowTransFat) AND "
            + "(:isHighProtein IS NULL OR p.isHighProtein = :isHighProtein) AND "
            + "(:isLowSodium IS NULL OR p.isLowSodium = :isLowSodium) AND "
            + "(:isLowCholesterol IS NULL OR p.isLowCholesterol = :isLowCholesterol) AND "
            + "(:isLowSaturatedFat IS NULL OR p.isLowSaturatedFat = :isLowSaturatedFat) AND "
            + "(:isLowFat IS NULL OR p.isLowFat = :isLowFat) AND "
            + "(:isHighFat IS NULL OR p.isHighFat = :isHighFat)"
    )
    Page<ProductFilter> filterProducts(
            @Param("searchTerm") String searchTerm,
            @Param("category") String category,
            @Param("isGreen") Boolean isGreen,
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

    // SQL 인젝션 공격 방지
    static String escapeSpecialCharacters(String searchTerm) {
        if (searchTerm != null) {
            return searchTerm.replaceAll("([%_])", "\\\\$1");
        }
        return null;
    }

    default Page<ProductFilter> filterProducts
            (String searchTerm, Object[] filterConditions, Pageable pageable) {
        String escapedSearchTerm = escapeSpecialCharacters(searchTerm);
        logger.info("Filtering with searchTerm: {}", escapedSearchTerm);
        logger.info("Filtering with category: {}", filterConditions[0]);
        logger.info("Filtering with isGreen: {}", filterConditions[1]);
        logger.info("Filtering with lowcal: {}", filterConditions[2]);
        logger.info("Filtering with highcal: {}", filterConditions[3]);
        logger.info("Filtering with sugarfree: {}", filterConditions[4]);
        logger.info("Filtering with lowsugar: {}", filterConditions[5]);
        logger.info("Filtering with lowcarb: {}", filterConditions[6]);
        logger.info("Filtering with highcarb: {}", filterConditions[7]);
        logger.info("Filtering with keto: {}", filterConditions[8]);
        logger.info("Filtering with lowtransfat: {}", filterConditions[9]);
        logger.info("Filtering with highprotein: {}", filterConditions[10]);
        logger.info("Filtering with lowsodium: {}", filterConditions[11]);
        logger.info("Filtering with lowchol: {}", filterConditions[12]);
        logger.info("Filtering with lowsaturated: {}", filterConditions[13]);
        logger.info("Filtering with lowfat: {}", filterConditions[14]);
        logger.info("Filtering with highfat: {}", filterConditions[15]);

        return filterProducts(
                escapedSearchTerm,
                (String) filterConditions[0],
                (Boolean) filterConditions[1],
                (Boolean) filterConditions[2],
                (Boolean) filterConditions[3],
                (Boolean) filterConditions[4],
                (Boolean) filterConditions[5],
                (Boolean) filterConditions[6],
                (Boolean) filterConditions[7],
                (Boolean) filterConditions[8],
                (Boolean) filterConditions[9],
                (Boolean) filterConditions[10],
                (Boolean) filterConditions[11],
                (Boolean) filterConditions[12],
                (Boolean) filterConditions[13],
                (Boolean) filterConditions[14],
                (Boolean) filterConditions[15],
                pageable
        );
    }
    default Page<ProductFilter> filterProducts
            (Object[] filterConditions, Pageable pageable) {
        return filterProducts(
                null,
                (String) filterConditions[0],
                (Boolean) filterConditions[1],
                (Boolean) filterConditions[2],
                (Boolean) filterConditions[3],
                (Boolean) filterConditions[4],
                (Boolean) filterConditions[5],
                (Boolean) filterConditions[6],
                (Boolean) filterConditions[7],
                (Boolean) filterConditions[8],
                (Boolean) filterConditions[9],
                (Boolean) filterConditions[10],
                (Boolean) filterConditions[11],
                (Boolean) filterConditions[12],
                (Boolean) filterConditions[13],
                (Boolean) filterConditions[14],
                (Boolean) filterConditions[15],
                pageable
        );
    }
}
