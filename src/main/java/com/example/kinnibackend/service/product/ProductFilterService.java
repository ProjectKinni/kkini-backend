package com.example.kinnibackend.service.product;

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

    @Autowired
    private final ReviewRepository reviewRepository;

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

    //    public boolean productMatchesUserCondition(ProductFilteringResponseDTO product, ProductFilteringResponseDTO userCondition){
//        logger.info("Matching product conditions with user conditions...");
//        boolean matches =  product.getIsGreen().equals(userCondition.getIsGreen()) &&
//                product.getCategoryName().equals(userCondition.getCategoryName()) &&
//                product.getIsLowCalorie().equals(userCondition.getIsLowCalorie()) &&
//                product.getIsSugarFree().equals(userCondition.getIsSugarFree()) &&
//                product.getIsLowSugar().equals(userCondition.getIsLowSugar()) &&
//                product.getIsLowCarb().equals(userCondition.getIsLowCarb()) &&
//                product.getIsKeto().equals(userCondition.getIsKeto()) &&
//                product.getIsTransFat().equals(userCondition.getIsTransFat()) &&
//                product.getIsHighProtein().equals(userCondition.getIsHighProtein()) &&
//                product.getIsLowSodium().equals(userCondition.getIsLowSodium()) &&
//                product.getIsCholesterol().equals(userCondition.getIsCholesterol()) &&
//                product.getIsSaturatedFat().equals(userCondition.getIsSaturatedFat()) &&
//                product.getIsLowFat().equals(userCondition.getIsLowFat());
//        logger.info("Product matching result: {}", matches);
//        return matches;
//    }

    //시도해볼것
//    public List<ProductCardListResponseDTO> getFilteredProductsByUserLikes(Long userId) {
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
//        List<ProductCardListResponseDTO> likedProducts = likedProductDTOs.stream()
//                .map(dto -> ProductCardListResponseDTO.fromEntity(dto.getProduct()))
//                .collect(Collectors.toList());
//
//        return likedProducts;
//    }

    public List<ProductCardListResponseDTO> getFilteredProductsByUserLikes(Long userId, ProductFilteringResponseDTO additionalFilter, int page) {
        logger.info("Entering getFilteredProductsByUserLikes for userId: {}", userId);

        List<ProductLikeDTO> likedProductDTOs = productLikeRepository.findByUsersUserId(userId)
                .stream()
                .map(pl -> ProductLikeDTO.fromEntity(pl.getProduct(), pl.getUsers()))
                .collect(Collectors.toList());

        if (likedProductDTOs == null || likedProductDTOs.isEmpty()) {
            logger.warn("No liked products found for userId: {}", userId);
            return Collections.emptyList();
        }

        List<ProductCardListResponseDTO> finalFilteredProducts = new ArrayList<>();

        for (ProductLikeDTO likedProductDTO : likedProductDTOs) {
            logger.info("Processing liked product with id: {}", likedProductDTO.getProductId());

            Optional<ProductFilter> optionalProductFilter =
                    productFilterRepository.findById(likedProductDTO.getProductId());

            if (!optionalProductFilter.isPresent()) {
                logger.warn("No product filter found for productId: {}", likedProductDTO.getProductId());
                continue;
            }

            ProductFilter productFilter = optionalProductFilter.get();
            logger.info("Converting ProductFilter to ProductFilteringResponseDTO for productId: {}", likedProductDTO.getProductId());
            ProductFilteringResponseDTO baseFilteringDTO = ProductFilteringResponseDTO.fromEntity(productFilter);
            logger.info("Successfully converted ProductFilter to ProductFilteringResponseDTO for productId: {}", likedProductDTO.getProductId());

            if (additionalFilter != null) {
                logger.info("Merging additional filter with base filter for productId: {}", likedProductDTO.getProductId());
                baseFilteringDTO.merge(additionalFilter);
                logger.info("Merged additional filter with base filter for productId: {}", likedProductDTO.getProductId());
            }

            logger.info("Filtering products with the merged filter for productId: {}", likedProductDTO.getProductId());
            List<ProductCardListResponseDTO> filteredProducts = filterProducts(baseFilteringDTO, page);
            logger.info("Finished filtering products with the merged filter for productId: {}", likedProductDTO.getProductId());

            if (filteredProducts != null && !filteredProducts.isEmpty()) {
                finalFilteredProducts.addAll(filteredProducts);
            }
        }

        return finalFilteredProducts.stream().distinct().collect(Collectors.toList());
    }


    // 필터링 기능
    public List<ProductCardListResponseDTO> filterProducts(ProductFilteringResponseDTO productFilteringResponseDTO, int page) {
        //paging
        int pageSize = 15;
        Pageable pageable = PageRequest.of(page, pageSize);

        // 필터링 조건 설정
        Object[] filterConditions = productFilteringResponseDTO.toFilterConditionsArray();

        List<Product> products = productRepository.filterProducts(filterConditions, pageable);

        return (products == null || products.isEmpty())
                ? Collections.emptyList()
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
