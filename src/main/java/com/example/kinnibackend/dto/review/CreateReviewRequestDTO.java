package com.example.kinnibackend.dto.review;

import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.Review;
import com.example.kinnibackend.entity.Users;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CreateReviewRequestDTO {
    private Long userId;
    private Long productId;
    private Double rating;
    private Double tasteRating;
    private Double priceRating;
    private Double ecoRating;
    private String content;
    private MultipartFile image1;
    private MultipartFile image2;
    private MultipartFile image3;
    private MultipartFile image4;

    public Review toEntity(Users user, Product product, String image1, String image2, String image3, String image4) {
        return Review.builder()
                .product(product)
                .users(user)
                .rating(this.rating)
                .tasteRating(this.tasteRating)
                .priceRating(this.priceRating)
                .ecoRating(this.ecoRating)
                .content(this.content)
                .image1(image1)
                .image2(image2)
                .image3(image3)
                .image4(image4)
                .build();
    }
}
