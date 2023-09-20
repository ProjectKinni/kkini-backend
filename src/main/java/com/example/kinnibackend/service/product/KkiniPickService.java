package com.example.kinnibackend.service.product;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.dto.product.ProductFilteringResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.repository.productLike.ProductLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KkiniPickService {

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final ProductLikeRepository productLikeRepository;

    public List<ProductCardListResponseDTO> getFilteredLikedProducts
            (Long userId, String categoryName, ProductFilteringResponseDTO filterDTO) {

        List<Long> likedProductIds = productLikeRepository.findByUsersUserId(userId)
                .stream()
                .map(productLike -> productLike.getProduct().getProductId())
                .collect(Collectors.toList());

        List<Product> likedProducts = productRepository.findAllById(likedProductIds);

        List<Product> filteredProducts = productRepository.filterProducts(filterDTO.toFilterConditionsArray())
                .stream()
                .filter(product -> !likedProductIds.contains(product.getProductId()) &&
                        likedProducts.stream().anyMatch(likedProduct ->
                                        likedProduct.getCategoryName().equals(product.getCategoryName()) &&
                                // 다른 조건 추가 가능
                        ))
                .collect(Collectors.toList());

        List<ProductCardListResponseDTO> filteredProductDTOs = filteredProducts.stream()
                .map(ProductCardListResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return filteredProductDTOs;
    }
}
