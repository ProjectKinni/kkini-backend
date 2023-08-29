package com.example.kinnibackend.dto.product;

import com.example.kinnibackend.entity.Product;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductCardListResponseDTO {
    private Long productId;
    private boolean isKkini;
    private String categoryName;
    private int vendorId;
    private String vendorName;
    private String productName;
    private String productImage;
    private float averageRating;

    private Double totalAmount;
    private Double calorie;
    private Double sugar;
    private Double transFat;
    private Double carb; // 탄수화물
    private Double protein; // 단백질
    private Double sodium; // 나트륨
    private Double saturatedFat; // 포화지방
    private Double fat; // 지방

    public static ProductCardListResponseDTO toProduct(Product product) {
        return ProductCardListResponseDTO.builder()
                .productId(product.getProductId())
                .isKkini(product.isKkini())
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
                .saturatedFat(product.getSaturatedFat())
                .fat(product.getFat())
                .build();
    }
}
