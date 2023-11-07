package com.example.kinnibackend.dto.product;

import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.ProductFilter;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class CombinedSearchFilterDTO {
    private Long productId;
    private Boolean isGreen;
    private String category;
    private String productName;

    private String detail;
    private String averageRating;
    private String makerName;
    private Double servingSize;
    private Double kcal;
    private Double carbohydrate;
    private Double protein; // 단백질
    private Double fat; // 지방
    private Double sodium; // 나트륨
    private Double cholesterol; // 콜레스테롤
    private Double saturatedFat; // 포화지방
    private Double transFat;
    private Double sugar;
    private Double score;
    private String image;
    private String nutImage;
    private Double nutScore;
    private String productLink;
    private Long viewCount;
    private Long reviewCount;


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

    // ProductCardListResponseDTO로 변환하는 메소드
    public ProductCardListResponseDTO toProductCardListResponseDTO() {
        return ProductCardListResponseDTO.builder()
                .productId(this.productId)
                .isGreen(this.isGreen)
                .category(this.category)
                .productName(this.productName)
                .detail(this.detail)
                .averageRating(this.averageRating)
                .makerName(this.makerName)
                .servingSize(this.servingSize)
                .kcal(this.kcal)
                .carbohydrate(this.carbohydrate)
                .protein(this.protein)
                .fat(this.fat)
                .sodium(this.sodium)
                .cholesterol(this.cholesterol)
                .saturatedFat(this.saturatedFat)
                .transFat(this.transFat)
                .sugar(this.sugar)
                .score(this.score)
                .image(this.image)
                .nutImage(this.nutImage)
                .nutScore(this.nutScore)
                .productLink(this.productLink)
                .viewCount(this.viewCount)
                .reviewCount(this.reviewCount)
                .build();
    }
    public Object[] toFilterConditionsArray() {
        return new Object[]{
                this.searchTerm,
                this.isGreen,
                this.productName,
                this.category,
                this.isLowCalorie,
                this.isHighCalorie,
                this.isSugarFree,
                this.isLowSugar,
                this.isLowCarb,
                this.isHighCarb,
                this.isKeto,
                this.isLowTransFat,
                this.isHighProtein,
                this.isLowSodium,
                this.isLowCholesterol,
                this.isLowSaturatedFat,
                this.isLowFat,
                this.isHighFat
        };
    }
    public boolean hasFilters() {
        return (isGreen != null) ||
                (productName != null && !productName.isEmpty()) ||
                (category != null && !category.isEmpty()) ||
                (isLowCalorie != null) ||
                (isHighCalorie != null) ||
                (isSugarFree != null) ||
                (isLowSugar != null) ||
                (isLowCarb != null) ||
                (isHighCarb != null) ||
                (isKeto != null) ||
                (isLowTransFat != null) ||
                (isHighProtein != null) ||
                (isLowSodium != null) ||
                (isLowCholesterol != null) ||
                (isLowSaturatedFat != null) ||
                (isLowFat != null) ||
                (isHighFat != null);
    }
}
