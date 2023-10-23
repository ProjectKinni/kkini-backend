package com.example.kinnibackend.service.product;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.dto.product.ProductFilteringResponseDTO;
import com.example.kinnibackend.dto.productLike.ProductLikeDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.ProductFilter;
import com.example.kinnibackend.repository.product.ProductFilterRepository;
import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.repository.productLike.ProductLikeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
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
    private static final Logger logger = LoggerFactory.getLogger(ProductFilterService.class);

    public List<ProductCardListResponseDTO> findTopKkiniPickRanking
            (Long userId, String categoryName, ProductFilteringResponseDTO filterDTO, int page, int size) {

        int pageSize = 15;
        Pageable pageable = PageRequest.of(page, pageSize);

        List<Long> likedProductIds = productLikeRepository.findByUsersUserId(userId)
                .stream()
                .map(productLike -> productLike.getProduct().getProductId())
                .collect(Collectors.toList());

        List<ProductFilter> likedProductFilters = productFilterRepository.findAllById(likedProductIds);

        Set<Product> filteredProductsSet = new HashSet<>();

        for (ProductFilter likedProductFilter : likedProductFilters) {
            Page<ProductFilter> similarProductFilters = productFilterRepository.filterKkiniPickProducts(
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

    //    public List<ProductCardListResponseDTO> findAllKkiniPickByCategoriesAndFilters
//            (Long userId, String categoryName, ProductFilteringResponseDTO filterDTO, int page) {
//        int pageSize = 15;
//        Pageable pageable = PageRequest.of(page, pageSize);
//
//        List<Long> likedProductIds = productLikeRepository.findByUsersUserId(userId)
//                .stream()
//                .map(productLike -> productLike.getProduct().getProductId())
//                .collect(Collectors.toList());
//
//        List<ProductFilter> likedProductFilters = productFilterRepository.findAllById(likedProductIds);
//
//        Set<Product> filteredProductsSet = new HashSet<>();
//
//        for (ProductFilter likedProductFilter : likedProductFilters) {
//            Page<ProductFilter> similarProductFilters = productFilterRepository.filterKkiniPickProducts(
//                    likedProductFilter.getIsGreen(),
//                    likedProductFilter.getCategoryName(),
//                    likedProductFilter.getIsLowCalorie(), likedProductFilter.getIsSugarFree(),
//                    likedProductFilter.getIsLowSugar(), likedProductFilter.getIsLowCarb(),
//                    likedProductFilter.getIsKeto(), likedProductFilter.getIsTransFat(),
//                    likedProductFilter.getIsHighProtein(), likedProductFilter.getIsLowSodium(),
//                    likedProductFilter.getIsCholesterol(), likedProductFilter.getIsSaturatedFat(),
//                    likedProductFilter.getIsLowFat(),
//                    pageable
//            );
//
//            for (ProductFilter similarProductFilter : similarProductFilters) {
//                Product similarProduct = productRepository.findByProductId(similarProductFilter.getProductId());
//                if (similarProduct != null && !likedProductIds.contains(similarProduct.getProductId())) {
//                    filteredProductsSet.add(similarProduct);
//                }
//            }
//        }
//
//        // ProductCardListResponseDTO로 변환
//        List<ProductCardListResponseDTO> filteredProductDTOs = filteredProductsSet.stream()
//                .map(ProductCardListResponseDTO::fromEntity)
//                .collect(Collectors.toList());
//
//        return filteredProductDTOs;
//    }
    public List<ProductFilteringResponseDTO> getKkiniPick(Long userId, Pageable pageable) {
        List<ProductLikeDTO> likedProductDTOs = productLikeRepository.findByUsersUserId(userId)
                .stream()
                .map(pl -> ProductLikeDTO.fromEntity(pl.getProduct(), pl.getUsers()))
                .collect(Collectors.toList());

        List<ProductFilteringResponseDTO> resultDTOs = new ArrayList<>();

        for (ProductLikeDTO likedProductDTO : likedProductDTOs) {
            Optional<ProductFilter> optionalProductFilter = productFilterRepository.findById(likedProductDTO.getProductId());

            if(!optionalProductFilter.isPresent()){
                continue;
            }

            ProductFilter productFilter = optionalProductFilter.get();
            ProductFilteringResponseDTO filteringDTO = ProductFilteringResponseDTO.fromEntity(productFilter);

            Page<ProductFilter> filteredProductFilters = productFilterRepository.filterKkiniPickProducts(
                    filteringDTO.getIsGreen(),
                    filteringDTO.getCategoryName(),
                    filteringDTO.getIsLowCalorie(),
                    filteringDTO.getIsSugarFree(),
                    filteringDTO.getIsLowSugar(),
                    filteringDTO.getIsLowCarb(),
                    filteringDTO.getIsKeto(),
                    filteringDTO.getIsTransFat(),
                    filteringDTO.getIsHighProtein(),
                    filteringDTO.getIsLowSodium(),
                    filteringDTO.getIsCholesterol(),
                    filteringDTO.getIsSaturatedFat(),
                    filteringDTO.getIsLowFat(),
                    pageable
            );

            resultDTOs.addAll(
                    filteredProductFilters.stream()
                            .map(ProductFilteringResponseDTO::fromEntity)
                            .collect(Collectors.toList())
            );
        }

        return resultDTOs.stream().distinct().collect(Collectors.toList());
    }
}
