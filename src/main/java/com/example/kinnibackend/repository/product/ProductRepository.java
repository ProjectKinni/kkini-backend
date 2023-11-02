package com.example.kinnibackend.repository.product;

import com.example.kinnibackend.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 띄어 쓰기 제외한 상품명 검색
    @Query("SELECT p FROM Product p WHERE REPLACE(p.productName, ' ', '') LIKE %?1%")
    List<Product> findByProductName(String productName);

    // 띄어 쓰기 제외한 카테고리명 검색
    @Query("SELECT p FROM Product p WHERE REPLACE(p.category, ' ', '') LIKE %?1%")
    List<Product> findByCategoryName(String category);

    // 상품 ID로 상품 찾기
    @Query("SELECT p FROM Product p WHERE p.productId = :productId ORDER BY p.productId DESC")
    Product findByProductId(Long productId);

//    @Query("SELECT p FROM Product p WHERE "
//            + "(:isGreen IS NULL OR p.isGreen = :isGreen) AND "
//            + "(:categoryName IS NULL OR p.categoryName = :categoryName) AND "
//            + "(:searchTerm IS NULL OR p.productName LIKE %:searchTerm% OR p.categoryName LIKE %:searchTerm%) AND "
//            + "(:isLowCalorie IS NULL OR (p.categoryName = '음료' AND p.kcal < 20) OR "
//            + "(p.categoryName != '음료' AND p.kcal < 40)) AND "
//            + "(:isHighCalorie IS NULL OR (p.kcal > 500 AND p.sodium > 600)) AND "
//            + "(:isSugarFree IS NULL OR p.sugar <= 1) AND "
//            + "(:isLowSugar IS NULL OR p.sugar <= p.servingSize * 0.05) AND "
//            + "(:isLowCarb IS NULL OR (p.carbohydrate >= p.servingSize * 0.11 AND "
//            + "p.carbohydrate <= p.servingSize * 0.20)) AND "
//            + "(:isHighCarb IS NULL OR p.carbohydrate > p.servingSize * 0.6) AND "
//            + "(:isKeto IS NULL OR p.carbohydrate <= p.servingSize * 0.10) AND "
//            + "(:isLowTransFat IS NULL OR p.transFat <= 1) AND "
//            + "(:isLowSaturatedFat IS NULL OR p.saturatedFat <= p.servingSize * 0.02) AND "
//            + "(:isLowFat IS NULL OR ((p.categoryName = '음료' AND p.fat <= 1.5) OR "
//            + "(p.categoryName != '음료' AND p.fat <= 3.0))) AND "
//            + "(:isHighFat IS NULL OR p.fat > p.servingSize * 0.2) AND"
//            + "(:isHighProtein IS NULL OR p.protein >= p.servingSize * 0.20) AND "
//            + "(:isLowSodium IS NULL OR p.sodium <= p.servingSize * 2) AND "
//            + "(:isLowCholesterol IS NULL OR p.cholesterol < 300)")
//    List<Product> filterProducts(
//            @Param("isGreen") Boolean isGreen,
//            @Param("categoryName") String categoryName,
//            @Param("searchTerm") String searchTerm,
//            @Param("isLowCalorie") Boolean isLowCalorie,
//            @Param("isHighCalorie") Boolean isHighCalorie,
//            @Param("isSugarFree") Boolean isSugarFree,
//            @Param("isLowSugar") Boolean isLowSugar,
//            @Param("isLowCarb") Boolean isLowCarb,
//            @Param("isHighCarb") Boolean isHighCarb,
//            @Param("isKeto") Boolean isKeto,
//            @Param("isLowTransFat") Boolean isLowTransFat,
//            @Param("isHighProtein") Boolean isHighProtein,
//            @Param("isLowSodium") Boolean isLowSodium,
//            @Param("isLowCholesterol") Boolean isLowCholesterol,
//            @Param("isLowSaturatedFat") Boolean isLowSaturatedFat,
//            @Param("isLowFat") Boolean isLowFat,
//            @Param("isHighFat") Boolean isHighFat,
//            Pageable pageable
//    );
//    default List<Product> filterProducts(Object[] filterConditions, Pageable pageable) {
//        return filterProducts(
//                (Boolean) filterConditions[0],
//                (String) filterConditions[1],
//                (String) filterConditions[2],
//                (Boolean) filterConditions[3],
//                (Boolean) filterConditions[4],
//                (Boolean) filterConditions[5],
//                (Boolean) filterConditions[6],
//                (Boolean) filterConditions[7],
//                (Boolean) filterConditions[8],
//                (Boolean) filterConditions[9],
//                (Boolean) filterConditions[10],
//                (Boolean) filterConditions[11],
//                (Boolean) filterConditions[12],
//                (Boolean) filterConditions[13],
//                (Boolean) filterConditions[14],
//                (Boolean) filterConditions[15],
//                (Boolean) filterConditions[16],
//                pageable
//        );
//    }

    // 평균 평점 계산
    @Query("SELECT COALESCE(AVG(r.rating), 0) FROM Review r WHERE r.product.productId = :productId")
    Optional<Double> findAverageRatingByProductId(@Param("productId") Long productId);

    //임시로 쓸 로직
    @Query("SELECT p FROM Product p ORDER BY p.productId DESC")
    List<Product> findAllByDesc();

    // 끼니 PICK 정렬

    //Top 12 끼니랭킹
    @Query("SELECT p FROM Product p ORDER BY p.score DESC, p.updatedAt DESC, p.productId DESC")
    List<Product> findTopProductsByScoreAndUpdatedAt(Pageable pageable);

    //categoryName과 productFilter 테이블 적용해서 끼니 랭킹 리스트에 있는 상품 필터링.
    @Query("SELECT p FROM Product p " +
            "INNER JOIN ProductFilter pf ON p.productId = pf.productId " +
            "ORDER BY p.score DESC, p.updatedAt DESC, p.productId DESC")
    List<Product> findAllByScoreAndCategoryNameAndFilters(Pageable pageable);

    // is_green true인 제품들 중에서 nut_score 높은 순으로 정렬, 같은 경우, 최근 업데이트 되고, product_id 높은 순으로.
    @Query("SELECT p FROM Product p WHERE p.isGreen = true ORDER BY p.nutScore DESC, p.updatedAt DESC, p.productId DESC")
    List<Product> findTopProductsByIsGreenIsTrueOrderByNutScoreDescUpdatedAtDescProductIdDesc(Pageable pageable);


    //categoryName과 productFilter 테이블 적용해서 끼니 그린 리스트에 있는 상품 필터링.
    @Query("SELECT p FROM Product p " +
            "LEFT JOIN ProductFilter pf ON p.productId = pf.productId " +
            "WHERE p.isGreen = true " +
            "ORDER BY p.nutScore DESC, p.updatedAt DESC, p.productId DESC")
    List<Product> findAllByIsGreenIsTrueOrderByNutScoreDescAndCategoryNameAndFilters(Pageable pageable);
}


