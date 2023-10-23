package com.example.kinnibackend.dto.product;

import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.ProductFilter;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductCardListResponseDTO {
    private Long productId;
    private Boolean isGreen;
    private String categoryName;
    private String productName;
    private String detail;
    private String averageRating;
    private String makerName;

    private Double servingSize;
    private Double kcal;
    private Double carbohydrate;
    private Double protein; // 단백질
    private Double fat; // 지방
    private Double sodium; // 나트륨
    private Double cholesterol; // 콜레스테롤
    private Double saturatedFat; // 포화지방
    private Double transFat;
    private Double sugar;
    private Double score;
    private String image;
    private String nutImage;
    private Double nutScore;
    private String productLink;

    private Long viewCount;
    private Long reviewCount;

    public static ProductCardListResponseDTO fromEntity(Product product) {
        return ProductCardListResponseDTO.builder()
                .productId(product.getProductId())
                .isGreen(product.getIsGreen())
                .categoryName(product.getCategoryName())
                .productName(product.getProductName())
                .detail(product.getDetail())
                .averageRating(String.format("%.1f", product.getAverageRating()))
                .makerName(product.getMakerName())
                .servingSize(product.getServingSize())
                .kcal(product.getKcal())
                .carbohydrate(product.getCarbohydrate())
                .protein(product.getProtein())
                .fat(product.getFat())
                .sodium(product.getSodium())
                .cholesterol(product.getCholesterol())
                .saturatedFat(product.getSaturatedFat())
                .transFat(product.getTransFat())
                .sugar(product.getSugar())
                .score(product.getScore())
                .image(product.getImage())
                .nutImage(product.getNutImage())
                .nutScore(product.getNutScore())
                .productLink(product.getProductLink())
                .build();
    }

}