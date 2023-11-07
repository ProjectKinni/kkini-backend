package com.example.kinnibackend.repository.product;

import com.example.kinnibackend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByProductNameContainingIgnoreCaseOrCategoryContainingIgnoreCase
            (String productName, String category, Pageable pageable);

    // 상품 ID로 상품 찾기
    @Query("SELECT p FROM Product p WHERE p.productId = :productId ORDER BY p.productId DESC")
    Product findByProductId(Long productId);

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


