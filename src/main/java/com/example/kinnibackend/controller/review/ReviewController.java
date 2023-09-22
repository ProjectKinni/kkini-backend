package com.example.kinnibackend.controller.review;

import com.example.kinnibackend.dto.review.*;
import com.example.kinnibackend.service.product.ProductService;
import com.example.kinnibackend.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ProductService productService;

    @PostMapping("/{userId}")
    ResponseEntity<CreateReviewResponseDTO> createReview
            (@ModelAttribute CreateReviewRequestDTO createReviewRequestDTO, @PathVariable Long userId) {
        CreateReviewResponseDTO responseDTO = reviewService.createReview(createReviewRequestDTO, userId);
        productService.updateProductAverageRating(createReviewRequestDTO.getProductId()); // 평균평점 업데이트
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    ResponseEntity<List<GetReviewResponseDTO>> getReviews(@RequestParam int page) {
        return ResponseEntity.ok(reviewService.getReviews(page));
    }

    @GetMapping("/{productId}")
    ResponseEntity<List<GetReviewResponseDTO>> getReviewsByProductId
            (@PathVariable Long productId, @RequestParam int page) {
        return ResponseEntity.ok(reviewService.getReviewsByProductId(productId, page));
    }
    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<GetReviewResponseDTO>> getReviewsByUserId(@PathVariable Long userId, Pageable pageable) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId, pageable));
    }

    @DeleteMapping("/{reviewId}")
    ResponseEntity<DeleteReviewResponseDTO> deleteReviewByReviewId(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.deleteReviewByReviewId(reviewId));
    }

    @PutMapping("/{reviewId}")
    ResponseEntity<UpdateReviewResponseDTO> updateReviewByReviewId
            (@RequestBody CreateReviewRequestDTO updateReviewRequestDTO, @PathVariable Long reviewId) {
        UpdateReviewResponseDTO responseDTO = reviewService.updateReviewByReviewId(reviewId, updateReviewRequestDTO);
        productService.updateProductAverageRating(updateReviewRequestDTO.getProductId()); // 평균평점 업데이트
        return ResponseEntity.ok(responseDTO);
    }

}
