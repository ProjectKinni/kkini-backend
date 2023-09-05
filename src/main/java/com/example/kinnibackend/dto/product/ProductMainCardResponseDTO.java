package com.example.kinnibackend.dto.product;

import com.example.kinnibackend.entity.Product;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductMainCardResponseDTO {
    private Long productId;
    private Boolean isGreen;
    private String categoryName;
    private String makerName;
    private String productName;
    private String image;
    private Float averageRating;

    public static ProductMainCardResponseDTO toEntity(Product product) {
        return ProductMainCardResponseDTO.builder()
                .productId(product.getProductId())
                .isGreen(product.getIsGreen())
                .categoryName(product.getCategoryName())
                .makerName(product.getMakerName())
                .productName(product.getProductName())
                .image(product.getImage())
                .averageRating(product.getAverageRating())
                .build();
    }
}
