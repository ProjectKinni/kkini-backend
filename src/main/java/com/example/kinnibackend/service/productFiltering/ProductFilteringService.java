package com.example.kinnibackend.service.productFiltering;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.exception.search.ProductNotFoundException;
import com.example.kinnibackend.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
        List<Product> products = productRepository.filterProducts(
                isGreen, searchTerm, categoryName, isLowCalorie, isSugarFree, isLowSugar,
                isLowCarb, isKeto, isTransFat, isHighProtein, isLowSodium,
                isCholesterol, isSaturatedFat, isLowFat
        );

        return (products == null || products.isEmpty())
                ? null
                : products.stream()
                .filter(Objects::nonNull)
                .map(ProductCardListResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
