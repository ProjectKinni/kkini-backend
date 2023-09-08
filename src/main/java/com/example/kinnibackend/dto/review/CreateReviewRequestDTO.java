package com.example.kinnibackend.dto.review;

import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.Review;
import com.example.kinnibackend.entity.Users;
import lombok.*;

@Getter @Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CreateReviewRequestDTO {
    private Long productId;
    private Long userId;
    private Double rating;
    private String content;
    private String image1;
    private String image2;
    private String image3;
    private String image4;

    public Review toEntity(Users user, Product product) {
        return Review.builder()
                .product(product)
                .users(user)
                .rating(this.rating)
                .content(this.content)
                .image1(this.image1)
                .image2(this.image2)
                .image3(this.image3)
                .image4(this.image4)
                .build();
    }
}
