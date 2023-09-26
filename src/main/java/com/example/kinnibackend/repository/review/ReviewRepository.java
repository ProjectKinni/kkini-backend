package com.example.kinnibackend.repository.review;

import com.example.kinnibackend.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct_ProductIdOrderByCreatedAtDesc(Long productId, Pageable pageable);
    Page<Review> findByUsers_UserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    List<Review> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT COUNT(r.reviewId) FROM Review r WHERE r.product.productId = :productId")
    Long findTotalReviewCountByProductId(@Param("productId") Long productId);

    List<Review> findByUsers_UserIdAndProduct_ProductId(Long userId, Long productId);

    void deleteByUsers_UserId(Long userId);

}
