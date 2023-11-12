package com.example.kinnibackend.service.search;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.productViewCount.ProductViewCount;
import com.example.kinnibackend.entity.Users;
import com.example.kinnibackend.exception.search.ProductNotFoundException;
import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.repository.product.ProductViewCountRepository;
import com.example.kinnibackend.repository.review.ReviewRepository;
import com.example.kinnibackend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ProductRepository productRepository;
    private final ProductViewCountRepository productViewCountRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

    // 검색과 자동완성 기능
    public Page<ProductCardListResponseDTO> searchProducts(String input, int page) {
        logger.info("searchAndAutoComplete 메소드 시작");

        // 입력값 처리
        String searchTerm = input != null ? input.trim() : "";
        if (searchTerm.isEmpty()) {
            logger.warn("searchTerm이 비어있습니다.");
            return Page.empty();
        }
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize);

        // 검색어가 주어진 경우, 자동 완성 로직을 적용해 검색 실행
        Page<Product> searchResults = productRepository.findByProductNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(
                searchTerm, pageable);

        if (searchResults.isEmpty()) {
            logger.info("검색 결과가 없습니다.");
            return Page.empty(pageable);
        }

        // 결과를 DTO로 변환
        Page<ProductCardListResponseDTO> responsePage = searchResults.map(ProductCardListResponseDTO::fromEntity);

        logger.info("searchAndAutoComplete 메소드 종료, 결과 개수: {}", responsePage.getTotalElements());
        return responsePage;
    }

    // 상품 리스트 -> 상품 상세
    public ProductCardListResponseDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        // 조회수 계산 로직
        Long totalViewCount = productViewCountRepository.findTotalViewCountByProductId(productId);
        // 리뷰수 계산 로직
        Long totalReviewCount = reviewRepository.findTotalReviewCountByProductId(productId);

        ProductCardListResponseDTO responseDTO = ProductCardListResponseDTO.fromEntity(product);
        responseDTO.setViewCount(totalViewCount);
        responseDTO.setReviewCount(totalReviewCount);

        return responseDTO;
    }

    public void incrementViewCount(Long productId, Long userId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        if (userId != null) {
            Users user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                ProductViewCount productViewCount =
                        productViewCountRepository.findByProductProductIdAndUsersUserId(productId, userId)
                                .orElseGet(() -> ProductViewCount.builder()
                                        .product(product)
                                        .users(user)
                                        .viewCount(0L)
                                        .build());

                productViewCount.incrementViewCount();
                productViewCountRepository.save(productViewCount);
            }
        }
    }
}