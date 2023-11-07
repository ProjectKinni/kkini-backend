package com.example.kinnibackend.dto.product;

import com.example.kinnibackend.entity.ProductFilter;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductFilteringResponseDTO {
    private Long productId;
    private String productName;
    private Boolean isGreen;
    private String category;

    private String searchTerm;

    private Boolean isLowCalorie;
    private Boolean isHighCalorie;
    private Boolean isSugarFree;
    private Boolean isLowSugar;
    private Boolean isLowCarb;
    private Boolean isHighCarb;
    private Boolean isKeto;
    private Boolean isLowTransFat;
    private Boolean isHighProtein;
    private Boolean isLowSodium;
    private Boolean isLowCholesterol;
    private Boolean isLowSaturatedFat;
    private Boolean isLowFat;
    private Boolean isHighFat;

    public static ProductFilteringResponseDTO fromEntity(ProductFilter productFilter){
        return ProductFilteringResponseDTO.builder()
                .productId(productFilter.getProductId())
                .productName(productFilter.getProductName())
                .category(productFilter.getCategory())
                .isGreen(productFilter.getIsGreen())
                .isLowCalorie(productFilter.getIsLowCalorie())
                .isHighCalorie(productFilter.getIsHighCalorie())
                .isSugarFree(productFilter.getIsSugarFree())
                .isLowSugar(productFilter.getIsLowSugar())
                .isLowCarb(productFilter.getIsLowCarb())
                .isHighCarb(productFilter.getIsHighCarb())
                .isKeto(productFilter.getIsKeto())
                .isLowTransFat(productFilter.getIsLowTransFat())
                .isHighProtein(productFilter.getIsHighProtein())
                .isLowSodium(productFilter.getIsLowSodium())
                .isLowCholesterol(productFilter.getIsLowCholesterol())
                .isLowSaturatedFat(productFilter.getIsLowSaturatedFat())
                .isLowFat(productFilter.getIsLowFat())
                .isHighFat(productFilter.getIsHighFat())
                .build();
    }
}
