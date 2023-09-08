package com.example.kinnibackend.controller.review;

import com.example.kinnibackend.dto.review.CreateReviewRequestDTO;
import com.example.kinnibackend.dto.review.CreateReviewResponseDTO;
import com.example.kinnibackend.entity.Users;
import com.example.kinnibackend.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // PathVariable로 userId 받아오도록 수정해야함
    @PostMapping
    ResponseEntity<CreateReviewResponseDTO> createReview(@RequestBody CreateReviewRequestDTO createReviewRequestDTO) {
        return ResponseEntity.ok(reviewService.createReview(createReviewRequestDTO));
    }

    @GetMapping
    ResponseEntity<List<GetReviewResponseDTO>> getReviewsByUserId(@PathVariable Long userId) {

    }

    @GetMapping()

}
