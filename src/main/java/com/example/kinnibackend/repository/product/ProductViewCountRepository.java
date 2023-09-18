package com.example.kinnibackend.repository.product;

import com.example.kinnibackend.entity.ProductViewCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductViewCountRepository extends JpaRepository<ProductViewCount, ProductViewCount.ProductViewCountId> {
    Optional<ProductViewCount> findByProductProductIdAndUsersUserId(Long productId, Long userId);

    @Query("SELECT SUM(p.viewCount) FROM ProductViewCount p WHERE p.product.productId = :productId")
    Long findTotalViewCountByProductId(@Param("productId") Long productId);
}
