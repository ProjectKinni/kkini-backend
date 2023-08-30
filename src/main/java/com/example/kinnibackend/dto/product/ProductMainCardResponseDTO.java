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
    private Boolean isKkini;
    private String categoryName;
    private Long hashtagId;
    private Integer vendorId;
    private String vendorName;
    private String productName;
    private String productImage;
    private Float averageRating;

    public static ProductMainCardResponseDTO toEntity(Product product) {
        return ProductMainCardResponseDTO.builder()
                .productId(product.getProductId())
                .isKkini(product.getIsKkini())
                .categoryName(product.getCategoryName())
                .hashtagId(product.getHashtagId())
                .vendorId(product.getVendorId())
                .vendorName(product.getVendorName())
                .productName(product.getProductName())
                .productImage(product.getProductImage())
                .averageRating(product.getAverageRating())
                .build();
    }
}
