package com.example.kinnibackend.service.product;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.dto.product.ProductFilteringResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.ProductFilter;
import com.example.kinnibackend.repository.product.ProductFilterRepository;
import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.repository.productLike.ProductLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductFilterService {

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final ProductLikeRepository productLikeRepository;

    @Autowired
    private final ProductFilterRepository productFilterRepository;

    public List<ProductCardListResponseDTO> getFilteredLikedProducts
            (Long userId, String categoryName, ProductFilteringResponseDTO filterDTO, int page, int size) {

        int pageSize = 15;
        Pageable pageable = PageRequest.of(page, pageSize);

        List<Long> likedProductIds = productLikeRepository.findByUsersUserId(userId)
                .stream()
                .map(productLike -> productLike.getProduct().getProductId())
                .collect(Collectors.toList());

        List<ProductFilter> likedProductFilters = productFilterRepository.findAllById(likedProductIds);
        System.out.println(likedProductFilters);  // For debugging

        Set<Product> filteredProductsSet = new HashSet<>();

        for (ProductFilter likedProductFilter : likedProductFilters) {
            Page<ProductFilter> similarProductFilters = productFilterRepository.filterProductResponse(
                    likedProductFilter.getIsGreen(),
                    likedProductFilter.getCategoryName(),
                    likedProductFilter.getIsLowCalorie(), likedProductFilter.getIsSugarFree(),
                    likedProductFilter.getIsLowSugar(), likedProductFilter.getIsLowCarb(),
                    likedProductFilter.getIsKeto(), likedProductFilter.getIsTransFat(),
                    likedProductFilter.getIsHighProtein(), likedProductFilter.getIsLowSodium(),
                    likedProductFilter.getIsCholesterol(), likedProductFilter.getIsSaturatedFat(),
                    likedProductFilter.getIsLowFat(),
                    pageable
            );

            for (ProductFilter similarProductFilter : similarProductFilters) {
                Product similarProduct = productRepository.findByProductId(similarProductFilter.getProductId());
                if (similarProduct != null && !likedProductIds.contains(similarProduct.getProductId())) {
                    filteredProductsSet.add(similarProduct);
                }
            }
        }

        // ProductCardListResponseDTO로 변환
        List<ProductCardListResponseDTO> filteredProductDTOs = filteredProductsSet.stream()
                .map(ProductCardListResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return filteredProductDTOs;
    }

    public List<ProductCardListResponseDTO> getFilteredLikedProducts
            (Long userId, String categoryName, ProductFilteringResponseDTO filterDTO) {

        List<Long> likedProductIds = productLikeRepository.findByUsersUserId(userId)
                .stream()
                .map(productLike -> productLike.getProduct().getProductId())
                .collect(Collectors.toList());

        List<ProductFilter> likedProductFilters = productFilterRepository.findAllById(likedProductIds);
        System.out.println(likedProductFilters);  // For debugging

        Set<Product> filteredProductsSet = new HashSet<>();

        for (ProductFilter likedProductFilter : likedProductFilters) {
            List<ProductFilter> similarProductFilters = productFilterRepository.filterProductResponse(
                    likedProductFilter.getIsGreen(),
                    likedProductFilter.getCategoryName(),
                    likedProductFilter.getIsLowCalorie(), likedProductFilter.getIsSugarFree(),
                    likedProductFilter.getIsLowSugar(), likedProductFilter.getIsLowCarb(),
                    likedProductFilter.getIsKeto(), likedProductFilter.getIsTransFat(),
                    likedProductFilter.getIsHighProtein(), likedProductFilter.getIsLowSodium(),
                    likedProductFilter.getIsCholesterol(), likedProductFilter.getIsSaturatedFat(),
                    likedProductFilter.getIsLowFat()
            );

            for (ProductFilter similarProductFilter : similarProductFilters) {
                Product similarProduct = productRepository.findByProductId(similarProductFilter.getProductId());
                if (similarProduct != null && !likedProductIds.contains(similarProduct.getProductId())) {
                    filteredProductsSet.add(similarProduct);
                }
            }
        }

        // ProductCardListResponseDTO로 변환
        List<ProductCardListResponseDTO> filteredProductDTOs = filteredProductsSet.stream()
                .map(ProductCardListResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return filteredProductDTOs;
    }
}
