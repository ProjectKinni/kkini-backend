package com.example.kinnibackend.controller.review;

import com.example.kinnibackend.controller.searchAndFiltering.SearchAndFilteringController;
import com.example.kinnibackend.dto.review.*;
import com.example.kinnibackend.service.product.ProductService;
import com.example.kinnibackend.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ProductService productService;
    static final Logger logger = LoggerFactory.getLogger(SearchAndFilteringController.class);

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

    @PutMapping("/{userId}/{reviewId}")
    ResponseEntity<?> updateReviewByReviewId
            (@PathVariable Long userId, @ModelAttribute CreateReviewRequestDTO updateReviewRequestDTO,
             @PathVariable Long reviewId) {
        try {
            // 로그 추가: 요청 값 검증
            logger.info("Received update request for userId: {}, reviewId: {}", userId, reviewId);
            logger.info("productId: {}", updateReviewRequestDTO.getProductId());

            // 해당 사용자가 해당 상품에 대해 리뷰를 이미 작성했는지 확인
            boolean hasReviewed = reviewService.hasUserReviewedProduct(userId, updateReviewRequestDTO.getProductId());
            if (!hasReviewed) {
                // 사용자가 리뷰를 작성하지 않았다면, 업데이트를 거부
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("사용자는 이 상품에 대한 리뷰를 작성하지 않았습니다.");
            }

            // 사용자가 리뷰를 작성했다면, 리뷰 업데이트 수행
            UpdateReviewResponseDTO responseDTO = reviewService.updateReviewByReviewId(reviewId, updateReviewRequestDTO);
            productService.updateProductAverageRating(updateReviewRequestDTO.getProductId()); // 평균평점 업데이트
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            // 예외 처리: 오류 로그 기록
            logger.error("Error occurred while updating review: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("리뷰 업데이트 중 서버 오류가 발생했습니다.");
        }
    }
}
