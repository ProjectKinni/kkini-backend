package com.example.kinnibackend.dto.product;

import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.ProductFilter;
import com.example.kinnibackend.entity.ProductFilterCriteria;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductAndFilteringResponseDTO {
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


    public static ProductCardListResponseDTO fromEntity(Product product, ProductFilter productFilter) {
        Boolean isGreenValue = productFilter != null ? productFilter.getIsGreen() : null;

        return ProductCardListResponseDTO.builder()
                .productId(product.getProductId())
                .isGreen(isGreenValue)
                .category(product.getCategory())
                .productName(product.getProductName())
                .detail(product.getDetail())
                .averageRating(String.format("%.1f", product.getAverageRating()))
                .makerName(product.getMakerName())
                .servingSize(product.getServingSize())
                .kcal(product.getKcal())
                .carbohydrate(product.getCarbohydrate())
                .protein(product.getProtein())
                .fat(product.getFat())
                .sodium(product.getSodium())
                .cholesterol(product.getCholesterol())
                .saturatedFat(product.getSaturatedFat())
                .transFat(product.getTransFat())
                .sugar(product.getSugar())
                .score(product.getScore())
                .image(product.getImage())
                .nutImage(product.getNutImage())
                .nutScore(product.getNutScore())
                .productLink(product.getProductLink())
                .build();
    }

    public boolean matchesCriteria(ProductFilterCriteria criteria) {
        if ((criteria.getIsGreen() != null && !criteria.getIsGreen().equals(this.isGreen))
                || (criteria.getCategory() != null && !criteria.getCategory().equals(this.category))
                || (criteria.getSearchTerm() != null && !this.productName.contains(criteria.getSearchTerm()) && !this.category.contains(criteria.getSearchTerm()))
                || (criteria.getIsLowCalorie() != null && (
                (this.category.equals("음료") && this.kcal >= 20) || (!this.category.equals("음료") && this.kcal >= 40)))
                || (criteria.getIsHighCalorie() != null && (this.kcal <= 500 || this.sodium <= 600))
                || (criteria.getIsSugarFree() != null && this.sugar > 1)
                || (criteria.getIsLowSugar() != null && this.sugar > this.servingSize * 0.05)
                || (criteria.getIsLowCarb() != null && (this.carbohydrate < this.servingSize * 0.11 || this.carbohydrate > this.servingSize * 0.20))
                || (criteria.getIsHighCarb() != null && this.carbohydrate <= this.servingSize * 0.6)
                || (criteria.getIsKeto() != null && this.carbohydrate > this.servingSize * 0.10)
                || (criteria.getIsLowTransFat() != null && this.transFat > 1)
                || (criteria.getIsLowSaturatedFat() != null && this.saturatedFat > this.servingSize * 0.02)
                || (criteria.getIsLowFat() != null && (
                (this.category.equals("음료") && this.fat > 1.5) || (!this.category.equals("음료") && this.fat > 3.0)))
                || (criteria.getIsHighFat() != null && this.fat <= this.servingSize * 0.2)
                || (criteria.getIsHighProtein() != null && this.protein < this.servingSize * 0.20)
                || (criteria.getIsLowSodium() != null && this.sodium > this.servingSize * 2)
                || (criteria.getIsLowCholesterol() != null && this.cholesterol >= 300)) {
            return false;
        }
        return true; // 모든 조건을 만족하면 true
    }

}
