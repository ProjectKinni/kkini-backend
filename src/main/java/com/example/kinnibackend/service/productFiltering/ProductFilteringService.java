package com.example.kinnibackend.service.productFiltering;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductFilteringService {

    @Autowired
    private final ProductRepository productRepository;

    public List<ProductCardListResponseDTO> searchProducts(Boolean isGreen, String searchTerm, String categoryName) {
        List<Product> products = productRepository.searchByCriteriaWithFilters(isGreen, searchTerm, categoryName,
                null, null, null, null, null,
                null, null, null, null, null, null);

        return products.stream()
                .map(ProductCardListResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ProductCardListResponseDTO> filterProductsByCriteria(String criteria) {
        Boolean isLowCalorie = "저칼로리".equals(criteria);
        Boolean isSugarFree = "슈가프리".equals(criteria);
        Boolean isLowSugar = "로우슈가".equals(criteria);
        Boolean isLowCarb = "저탄수화물".equals(criteria);
        Boolean isKeto = "키토".equals(criteria);
        Boolean isTransFat = "트랜스 지방".equals(criteria);
        Boolean isHighProtein = "고단백".equals(criteria);
        Boolean isLowSodium = "저나트륨".equals(criteria);
        Boolean isCholesterol = "콜레스테롤".equals(criteria);
        Boolean isSaturatedFat = "포화지방".equals(criteria);
        Boolean isLowFat = "저지방".equals(criteria);

        List<Product> rawProducts = productRepository.searchByCriteriaWithFilters(
                null, null, null,
                isLowCalorie, isSugarFree, isLowSugar, isLowCarb, isKeto,
                isTransFat, isHighProtein, isLowSodium, isCholesterol,
                isSaturatedFat, isLowFat
        );

        return rawProducts.stream()
                .map(ProductCardListResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
