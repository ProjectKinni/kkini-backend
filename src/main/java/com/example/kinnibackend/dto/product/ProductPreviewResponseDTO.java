package com.example.kinnibackend.dto.product;

import com.example.kinnibackend.entity.Product;
import lombok.*;

@Getter
@AllArgsConstructor @Builder
@NoArgsConstructor
public class ProductPreviewResponseDTO {
    private Long productId;
    private String productName;
    private String image;
    private Float averageRating;
    private Boolean isGreen;
    private String categoryName;

    public ProductPreviewResponseDTO(Product entity){
        this.productId = entity.getProductId();
        this.productName = entity.getProductName();
        this.image = entity.getImage();
        this.averageRating = entity.getAverageRating();
        this.isGreen = entity.getIsGreen();
        this.categoryName = entity.getCategoryName();
    }
}
