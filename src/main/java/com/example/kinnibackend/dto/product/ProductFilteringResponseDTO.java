package com.example.kinnibackend.dto.product;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductFilteringResponseDTO {
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
}
