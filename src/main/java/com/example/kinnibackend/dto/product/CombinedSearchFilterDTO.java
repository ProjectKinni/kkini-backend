package com.example.kinnibackend.dto.product;

import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.ProductFilter;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class CombinedSearchFilterDTO {
    private static final Logger logger = LoggerFactory.getLogger(CombinedSearchFilterDTO.class);

    private Long productId;
    private Boolean isGreen;
    private String category;
    private String productName;

    private String detail;
    private Float averageRating;
    private Float averageTasteRating;
    private Float averagePriceRating;
    private Float averageEcoRating;
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
        logger.info("Converting CombinedSearchFilterDTO to ProductCardListResponseDTO for Product ID: {}", productId);
        return ProductCardListResponseDTO.builder()
                .productId(this.productId)
                .isGreen(this.isGreen)
                .category(this.category)
                .productName(this.productName)
                .detail(this.detail)
                .averageRating(this.averageRating)
                .averageTasteRating(this.getAverageTasteRating())
                .averagePriceRating(this.getAveragePriceRating())
                .averageEcoRating(this.getAverageEcoRating())
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
        logger.info("Creating filter conditions array from CombinedSearchFilterDTO for Product Name: {}", productName);
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
                this.isHighFat,
                this.score
        };
    }
    public boolean hasFilters() {
        logger.info("Checking if CombinedSearchFilterDTO has any filters set");
        boolean hasFilters = (isGreen != null) ||
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
        logger.info("CombinedSearchFilterDTO has filters: {}", hasFilters);
        return hasFilters;
    }

    public static CombinedSearchFilterDTO combineProductFilterWithProduct(ProductFilter productFilter, Product product) {

        return CombinedSearchFilterDTO.builder()
                .productId(productFilter.getProductId())
                .productName(productFilter.getProductName())
                .category(productFilter.getCategory())
                .isGreen(productFilter.getIsGreen())
                .detail(product.getDetail())
                .makerName(product.getMakerName())
                .servingSize(product.getServingSize())
                .kcal(product.getKcal())
                .carbohydrate(product.getCarbohydrate())
                .protein(product.getProtein())
                .fat(product.getFat())
                .sodium(product.getSodium())
                .cholesterol(product.getCholesterol())
                .saturatedFat(product.getSaturatedFat())
                .averageEcoRating(product.getAverageEcoRating())
                .averageRating(product.getAverageRating())
                .averagePriceRating(product.getAveragePriceRating())
                .averageTasteRating(product.getAverageTasteRating())
                .transFat(product.getTransFat())
                .sugar(product.getSugar())
                .score(product.getScore())
                .image(product.getImage())
                .nutImage(product.getNutImage())
                .nutScore(product.getNutScore())
                .productLink(product.getProductLink())
                .build();
    }
}
