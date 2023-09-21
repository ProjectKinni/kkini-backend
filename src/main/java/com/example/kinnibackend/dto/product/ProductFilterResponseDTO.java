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
public class ProductFilterResponseDTO {
    private Long productId;

    private Boolean isGreen;

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

    public ProductFilterResponseDTO(ProductFilter entity){
        this.productId = entity.getProductId();
        this.isGreen = entity.getIsGreen();
        this.categoryName = entity.getCategoryName();
        this.isLowCalorie = entity.getIsLowCalorie();
        this.isSugarFree = entity.getIsSugarFree();
        this.isLowSugar = entity.getIsLowSugar();
        this.isLowCarb = entity.getIsLowCarb();
        this.isKeto = entity.getIsKeto();
        this.isTransFat = entity.getIsTransFat();
        this.isHighProtein = entity.getIsHighProtein();
        this.isLowSodium = entity.getIsLowSodium();
        this.isCholesterol = entity.getIsCholesterol();
        this.isSaturatedFat = entity.getIsSaturatedFat();
        this.isLowFat = entity.getIsLowFat();
    }
}






