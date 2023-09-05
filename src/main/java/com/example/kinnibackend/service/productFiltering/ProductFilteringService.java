package com.example.kinnibackend.service.productFiltering;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductFilteringService {

    @Autowired
    private final ProductRepository productRepository;

    public List<ProductCardListResponseDTO> filterProducts(
            Boolean isGreen,
            String searchTerm,
            String categoryName,
            Boolean isLowCalorie,
            Boolean isSugarFree,
            Boolean isLowSugar,
            Boolean isLowCarb,
            Boolean isKeto,
            Boolean isTransFat,
            Boolean isHighProtein,
            Boolean isLowSodium,
            Boolean isCholesterol,
            Boolean isSaturatedFat,
            Boolean isLowFat
    ) {
        List<Product> rawProducts = productRepository.findAll();

        return rawProducts.stream()
                .filter(product -> isMatchingGreenFilter(product, isGreen))
                .filter(product -> isMatchingCategoryFilter(product, categoryName))
                .filter(product -> isMatchingSearchTermFilter(product, searchTerm))
                .filter(product -> isMatchingLowCalorieFilter(product, isLowCalorie))
                .filter(product -> isMatchingSugarFilter(product, isSugarFree, isLowSugar))
                .filter(product -> isMatchingCarbFilter(product, isLowCarb, isKeto))
                .filter(product -> isMatchingFatFilter(product, isTransFat, isSaturatedFat, isLowFat))
                .filter(product -> isMatchingProteinFilter(product, isHighProtein))
                .filter(product -> isMatchingSodiumFilter(product, isLowSodium))
                .filter(product -> isMatchingCholesterolFilter(product, isCholesterol))
                .map(ProductCardListResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private boolean isMatchingGreenFilter(Product product, Boolean isGreen) {
        return isGreen == null || product.getIsGreen().equals(isGreen);
    }

    private boolean isMatchingCategoryFilter(Product product, String categoryName) {
        return categoryName == null || product.getCategoryName().equals(categoryName);
    }

    private boolean isMatchingSearchTermFilter(Product product, String searchTerm) {
        return searchTerm == null || product.getProductName().contains(searchTerm) ||
                product.getCategoryName().contains(searchTerm);
    }

    private boolean isMatchingLowCalorieFilter(Product product, Boolean isLowCalorie) {
        return isLowCalorie == null ||
                (isLowCalorie && ((product.getCategoryName().equals("음료") && product.getKcal() < 20) ||
                        (!product.getCategoryName().equals("음료") && product.getKcal() < 40)));
    }

    private boolean isMatchingSugarFilter(Product product, Boolean isSugarFree, Boolean isLowSugar) {
        return (isSugarFree == null || (isSugarFree && product.getSugar() <= 1)) &&
                (isLowSugar == null || (isLowSugar && product.getSugar() <= product.getServingSize() * 0.05));
    }

    private boolean isMatchingCarbFilter(Product product, Boolean isLowCarb, Boolean isKeto) {
        return (isLowCarb == null || (isLowCarb && product.getCarbohydrate() >= product.getServingSize() * 0.11 &&
                product.getCarbohydrate() <= product.getServingSize() * 0.20)) &&
                (isKeto == null || (isKeto && product.getCarbohydrate() <= product.getServingSize() * 0.10));
    }

    private boolean isMatchingFatFilter(Product product, Boolean isTransFat, Boolean isSaturatedFat, Boolean isLowFat) {
        return (isTransFat == null || (isTransFat && product.getTransFat() <= 1)) &&
                (isSaturatedFat == null || (isSaturatedFat && product.getSaturatedFat() <= product.getServingSize() * 0.02)) &&
                (isLowFat == null || (isLowFat && product.getFat() <= product.getServingSize() * 0.04));
    }

    private boolean isMatchingProteinFilter(Product product, Boolean isHighProtein) {
        return isHighProtein == null || (isHighProtein && product.getProtein() >= product.getServingSize() * 0.20);
    }

    private boolean isMatchingSodiumFilter(Product product, Boolean isLowSodium) {
        return isLowSodium == null || (isLowSodium && product.getSodium() <= product.getServingSize() * 2);
    }

    private boolean isMatchingCholesterolFilter(Product product, Boolean isCholesterol) {
        return isCholesterol == null || (isCholesterol && product.getCholesterol() < 300);
    }
}
