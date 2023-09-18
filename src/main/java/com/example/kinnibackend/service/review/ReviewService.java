package com.example.kinnibackend.service.review;

import com.example.kinnibackend.dto.review.*;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.Review;
import com.example.kinnibackend.entity.Users;
import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.repository.review.ReviewRepository;
import com.example.kinnibackend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    @Autowired
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CreateReviewResponseDTO createReview(CreateReviewRequestDTO createReviewRequestDTO, Long userId) {
        Users user = userRepository.findByUserId(userId);
        Product product = productRepository.findByProductId(createReviewRequestDTO.getProductId());
        Review review = createReviewRequestDTO.toEntity(user, product);

        return CreateReviewResponseDTO.fromEntity(reviewRepository.save(review));
    }

    public List<GetReviewResponseDTO> getReviews() {
        List<Review> reviews = reviewRepository.findAll();

        return reviews.stream()
                .map(review -> GetReviewResponseDTO.fromEntity(review))
                .collect(Collectors.toList());
    }

    public List<GetReviewResponseDTO> getReviewsByProductId(Long productId, int page) {
        int pageSize = 10;
        Pageable pageable = (Pageable) PageRequest.of(page, pageSize);
        List<Review> reviews = reviewRepository.findByProduct_ProductIdOrderByCreatedAtDesc(productId, pageable);

        return reviews.stream()
                .map(review -> GetReviewResponseDTO.fromEntity(review))
                .collect(Collectors.toList());
    }

    public List<GetReviewResponseDTO> getReviewsByUserId(Long userId) {
        List<Review> reviews = reviewRepository.findByUsers_UserIdOrderByCreatedAtDesc(userId);

        return reviews.stream()
                .map(review -> GetReviewResponseDTO.fromEntity(review))
                .collect(Collectors.toList());
    }

    public DeleteReviewResponseDTO deleteReviewByReviewId(Long reviewId) {
        reviewRepository.deleteById(reviewId);

        return DeleteReviewResponseDTO.fromEntity(reviewRepository.findById(reviewId));
    }

    public UpdateReviewResponseDTO updateReviewByReviewId(Long reviewId, CreateReviewRequestDTO updateInfo) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);

        if (!optionalReview.isPresent()) {
            return UpdateReviewResponseDTO.builder()
                    .message("리뷰가 존재하지 않습니다.")
                    .build();
        }

        Review review = optionalReview.get();

        try {
            review.updateReview(updateInfo.getRating(), updateInfo.getContent(), updateInfo.getImage1(), updateInfo.getImage2(), updateInfo.getImage3(), updateInfo.getImage4());
            reviewRepository.save(review);
        } catch (Exception e) {
            return UpdateReviewResponseDTO.builder()
                    .message("리뷰 업데이트 실패")
                    .build();
        }

        return UpdateReviewResponseDTO.builder()
                .message("리뷰가 업데이트 되었습니다.")
                .build();
    }


}
