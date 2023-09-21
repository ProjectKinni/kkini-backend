package com.example.kinnibackend.service.product;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.dto.product.ProductFilteringResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.repository.review.ReviewRepository;
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
    private final ReviewRepository reviewRepository;

    // 검색과 검색 결과 필터링 기능
    public List<ProductCardListResponseDTO> filterProducts(ProductFilteringResponseDTO productFilteringResponseDTO) {
        Object[] filterConditions = productFilteringResponseDTO.toFilterConditionsArray();

        List<Product> products = productRepository.filterProducts(filterConditions);

        return (products == null || products.isEmpty())
                ? null
                : products.stream()
                .filter(Objects::nonNull)
                .map(product -> {
                    ProductCardListResponseDTO dto = ProductCardListResponseDTO.fromEntity(product);
                    dto.setReviewCount(reviewRepository.findTotalReviewCountByProductId(product.getProductId()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

}
