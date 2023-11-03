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

    public Object[] toFilterConditionsArray(String searchTerm) {
        return new Object[]{
                this.getSearchTerm(),
                this.getIsGreen(),
                this.getProductName(),
                this.getCategory(),
                this.getIsLowCalorie(),
                this.getIsHighCalorie(),
                this.getIsSugarFree(),
                this.getIsLowSugar(),
                this.getIsLowCarb(),
                this.getIsHighCarb(),
                this.getIsKeto(),
                this.getIsLowTransFat(),
                this.getIsHighProtein(),
                this.getIsLowSodium(),
                this.getIsLowCholesterol(),
                this.getIsLowSaturatedFat(),
                this.getIsLowFat(),
                this.getIsHighFat()
        };
    }

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

    public void merge(ProductFilteringResponseDTO additionalFilter) {
        if (additionalFilter != null) {
            if (additionalFilter.getIsGreen() != null) {
                this.isGreen = additionalFilter.getIsGreen();
            }
            if (additionalFilter.getCategory() != null) {
                this.category = additionalFilter.getCategory();
            }
            if (additionalFilter.getIsLowCalorie() != null) {
                this.isLowCalorie = additionalFilter.getIsLowCalorie();
            }
            if (additionalFilter.getIsHighCalorie() != null) {
                this.isHighCalorie = additionalFilter.getIsHighCalorie();
            }
            if (additionalFilter.getIsSugarFree() != null) {
                this.isSugarFree = additionalFilter.getIsSugarFree();
            }
            if (additionalFilter.getIsLowSugar() != null) {
                this.isLowSugar = additionalFilter.getIsLowSugar();
            }
            if (additionalFilter.getIsLowCarb() != null) {
                this.isLowCarb = additionalFilter.getIsLowCarb();
            }
            if (additionalFilter.getIsHighCarb() != null) {
                this.isHighCarb = additionalFilter.getIsHighCarb();
            }
            if (additionalFilter.getIsKeto() != null) {
                this.isKeto = additionalFilter.getIsKeto();
            }
            if (additionalFilter.getIsLowTransFat() != null) {
                this.isLowTransFat = additionalFilter.getIsLowTransFat();
            }
            if (additionalFilter.getIsHighProtein() != null) {
                this.isHighProtein = additionalFilter.getIsHighProtein();
            }
            if (additionalFilter.getIsLowSodium() != null) {
                this.isLowSodium = additionalFilter.getIsLowSodium();
            }
            if (additionalFilter.getIsLowCholesterol() != null) {
                this.isLowCholesterol = additionalFilter.getIsLowCholesterol();
            }
            if (additionalFilter.getIsLowSaturatedFat() != null) {
                this.isLowSaturatedFat = additionalFilter.getIsLowSaturatedFat();
            }
            if (additionalFilter.getIsLowFat() != null) {
                this.isLowFat = additionalFilter.getIsLowFat();
            }
            if (additionalFilter.getIsHighFat() != null) {
                this.isHighFat = additionalFilter.getIsHighFat();
            }
        }
    }
}
