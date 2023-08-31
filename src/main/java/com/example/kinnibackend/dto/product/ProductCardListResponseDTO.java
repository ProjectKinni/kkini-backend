package com.example.kinnibackend.dto.product;

import com.example.kinnibackend.entity.Product;
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
    private Integer vendorId;
    private String vendorName;
    private String productName;
    private String productImage;
    private Float averageRating;

    private Double totalAmount;
    private Double calorie;
    private Double sugar;
    private Double transFat;
    private Double carb; // 탄수화물
    private Double protein; // 단백질
    private Double sodium; // 나트륨
    private Integer cholesterol; // 콜레스테롤
    private Double saturatedFat; // 포화지방
    private Double fat; // 지방

    public static ProductCardListResponseDTO fromEntity(Product product) {
        return ProductCardListResponseDTO.builder()
                .productId(product.getProductId())
                .isGreen(product.getIsGreen())
                .categoryName(product.getCategoryName())
                .vendorId(product.getVendorId())
                .vendorName(product.getVendorName())
                .productName(product.getProductName())
                .productImage(product.getProductImage())
                .averageRating(product.getAverageRating())
                .totalAmount(product.getTotalAmount())
                .calorie(product.getCalorie())
                .sugar(product.getSugar())
                .transFat(product.getTransFat())
                .carb(product.getCarb())
                .protein(product.getProtein())
                .sodium(product.getSodium())
                .cholesterol(product.getCholesterol())
                .saturatedFat(product.getSaturatedFat())
                .fat(product.getFat())
                .build();
    }
}
