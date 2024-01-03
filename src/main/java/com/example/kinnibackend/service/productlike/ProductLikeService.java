package com.example.kinnibackend.service.productlike;

import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.Users;
import com.example.kinnibackend.entity.like.LikeId;
import com.example.kinnibackend.entity.like.ProductLike;
import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.repository.productlike.ProductLikeRepository;
import com.example.kinnibackend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductLikeService {

    private final ProductLikeRepository productLikeRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public boolean toggleProductLike(Long userId, Long productId) {
        LikeId likeId = new LikeId(productId, userId);
        ProductLike existingLike = productLikeRepository.findById(likeId).orElse(null);

        if (existingLike == null) {
            Product product = productRepository.findByProductId(productId);
            Users user = userRepository.findByUserId(userId);
            ProductLike newLike = new ProductLike(product, user);
            productLikeRepository.save(newLike);
            return true; // 좋아요 추가
        } else {
            productLikeRepository.delete(existingLike);
            return false; // 좋아요 제거
        }
    }

    public Page<ProductLike> getProductLikesByUserId(Long userId, Pageable pageable) {
        return productLikeRepository.findByUsersUserId(userId, pageable);
    }


    public boolean isProductLiked(Long userId, Long productId) {
        LikeId likeId = new LikeId(productId, userId);
        return productLikeRepository.existsById(likeId);
    }

    public void removeProductLike(Long userId, Long productId) {
        List<ProductLike> productLikes = productLikeRepository.findByUsersUserIdAndProductProductId(userId, productId);
        if (!productLikes.isEmpty()) {
            productLikes.forEach(product -> {
                productLikeRepository.delete(product);
            });
        }
    }
}