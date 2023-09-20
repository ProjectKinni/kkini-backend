package com.example.kinnibackend.repository.productLike;

import com.example.kinnibackend.entity.like.LikeId;
import com.example.kinnibackend.entity.like.ProductLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductLikeRepository extends JpaRepository<ProductLike, LikeId> {
    List<ProductLike> findByUsersUserIdAndProductProductId(Long userId, Long productId);

    List<ProductLike> findByUsersUserId(Long userId);

}