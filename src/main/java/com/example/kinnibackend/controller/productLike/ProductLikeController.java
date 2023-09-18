package com.example.kinnibackend.controller.productLike;

import com.example.kinnibackend.entity.Like.ProductLike;
import com.example.kinnibackend.service.productLike.ProductLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
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
    public ResponseEntity<List<ProductLike>> getLikedProducts(@PathVariable Long userId) {
        List<ProductLike> likedProducts = productLikeService.getProductLikesByUserId(userId);
        return ResponseEntity.ok(likedProducts);
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<Void> removeProduct(@PathVariable Long userId, @PathVariable Long productId) {
        productLikeService.removeProductLike(userId, productId);
        return ResponseEntity.noContent().build();
    }
}