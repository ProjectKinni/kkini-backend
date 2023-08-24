package com.example.kinnibackend.product.service;

import com.example.kinnibackend.product.dto.ProductCardListResponseDTO;
import com.example.kinnibackend.product.dto.ProductMainCardResponseDTO;
import com.example.kinnibackend.product.entity.Product;
import com.example.kinnibackend.product.repository.ProductJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductJPARepository productJPARepository;

    public List<ProductCardListResponseDTO> searchProductsByName(String name, String categoryName) {
        List<ProductCardListResponseDTO> productList;

        if (name != null && !name.trim().isEmpty()) {
            List<Product> productsWithCategoryName = productJPARepository.findByCategoryNameContaining(name);

            List<ProductCardListResponseDTO> categories = productsWithCategoryName.stream()
                    .map(Product::toProductCardListResponseDTO)
                    .collect(Collectors.toList());

            if (!categories.isEmpty()) {
                if (categoryName == null) {
                    productList = productJPARepository.findByCategoryNameOrderByAverageRatingDesc
                                    (categories.get(0).getCategoryName())
                            .stream()
                            .map(Product::toProductCardListResponseDTO)
                            .collect(Collectors.toList());
                } else {
                    productList = productJPARepository.findByNameAndCategoryName(name, categoryName)
                            .stream()
                            .map(Product::toProductCardListResponseDTO)
                            .collect(Collectors.toList());
                }
            } else {
                productList = productJPARepository.findByProductNameContainingOrderByAverageRatingDesc(name)
                        .stream()
                        .map(Product::toProductCardListResponseDTO)
                        .collect(Collectors.toList());
            }
        } else if (categoryName != null) {
            productList = productJPARepository.findByCategoryNameOrderByAverageRatingDesc(categoryName)
                    .stream()
                    .map(Product::toProductCardListResponseDTO)
                    .collect(Collectors.toList());
        } else {
            productList = Collections.emptyList();
        }

        return productList;
    }

    public ProductMainCardResponseDTO getProductById(Long productId) {
        return productJPARepository.findById(productId)
                .map(Product::toProductMainCardResponseDTO)
                .orElse(null);
    }

    public List<ProductCardListResponseDTO> getAllItemsByCategory(String categoryName) {
        return productJPARepository.findByCategoryNameOrderByAverageRatingDesc(categoryName)
                .stream()
                .map(Product::toProductCardListResponseDTO)
                .collect(Collectors.toList());
    }
}