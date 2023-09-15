package com.example.kinnibackend.service.productFiltering;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.dto.product.ProductFilteringResponseDTO;
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

    public List<ProductCardListResponseDTO> filterProducts(ProductFilteringResponseDTO productFilteringResponseDTO) {
        List<Product> products = productRepository.filterProducts(
                productFilteringResponseDTO.getIsGreen(),
                productFilteringResponseDTO.getSearchTerm(),
                productFilteringResponseDTO.getCategoryName(),
                productFilteringResponseDTO.getIsLowCalorie(),
                productFilteringResponseDTO.getIsSugarFree(),
                productFilteringResponseDTO.getIsLowSugar(),
                productFilteringResponseDTO.getIsLowCarb(),
                productFilteringResponseDTO.getIsKeto(),
                productFilteringResponseDTO.getIsTransFat(),
                productFilteringResponseDTO.getIsHighProtein(),
                productFilteringResponseDTO.getIsLowSodium(),
                productFilteringResponseDTO.getIsCholesterol(),
                productFilteringResponseDTO.getIsSaturatedFat(),
                productFilteringResponseDTO.getIsLowFat()
        );

        return (products == null || products.isEmpty())
                ? null
                : products.stream()
                .filter(Objects::nonNull)
                .map(ProductCardListResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
