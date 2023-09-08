package com.example.kinnibackend.service.review;

import com.example.kinnibackend.dto.review.CreateReviewRequestDTO;
import com.example.kinnibackend.dto.review.CreateReviewResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.Review;
import com.example.kinnibackend.entity.Users;
import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.repository.review.ReviewRepository;
import com.example.kinnibackend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    @Autowired
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CreateReviewResponseDTO createReview(CreateReviewRequestDTO createReviewRequestDTO) {
        Users user = userRepository.findByUserId(createReviewRequestDTO.getUserId());
        Product product = productRepository.findByProductId(createReviewRequestDTO.getProductId());
        Review review = createReviewRequestDTO.toEntity(user, product);

        return CreateReviewResponseDTO.fromEntity(reviewRepository.save(review));
    }



}
