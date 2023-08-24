package com.example.kinnibackend.product.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductMainCardResponseDTO {
    private Long productId;
    private boolean isKkini;
    private String categoryName;
    private Long hashtagId;
    private int vendorId;
    private String vendorName;
    private String productName;
    private String productImage;
    private float averageRating;
    private int productPrice;
}
