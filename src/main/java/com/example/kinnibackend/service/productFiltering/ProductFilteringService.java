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
        if (searchTerm == null) {
            return true;
        }
        String productName = product.getProductName();
        String categoryName = product.getCategoryName();
        return (productName != null && productName.contains(searchTerm)) ||
                (categoryName != null && categoryName.contains(searchTerm));
    }

    private boolean isMatchingLowCalorieFilter(Product product, Boolean isLowCalorie) {
        if (isLowCalorie == null) {
            return true;
        }

        Double kcal = product.getKcal();
        if (kcal == null) {
            return false;
        }

        return (isLowCalorie && ((product.getCategoryName().equals("음료") && kcal < 20) ||
                (!product.getCategoryName().equals("음료") && kcal < 40)));
    }

    private boolean isMatchingSugarFilter(Product product, Boolean isSugarFree, Boolean isLowSugar) {
        Double sugar = product.getSugar();
        Double servingSize = product.getServingSize();
        if (sugar == null || servingSize == null) {
            return false;
        }
        return (isSugarFree == null || (isSugarFree && sugar <= 1)) &&
                (isLowSugar == null || (isLowSugar && sugar <= servingSize * 0.05));
    }

    private boolean isMatchingCarbFilter(Product product, Boolean isLowCarb, Boolean isKeto) {
        Double carbohydrate = product.getCarbohydrate();
        Double servingSize = product.getServingSize();
        if (carbohydrate == null || servingSize == null) {
            return false;
        }
        return (isLowCarb == null || (isLowCarb && carbohydrate >= servingSize * 0.11 &&
                carbohydrate <= servingSize * 0.20)) &&
                (isKeto == null || (isKeto && carbohydrate <= servingSize * 0.10));
    }

    private boolean isMatchingFatFilter(Product product, Boolean isTransFat, Boolean isSaturatedFat, Boolean isLowFat) {
        Double fat = product.getFat();
        Double transFat = product.getTransFat();
        Double saturatedFat = product.getSaturatedFat();
        Double servingSize = product.getServingSize();
        if (fat == null || transFat == null || saturatedFat == null || servingSize == null) {
            return false;
        }
        if (isLowFat != null) {
            if (product.getCategoryName().equals("음료")) {
                return (isLowFat && fat <= 1.5) &&
                        (isTransFat == null || (isTransFat && transFat <= 1)) &&
                        (isSaturatedFat == null || (isSaturatedFat && saturatedFat <= servingSize * 0.02));
            } else {
                return (isLowFat && fat <= 3.0) &&
                        (isTransFat == null || (isTransFat && transFat <= 1)) &&
                        (isSaturatedFat == null || (isSaturatedFat && saturatedFat <= servingSize * 0.02));
            }
        } else {
            return (isTransFat == null || (isTransFat && transFat <= 1)) &&
                    (isSaturatedFat == null || (isSaturatedFat && saturatedFat <= servingSize * 0.02));
        }
    }

    private boolean isMatchingProteinFilter(Product product, Boolean isHighProtein) {
        Double protein = product.getProtein();
        Double servingSize = product.getServingSize();
        if (protein == null || servingSize == null) {
            return false;
        }
        return isHighProtein == null || (isHighProtein && protein >= servingSize * 0.20);
    }

    private boolean isMatchingSodiumFilter(Product product, Boolean isLowSodium) {
        Double sodium = product.getSodium();
        Double servingSize = product.getServingSize();
        if (sodium == null || servingSize == null) {
            return false;
        }
        return isLowSodium == null || (isLowSodium && sodium <= servingSize * 2);
    }

    private boolean isMatchingCholesterolFilter(Product product, Boolean isCholesterol) {
        Double cholesterol = product.getCholesterol();
        if (cholesterol == null) {
            return false;
        }
        return isCholesterol == null || (isCholesterol && cholesterol < 300);
    }
}
