package com.example.kinnibackend.service.product;

import com.example.kinnibackend.dto.product.CombinedSearchFilterDTO;
import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.dto.product.ProductFilteringResponseDTO;
import com.example.kinnibackend.dto.productLike.ProductLikeDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.ProductFilter;
import com.example.kinnibackend.repository.product.ProductFilterRepository;
import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.repository.productLike.ProductLikeRepository;
import com.example.kinnibackend.repository.review.ReviewRepository;
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

    // 필터링 로직
    public List<ProductCardListResponseDTO> filterProducts(CombinedSearchFilterDTO combinedSearchFilterDTO, int page) {

        int pageSize = 15;
        Pageable pageable = PageRequest.of(page, pageSize);

        logger.info("filterProducts 시작: criteria={}, page={}", combinedSearchFilterDTO, page);

        Page<ProductFilter> productsPage = productFilterRepository.filterProducts(
                combinedSearchFilterDTO.getSearchTerm(),
                combinedSearchFilterDTO.getCategory(),
                combinedSearchFilterDTO.getIsGreen(),
                combinedSearchFilterDTO.getIsLowCalorie(),
                combinedSearchFilterDTO.getIsHighCalorie(),
                combinedSearchFilterDTO.getIsSugarFree(),
                combinedSearchFilterDTO.getIsLowSugar(),
                combinedSearchFilterDTO.getIsLowCarb(),
                combinedSearchFilterDTO.getIsHighCarb(),
                combinedSearchFilterDTO.getIsKeto(),
                combinedSearchFilterDTO.getIsLowTransFat(),
                combinedSearchFilterDTO.getIsHighProtein(),
                combinedSearchFilterDTO.getIsLowSodium(),
                combinedSearchFilterDTO.getIsLowCholesterol(),
                combinedSearchFilterDTO.getIsLowSaturatedFat(),
                combinedSearchFilterDTO.getIsLowFat(),
                combinedSearchFilterDTO.getIsHighFat(),
                pageable
        );
        if (productsPage == null || productsPage.isEmpty()) {
            logger.info("검색 결과가 없습니다.");
            return Collections.emptyList();
        } else {
            logger.info("검색 결과 수: {}", productsPage.getTotalElements());
            return productsPage.getContent().stream()
                    .map(productFilter -> {
                        Product product = productRepository.findByProductId(productFilter.getProductId());
                        if (product == null) {
                            return null;
                        }
                        CombinedSearchFilterDTO combinedDTO =
                                CombinedSearchFilterDTO.combineProductFilterWithProduct(productFilter, product);
                        return combinedDTO.toProductCardListResponseDTO();
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
    }

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
            Page<ProductFilter> similarProductFilters = productFilterRepository.filterProducts(
                    null, likedProductFilter.getCategory(),
                    likedProductFilter.getIsGreen(),
                    likedProductFilter.getIsLowCalorie(),likedProductFilter.getIsHighCalorie(),
                    likedProductFilter.getIsSugarFree(), likedProductFilter.getIsLowSugar(),
                    likedProductFilter.getIsLowCarb(), likedProductFilter.getIsHighCarb(),
                    likedProductFilter.getIsKeto(), likedProductFilter.getIsLowTransFat(),
                    likedProductFilter.getIsHighProtein(), likedProductFilter.getIsLowSodium(),
                    likedProductFilter.getIsLowCholesterol(), likedProductFilter.getIsLowSaturatedFat(),
                    likedProductFilter.getIsLowFat(), likedProductFilter.getIsHighFat(),
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

//    public List<ProductCardListResponseDTO> getFilteredProductsByUserLikes(Long userId, ProductFilteringResponseDTO additionalFilter, int page) {
//        logger.info("Entering getFilteredProductsByUserLikes for userId: {}", userId);
//
//        List<ProductLikeDTO> likedProductDTOs = productLikeRepository.findByUsersUserId(userId)
//                .stream()
//                .map(pl -> ProductLikeDTO.fromEntity(pl.getProduct(), pl.getUsers()))
//                .collect(Collectors.toList());
//
//        if (likedProductDTOs == null || likedProductDTOs.isEmpty()) {
//            logger.warn("No liked products found for userId: {}", userId);
//            return Collections.emptyList();
//        }
//
//        List<ProductCardListResponseDTO> finalFilteredProducts = new ArrayList<>();
//
//        for (ProductLikeDTO likedProductDTO : likedProductDTOs) {
//            logger.info("Processing liked product with id: {}", likedProductDTO.getProductId());
//
//            Optional<ProductFilter> optionalProductFilter =
//                    productFilterRepository.findById(likedProductDTO.getProductId());
//
//            if (!optionalProductFilter.isPresent()) {
//                logger.warn("No product filter found for productId: {}", likedProductDTO.getProductId());
//                continue;
//            }
//
//            ProductFilter productFilter = optionalProductFilter.get();
//            logger.info("Converting ProductFilter to ProductFilteringResponseDTO for productId: {}", likedProductDTO.getProductId());
//            ProductFilteringResponseDTO baseFilteringDTO = ProductFilteringResponseDTO.fromEntity(productFilter);
//            logger.info("Successfully converted ProductFilter to ProductFilteringResponseDTO for productId: {}", likedProductDTO.getProductId());
//
//            if (additionalFilter != null) {
//                logger.info("Merging additional filter with base filter for productId: {}", likedProductDTO.getProductId());
//                baseFilteringDTO.merge(additionalFilter);
//                logger.info("Merged additional filter with base filter for productId: {}", likedProductDTO.getProductId());
//            }
//
//            logger.info("Filtering products with the merged filter for productId: {}", likedProductDTO.getProductId());
//            List<ProductCardListResponseDTO> filteredProducts = filterProducts(baseFilteringDTO, page);
//            logger.info("Finished filtering products with the merged filter for productId: {}", likedProductDTO.getProductId());
//
//            if (filteredProducts != null && !filteredProducts.isEmpty()) {
//                finalFilteredProducts.addAll(filteredProducts);
//            }
//        }
//
//        return finalFilteredProducts.stream().distinct().collect(Collectors.toList());
//    }
}
