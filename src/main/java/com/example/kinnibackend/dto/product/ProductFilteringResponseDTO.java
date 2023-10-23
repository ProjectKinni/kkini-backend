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
public class ProductFilteringResponseDTO {
    private Long productId;
    private Boolean isGreen;
    private String searchTerm;
    private String categoryName;
    private Boolean isLowCalorie;
    private Boolean isSugarFree;
    private Boolean isLowSugar;
    private Boolean isLowCarb;
    private Boolean isKeto;
    private Boolean isTransFat;
    private Boolean isHighProtein;
    private Boolean isLowSodium;
    private Boolean isCholesterol;
    private Boolean isSaturatedFat;
    private Boolean isLowFat;

    public Object[] toFilterConditionsArray(String searchTerm) {
        return new Object[]{
                this.getIsGreen(),
                searchTerm,
                this.getCategoryName(),
                this.getIsLowCalorie(),
                this.getIsSugarFree(),
                this.getIsLowSugar(),
                this.getIsLowCarb(),
                this.getIsKeto(),
                this.getIsTransFat(),
                this.getIsHighProtein(),
                this.getIsLowSodium(),
                this.getIsCholesterol(),
                this.getIsSaturatedFat(),
                this.getIsLowFat(),
        };
    }

    public static ProductFilteringResponseDTO fromEntity(ProductFilter productFilter){
        return ProductFilteringResponseDTO.builder()
                .productId(productFilter.getProductId())
                .categoryName(productFilter.getCategoryName())
                .isGreen(productFilter.getIsGreen())
                .isLowCalorie(productFilter.getIsLowCalorie())
                .isSugarFree(productFilter.getIsSugarFree())
                .isLowSugar(productFilter.getIsLowSugar())
                .isLowCarb(productFilter.getIsLowCarb())
                .isKeto(productFilter.getIsKeto())
                .isTransFat(productFilter.getIsTransFat())
                .isHighProtein(productFilter.getIsHighProtein())
                .isLowSodium(productFilter.getIsLowSodium())
                .isCholesterol(productFilter.getIsCholesterol())
                .isSaturatedFat(productFilter.getIsSaturatedFat())
                .isLowFat(productFilter.getIsLowFat())
                .build();
    }
}
