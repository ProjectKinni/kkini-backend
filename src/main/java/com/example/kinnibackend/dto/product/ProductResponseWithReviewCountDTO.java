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
    private Float averageTasteRating;
    private Float averagePriceRating;
    private Float averageEcoRating;
    private Boolean isGreen;
    private String category;
    private Double score;
    private long reviewCount;
    private Double carbohydrate;
    private Double protein;
    private Double fat;
    private String makerName;

    public ProductResponseWithReviewCountDTO(Product entity, long reviewCount){
        this.productId = entity.getProductId();
        this.productName = entity.getProductName();
        this.image = entity.getImage();
        this.averageRating = entity.getAverageRating();
        this.averageTasteRating = entity.getAverageTasteRating();
        this.averagePriceRating = entity.getAveragePriceRating();
        this.averageEcoRating = entity.getAverageEcoRating();
        this.isGreen = entity.getIsGreen();
        this.category = entity.getCategory();
        this.score = entity.getScore();
        this.reviewCount = reviewCount;
        this.carbohydrate = entity.getCarbohydrate();
        this.protein = entity.getProtein();
        this.fat = entity.getFat();
        this.makerName = entity.getMakerName();
    }
//    public Product toEntity(){
//        return Product.builder()
//                .productId(productId)
//                .productName(productName)
//                .image(image)
//                .averageRating(averageRating)
//                .isGreen(isGreen)
//                .category(category)
//                .score(score)
//                .build();
//    }

}
