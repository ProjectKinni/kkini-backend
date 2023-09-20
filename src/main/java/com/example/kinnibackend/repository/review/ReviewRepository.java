package com.example.kinnibackend.repository.review;

import com.example.kinnibackend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct_ProductIdOrderByCreatedAtDesc(Long productId, Pageable pageable);
    List<Review> findByUsers_UserIdOrderByCreatedAtDesc(Long userId);
    List<Review> findAllByOrderByCreatedAtDesc(Pageable pageable);

}
