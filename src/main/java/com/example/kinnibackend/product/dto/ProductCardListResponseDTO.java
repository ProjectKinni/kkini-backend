package com.example.kinnibackend.product.dto;

import com.example.kinnibackend.product.entity.Product;
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
}
