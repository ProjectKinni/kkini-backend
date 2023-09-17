package com.example.kinnibackend.controller.productLike;

import com.example.kinnibackend.service.productLike.ProductLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}