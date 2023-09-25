package com.example.kinnibackend.dto.product;

import com.example.kinnibackend.entity.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProductResponseWithReviewCountDTO {

    private Long productId;
    private String productName;
    private String image;
    private Float averageRating;
    private Boolean isGreen;
    private String categoryName;
    private Double score;
    private long reviewCount;

    public ProductResponseWithReviewCountDTO(Product entity, long reviewCount){
        this.productId = entity.getProductId();
        this.productName = entity.getProductName();
        this.image = entity.getImage();
        this.averageRating = entity.getAverageRating();
        this.isGreen = entity.getIsGreen();
        this.categoryName = entity.getCategoryName();
        this.score = entity.getScore();
        this.reviewCount = reviewCount;
    }


    public Product toEntity(){
        return Product.builder()
                .productId(productId)
                .productName(productName)
                .image(image)
                .averageRating(averageRating)
                .isGreen(isGreen)
                .categoryName(categoryName)
                .score(score)
                .build();
    }

}