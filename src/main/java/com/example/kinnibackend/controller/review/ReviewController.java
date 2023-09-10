package com.example.kinnibackend.controller.review;

import com.example.kinnibackend.dto.review.*;
import com.example.kinnibackend.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{userId}")
    ResponseEntity<CreateReviewResponseDTO> createReview(@RequestBody CreateReviewRequestDTO createReviewRequestDTO, @PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.createReview(createReviewRequestDTO, userId));
    }

    @GetMapping
    ResponseEntity<List<GetReviewResponseDTO>> getReviews() {
        return ResponseEntity.ok(reviewService.getReviews());
    }

    @GetMapping("/{productId}")
    ResponseEntity<List<GetReviewResponseDTO>> getReviewsByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getReviewsByProductId(productId));
    }

    @GetMapping("/{userId}")
    ResponseEntity<List<GetReviewResponseDTO>> getReviewsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId));
    }

    @DeleteMapping("/{reviewId}")
    ResponseEntity<DeleteReviewResponseDTO> deleteReviewByReviewId(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.deleteReviewByReviewId(reviewId));
    }

    @PutMapping("/{reviewId}")
    ResponseEntity<UpdateReviewResponseDTO> updateReviewByReviewId(@RequestBody CreateReviewRequestDTO updateReviewRequestDTO, @PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.updateReviewByReviewId(reviewId, updateReviewRequestDTO));
    }


}
