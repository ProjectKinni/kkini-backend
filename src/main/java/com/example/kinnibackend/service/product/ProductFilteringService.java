package com.example.kinnibackend.service.product;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.dto.product.ProductFilteringResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<ProductCardListResponseDTO> filterProducts(ProductFilteringResponseDTO productFilteringResponseDTO, int page) {
        //paging
        int pageSize = 15;
        Pageable pageable = PageRequest.of(page, pageSize);

        // 띄어쓰기 제거
        String searchTerm = productFilteringResponseDTO.getSearchTerm().replace(" ", "");

        // 띄어쓰기가 제거된 검색어로 필터링 조건 설정
        Object[] filterConditions = productFilteringResponseDTO.toFilterConditionsArray(searchTerm); // searchTerm을 인자로 전달

        List<Product> products = productRepository.filterProducts(filterConditions, pageable);

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