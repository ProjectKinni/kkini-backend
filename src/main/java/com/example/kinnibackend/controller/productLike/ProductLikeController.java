package com.example.kinnibackend.controller.productLike;

import com.example.kinnibackend.dto.productLike.ProductLikeDTO;
import com.example.kinnibackend.entity.like.ProductLike;
import com.example.kinnibackend.service.productLike.ProductLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
@CrossOrigin(origins = "http://localhost:3000") // 허용할 원본 출처 설정
public class ProductLikeController {

    private final ProductLikeService productLikeService;

    @GetMapping("/{userId}/{productId}")
    public ResponseEntity<Boolean> getLikeStatus(@PathVariable Long userId, @PathVariable Long productId) {
        boolean isLiked = productLikeService.isProductLiked(userId, productId);

        return ResponseEntity.ok(isLiked);
    }

    @PostMapping("/{userId}/{productId}/toggle")
    public ResponseEntity<Boolean> toggleProductLike(@PathVariable Long userId, @PathVariable Long productId) {
        boolean result = productLikeService.toggleProductLike(userId, productId);

        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED).body(true);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(false);
        }
    }

    @GetMapping("/liked-products/{userId}")
    public ResponseEntity<Page<ProductLike>> getLikedProducts(@PathVariable Long userId, Pageable pageable) {
        Page<ProductLike> likedProducts = productLikeService.getProductLikesByUserId(userId, pageable);
        return ResponseEntity.ok(likedProducts);
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<Void> removeProduct(@PathVariable Long userId, @PathVariable Long productId) {
        productLikeService.removeProductLike(userId, productId);
        return ResponseEntity.noContent().build();
    }
}