package com.example.kinnibackend.dto.review;

import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.Review;
import com.example.kinnibackend.entity.Users;
import lombok.*;

@Getter @Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class GetReviewResponseDTO {
    private Long reviewId;
    private Product product;
    private Users user;
    private Double rating;
    private String content;
    private String image1;
    private String image2;
    private String image3;
    private String image4;

    public static GetReviewResponseDTO fromEntity(Review review) {
        return GetReviewResponseDTO.builder()
                .reviewId(review.getReviewId())
                .product(review.getProduct())
                .user(review.getUsers())
                .rating(review.getRating())
                .content(review.getContent())
                .image1(review.getImage1())
                .image2(review.getImage2())
                .image3(review.getImage3())
                .image4(review.getImage4())
                .build();
    }
}
