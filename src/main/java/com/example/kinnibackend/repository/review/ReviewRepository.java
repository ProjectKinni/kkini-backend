package com.example.kinnibackend.repository.review;

import com.example.kinnibackend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct_ProductId(Long productId);
    List<Review> findByUsers_UserId(Long userId);
}
